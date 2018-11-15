package cn.com.adtech.service;

import cn.com.adtech.component.*;
import cn.com.adtech.domain.captcha.UserinfoThreeElementsParameter;
import cn.com.adtech.domain.login.LoginSuccessUserinfo;
import cn.com.adtech.domain.login.directlogin.DirectLoginParameter;
import cn.com.adtech.domain.login.doctorscan.DoctorScanLoginParameter;
import cn.com.adtech.domain.login.identitypassword.AppPasswordParameter;
import cn.com.adtech.domain.login.identitypassword.IdentityPasswordLoginParameter;
import cn.com.adtech.domain.login.qrscan.AppScanLoginParameter;
import cn.com.adtech.domain.login.smscaptcha.IdentitySmsCaptchaLoginParameter;
import cn.com.adtech.enums.LoginType;
import cn.com.adtech.enums.ProjectPath;
import cn.com.adtech.enums.ResponseResultStatus;
import cn.com.adtech.environment.properties.FacilitateProperties;
import cn.com.adtech.event.LoginSuccessEvent;
import cn.com.adtech.stereotype.ResponseResult;
import cn.com.adtech.stereotype.ResultStatus;
import cn.com.adtech.table.DataTables;
import cn.com.adtech.table.DataTables.Userinfo;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author chenguangxue
 * @CreateDate 2018/11/2 13:06
 */
@Service
@Transactional
@Slf4j
public class LoginService {

    @Autowired
    @Qualifier("primaryJooq")
    private DSLContext jooq;
    @Autowired
    private SmsSender smsSender;
    @Autowired
    private FacilitateProperties facilitateProperties;
    @Autowired
    private LoginVerifier loginVerifier;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CacheKey cacheKey;
    @Autowired
    private EventPublisher eventPublisher;
    @Autowired
    private StringRedisTemplate redisTemplate;

    // 免密方式进行登录验证
    public ResponseResult doDirectLogin(DirectLoginParameter parameter) {
        // 校验用户信息
        ResponseResultStatus status = loginVerifier.nameAndIdentityVerifier.apply(parameter.getUserName(), parameter.getCardCode());
        if (status.isFail()) {
            return ResponseResult.result(status);
        }

        String loginToken = UUID.randomUUID().toString();
        String value = LoginType.OTHER_DIRECT_LOGIN.join(parameter.getCardCode());
        redisTemplate.opsForValue().set(loginToken, value, 60 * 15, TimeUnit.SECONDS);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("webPath", ProjectPath.WEB_INDEX.getPath());
        jsonObject.put("h5Path", ProjectPath.H5_INDEX.getPath());
        jsonObject.put("loginType", LoginType.OTHER_DIRECT_LOGIN.name());
        jsonObject.put("loginToken", loginToken);

        return ResponseResult.mockSuccess().data(jsonObject);
    }

    // 提供给使用密码方式登录验证
    public ResponseResult doAppPasswordLogin(AppPasswordParameter parameter) {
        // 校验用户信息是否存在
        ResponseResultStatus resultStatus = loginVerifier.nameAndIdentityVerifier.apply(parameter.getUserName(), parameter.getCardCode());

        // 校验密码是否正确
        // 根据身份证查询出用户的信息
        Result<Record2<String, String>> result = jooq.select(Userinfo.realName, Userinfo.password).from(DataTables.t_userinfo)
                .where(Userinfo.identity.eq(parameter.getCardCode())).fetch();
        if (result.isEmpty() || result.stream().noneMatch(record2 -> Objects.equals(record2.get(Userinfo.password), parameter.getPassWord()))) {
            return ResponseResult.result(ResponseResultStatus.USER_PASSWORD_MISMATCH);
        }

        // 将loginToken和身份证保存到缓存中
        String loginToken = UUID.randomUUID().toString();
        LoginType loginType = LoginType.APP_PASSWORD_LOGIN;
        redisTemplate.opsForValue().set(loginToken, loginType.join(parameter.getCardCode()));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("webPath", ProjectPath.WEB_INDEX.getPath());
        jsonObject.put("h5Path", ProjectPath.H5_INDEX.getPath());
        jsonObject.put("loginType", loginType.name().toUpperCase());
        jsonObject.put("loginToken", loginToken);

        return ResponseResult.mockSuccess().data(jsonObject);
    }

    // 以 身份证、密码、图形验证码 执行登录验证
    public ResponseResult doIdentityPasswordLogin(IdentityPasswordLoginParameter parameter) {
        log.info("开始执行登录操作");

        // 校验图形验证码是否正确
        ResultStatus captchaResultStatus = loginVerifier.verifyImageCaptcha.apply(parameter.getImageCaptchaKey(), parameter.getImageCaptcha());
        if (captchaResultStatus.isFail()) {
            return ResponseResult.result(captchaResultStatus);
        }

        // 根据身份证查询出用户的信息
        Result<Record2<String, String>> result = jooq.select(Userinfo.realName, Userinfo.password).from(DataTables.t_userinfo)
                .where(Userinfo.identity.eq(parameter.getIdentityCard())).fetch();

        // 找出密码正确的记录
        List<Record2<String, String>> passwordRecord = result.stream()
                .filter(record2 -> Objects.equals(record2.get(Userinfo.password), parameter.getPassword()))
                .collect(Collectors.toList());

        if (passwordRecord.isEmpty()) {
            return ResponseResult.result(ResponseResultStatus.USER_PASSWORD_MISMATCH);
        }

        boolean anyMatch = passwordRecord.stream()
                .anyMatch(record2 -> Objects.equals(record2.get(Userinfo.realName), parameter.getRealName()));

        if (!anyMatch) {
            return ResponseResult.result(ResponseResultStatus.USER_NAME_IDENTITY_MISMATCH);
        }

        // 判断是否为第一次登录
//        ResultStatus firstLoginResultStatus = loginVerifier.firstLogin.apply(parameter.getIdentityCard());
//        // 如果是第一次访问，则显示修改密码的页面，不允许直接访问系统
//        if (firstLoginResultStatus.isSuccess()) {
//            return ResponseResult.result(firstLoginResultStatus);
//        }

        // 触发登录成功的事件
        String loginToken = cacheKey.loginToken.apply(request.getSession().getId());
        LoginSuccessEvent event = new LoginSuccessEvent(this, parameter.getIdentityCard(), loginToken, parameter.getAppId());
        eventPublisher.publish(event);

        // 对于h5端，返回一个当前登录用户的id
        String userId = jooq.select(Userinfo.id).from(DataTables.t_userinfo).where(
                Userinfo.identity.eq(parameter.getIdentityCard())
        ).fetch().get(0).value1();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", loginToken);
        jsonObject.put("userId", userId);

        return ResponseResult.result(ResponseResultStatus.LOGIN_SUCCESS).data(jsonObject);
    }

    // 以身份证、姓名、手机、短信验证码方式执行登录验证
    public ResponseResult doIdentitySmsCaptchaLogin(IdentitySmsCaptchaLoginParameter parameter) {
        // 首先，校验三要素是否匹配
        UserinfoThreeElementsParameter threeElementsParameter = UserinfoThreeElementsParameter.of(parameter);
        ResultStatus phoneResultStatus = loginVerifier.verifyUserThreeElements.apply(threeElementsParameter);
        if (phoneResultStatus.isFail()) {
            return ResponseResult.result(phoneResultStatus);
        }

        // 校验短信验证码
        ResultStatus captchaResultStatus = loginVerifier.verifySmsCaptcha.apply(parameter.getUserPhone(), parameter.getSmsCaptcha());
        if (captchaResultStatus.isFail()) {
            return ResponseResult.result(captchaResultStatus);
        }
        String appId = parameter.getAppId();

        // 判断是否为第一次登录
        ResultStatus firstLoginResultStatus = loginVerifier.firstLogin.apply(parameter.getIdentityCard());
        // 如果是第一次访问，则显示修改密码的页面，不允许直接访问系统
        if (firstLoginResultStatus.isSuccess()) {
            return ResponseResult.result(firstLoginResultStatus);
        }

        // 触发登录成功事件
        String key = cacheKey.loginToken.apply(request.getSession().getId());
        LoginSuccessEvent event = new LoginSuccessEvent(this, parameter.getIdentityCard(), key, appId);

        eventPublisher.publish(event);
        return ResponseResult.result(ResponseResultStatus.LOGIN_SUCCESS);
    }

    // 扫码登录验证
    public ResponseResult doScanLogin(AppScanLoginParameter parameter) {
        // 判断二维码编号是否存在
        String serverCode = parameter.getServerCode();
        Boolean hasKey = redisTemplate.hasKey(serverCode);
        if (hasKey == null || !hasKey) {
            return ResponseResult.result(ResponseResultStatus.QR_CODE_NOT_EXIST);
        }

        ResponseResultStatus status = loginVerifier.nameAndIdentityVerifier.apply(parameter.getUserName(), parameter.getCardCode());
        if (status.isFail()) {
            return ResponseResult.result(status);
        }

        // 到这里说明传递的用户信息是存在的，于是将用户的身份证和二维码编号绑定起来
        redisTemplate.opsForValue().set(serverCode, LoginType.APP_SCAN_LOGIN.join(parameter.getCardCode()));

        // 将loginType和LoginToken返回给调用者
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("loginType", LoginType.APP_SCAN_LOGIN.name().toUpperCase());
        jsonObject.put("loginToken", UUID.randomUUID().toString());
        jsonObject.put("webPath", "/web/doctor/index");

        return ResponseResult.mockSuccess().data(jsonObject);
    }

    @Autowired
    private UserinfoService userinfoService;

    // 医生端扫码登录验证
    public ResponseResult doDoctorScanLogin(DoctorScanLoginParameter parameter) {
        // 查询用户的信息是否存在
        String cardCode = parameter.getCardCode();
        LoginSuccessUserinfo userinfo = userinfoService.findByIdentity(cardCode);
        if (userinfo == null) {
            return ResponseResult.result(ResponseResultStatus.USERINFO_NOT_EXIST);
        }

        // 判断用户的信息是否匹配
        if (!Objects.equals(userinfo.getName(), parameter.getUserName())) {
            return ResponseResult.result(ResponseResultStatus.USERINFO_IDENTITY_NAME_MISMATCH);
        }

        // 到这里说明用户信息校验通过，并且需要loginToken并返回给调用者
        // 保存loginToken和loginType保存到redis中，等页面请求登录的时候读取这些信息来校验是否可登录
        String loginToken = UUID.randomUUID().toString();
        String value = LoginType.DOCTOR_SCAN_LOGIN.join(cardCode);
        redisTemplate.opsForValue().set(loginToken, value, facilitateProperties.getLoginTimeOut(), TimeUnit.SECONDS);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("loginToken", loginToken);
        jsonObject.put("loginType", LoginType.DOCTOR_SCAN_LOGIN.name());
        jsonObject.put("webPath", "/web/doctor/index");

        return ResponseResult.mockSuccess().data(jsonObject);
    }

    // 退出系统
    public ResponseResult doLogout() {
        return ResponseResult.mockSuccess();
    }
}
