package cn.com.adtech.service;

import cn.com.adtech.component.CacheKey;
import cn.com.adtech.component.EventPublisher;
import cn.com.adtech.component.UserinfoService;
import cn.com.adtech.domain.login.LoginSuccessUserinfo;
import cn.com.adtech.domain.password.change.ChangePasswordParameter;
import cn.com.adtech.domain.password.change.FirstLoginChangePasswordParameter;
import cn.com.adtech.domain.password.reset.ResetPasswordParameter;
import cn.com.adtech.enums.ResponseResultStatus;
import cn.com.adtech.event.LoginSuccessEvent;
import cn.com.adtech.stereotype.ResponseResult;
import cn.com.adtech.table.DataTables;
import cn.com.adtech.table.DataTables.Userinfo;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Description 密码业务的处理流程
 * @Author chenguangxue
 * @CreateDate 2018/11/2 14:16
 */
@Service
@Transactional
public class PasswordService {

    @Autowired
    @Qualifier("primaryJooq")
    private DSLContext jooq;
    @Autowired
    private CacheKey cacheKey;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private EventPublisher eventPublisher;
    @Autowired
    private UserinfoService userinfoService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    public ResponseResult doFirstLoginChangePassword(FirstLoginChangePasswordParameter parameter) {
        if (!Objects.equals(parameter.getNewPassword(), parameter.getConfirmPassword())) {
            return ResponseResult.result(ResponseResultStatus.TWICE_PASSWORD_MISMATCH);
        }

        // 新密码不能为身份证的后六位
        String last = parameter.getIdentity().substring(12);

        if (Objects.equals(last, parameter.getNewPassword())) {
            return ResponseResult.result(ResponseResultStatus.PASSWORD_EQUAL_IDENTITY_LAST_6);
        }
        String appId = parameter.getAppId();

        // 修改密码，并且将登录次数加1
        int execute = jooq.update(DataTables.t_userinfo)
                .set(Userinfo.password, parameter.getNewPassword())
                .set(Userinfo.loginCount, 1)
                .where(Userinfo.identity.eq(parameter.getIdentity())).execute();

        if (execute == 0) {
            return ResponseResult.result(ResponseResultStatus.PASSWORD_CHANGE_FAIL);
        }
        else {
            // 修改成功的情况下，触发登录成功事件
            String key = cacheKey.loginToken.apply(request.getSession().getId());
            LoginSuccessEvent event = new LoginSuccessEvent(this, parameter.getIdentity(), key, appId);
            eventPublisher.publish(event);
            return ResponseResult.result(ResponseResultStatus.PASSWORD_CHANGE_SUCCESS);
        }
    }

    // 修改密码
    public ResponseResult doChangePassword(ChangePasswordParameter parameter, LoginSuccessUserinfo userinfo) {
        // 校验旧密码是否正确
        Result<Record1<String>> result = jooq.select(Userinfo.password).from(DataTables.t_userinfo)
                .where(Userinfo.identity.eq(userinfo.getIdentity())).fetch();
        if (result.isEmpty() || !result.get(0).value1().equals(parameter.getOldPassword())) {
            return ResponseResult.result(ResponseResultStatus.USER_PASSWORD_MISMATCH);
        }

        // 校验2次输入的密码是否一致
        if (!Objects.equals(parameter.getNewPassword(), parameter.getConfirmPassword())) {
            return ResponseResult.result(ResponseResultStatus.TWICE_PASSWORD_MISMATCH);
        }

        // 校验新密码不能是身份证后6位
        String last = userinfo.getIdentity().substring(12);
        if (Objects.equals(last, parameter.getNewPassword())) {
            return ResponseResult.result(ResponseResultStatus.PASSWORD_EQUAL_IDENTITY_LAST_6);
        }

        // 修改密码，并且将登录次数加1
        int execute = jooq.update(DataTables.t_userinfo)
                .set(Userinfo.password, parameter.getNewPassword())
                .where(Userinfo.identity.eq(userinfo.getIdentity())).execute();

        if (execute == 0) {
            return ResponseResult.result(ResponseResultStatus.PASSWORD_CHANGE_FAIL);
        }
        else {
            return ResponseResult.result(ResponseResultStatus.PASSWORD_CHANGE_SUCCESS);
        }
    }

    // 重置密码
    public ResponseResult doResetPassword(ResetPasswordParameter parameter) {
        // 校验短信验证码是否正确
        String smsCaptchaKey = cacheKey.smsCaptcha.apply(request.getSession().getId());
        String smsCaptchaInCache = redisTemplate.opsForValue().get(smsCaptchaKey);
        if (!Objects.equals(smsCaptchaInCache, parameter.getSmsCaptcha())) {
            return ResponseResult.result(ResponseResultStatus.SMS_CAPTCHA_MISMATCH);
        }

        // 校验新密码不能是身份证后6位
        String last = parameter.getIdentityCard().substring(12);
        if (Objects.equals(last, parameter.getNewPassword())) {
            return ResponseResult.result(ResponseResultStatus.PASSWORD_EQUAL_IDENTITY_LAST_6);
        }

        return ResponseResult.mockSuccess();
    }
}
