package cn.com.adtech.component;

import cn.com.adtech.domain.captcha.UserinfoThreeElementsParameter;
import cn.com.adtech.enums.ResponseResultStatus;
import cn.com.adtech.stereotype.ResultStatus;
import cn.com.adtech.table.DataTables;
import cn.com.adtech.table.DataTables.Userinfo;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description 登录的验证器
 * @Author chenguangxue
 * @CreateDate 2018/11/6 18:17
 */
@Component
@Slf4j
public class LoginVerifier {

    @Autowired
    @Qualifier("primaryJooq")
    private DSLContext jooq;
    @Autowired
    private CacheKey cacheKey;
    @Autowired
    private StringRedisTemplate redisTemplate;

    // 校验身份证是否存在、身份证和姓名是否匹配
    public BiFunction<String, String, ResponseResultStatus> nameAndIdentityVerifier = (name, identity) -> {
        Result<Record2<String, String>> result = jooq.select(Userinfo.realName, Userinfo.identity).from(DataTables.t_userinfo)
                .where(Userinfo.identity.eq(identity)).fetch();
        if (result.isEmpty()) {
            return ResponseResultStatus.USERINFO_NOT_EXIST;
        }

        // 判断姓名和身份证是否匹配
        boolean anyMatch = result.stream().anyMatch(record2 -> Objects.equals(record2.getValue(Userinfo.realName), name));
        if (anyMatch) {
            return ResponseResultStatus.USERINFO_CORRECT;
        }
        else {
            return ResponseResultStatus.USERINFO_IDENTITY_NAME_MISMATCH;
        }
    };

    // 验证三要素信息是否匹配
    public Function<UserinfoThreeElementsParameter, ResultStatus> verifyUserThreeElements = parameter -> {
        // 判断信息是否存在
        Result<Record2<String, String>> result = jooq.select(Userinfo.phone, Userinfo.realName).from(DataTables.t_userinfo)
                .where(Userinfo.identity.eq(parameter.getIdentity())).fetch();

        // 用身份证无法查询到任何记录
        if (result.isEmpty()) {
            return ResponseResultStatus.USER_NOT_EXIST;
        }

        // 找出姓名和身份证匹配的记录
        List<Record2<String, String>> nameList = result.stream()
                .filter(record2 -> Objects.equals(record2.get(Userinfo.realName), parameter.getName()))
                .collect(Collectors.toList());
        if (nameList.isEmpty()) {
            return ResponseResultStatus.USERINFO_IDENTITY_NAME_MISMATCH;
        }

        // 判断是否有记录的手机号和参数中的手机号相同
        boolean anyMatch = nameList.stream().anyMatch(record2 -> Objects.equals(record2.get(Userinfo.phone), parameter.getPhone()));

        if (anyMatch) {
            return ResponseResultStatus.USERINFO_CORRECT;
        }
        else {
            return ResponseResultStatus.USERINFO_PHONE_MISMATCH;
        }
    };

    // 验证短信验证码是否正确
    public BiFunction<String, String, ResultStatus> verifySmsCaptcha = (phone, captcha) -> {
        String key = cacheKey.smsCaptcha.apply(phone);
        String captchaInCache = redisTemplate.opsForValue().get(key);

        if (captchaInCache == null) {
            return ResponseResultStatus.SMS_CAPTCHA_EXPIRE;
        }
        else if (!captchaInCache.equals(captcha)) {
            return ResponseResultStatus.SMS_CAPTCHA_MISMATCH;
        }
        else {
            return ResponseResultStatus.SMS_CAPTCHA_CORRECT;
        }
    };

    // 校验图形验证码是否正确
    public BiFunction<String, String, ResultStatus> verifyImageCaptcha = (captchaKey, captcha) -> {
        String key = cacheKey.imageCaptcha.apply(captchaKey);
        String captchaInCache = redisTemplate.opsForValue().get(key);

        log.info("传递的请求key：{}", captchaKey);
        log.info("缓存的验证码为：{}", captchaInCache);
        log.info("传递的验证码为：{}", captcha);

        if (captchaInCache == null) {
            return ResponseResultStatus.IMAGE_CAPTCHA_EXPIRE;
        }
        else if (!captchaInCache.equalsIgnoreCase(captcha)) {
            return ResponseResultStatus.IMAGE_CAPTCHA_MISMATCH;
        }
        else {
            return ResponseResultStatus.IMAGE_CAPTCHA_CORRECT;
        }
    };

    // 校验是否为第一次登录本系统
    public Function<String, ResultStatus> firstLogin = identity -> {
        List<Integer> result = jooq.select(Userinfo.loginCount).from(DataTables.t_userinfo).where(Userinfo.identity.eq(identity))
                .fetch(Userinfo.loginCount);

        if (result.isEmpty()) {
            return ResponseResultStatus.USERINFO_NOT_EXIST;
        }

        Integer integer = result.get(0);
        return integer == 0 ? ResponseResultStatus.IS_FIRST_LOGIN : ResponseResultStatus.IS_NOT_FIRST_LOGIN;
    };
}
