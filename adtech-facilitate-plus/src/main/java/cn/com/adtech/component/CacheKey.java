package cn.com.adtech.component;

import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * @Description
 * @Author chenguangxue
 * @CreateDate 2018/11/6 17:55
 */
@Component
public class CacheKey {

    public Function<String, String> imageCaptcha = key -> {
        return "IMAGE_CAPTCHA_KEY_" + key;
    };

    public Function<String, String> smsCaptcha = phone -> {
        return "SMS_CAPTCHA_KEY_" + phone;
    };

    // 登录用户loginToken的key
    public Function<String, String> loginToken = sessionId -> {
        return "LOGIN_TOKEN_KEY_" + sessionId;
    };

    // 二维码的序列号key
    public Function<String, String> qrCodeSerialNumberKey = sessionId -> {
        return "QR_CODE_SERIAL_NUMBER_KEY_" + sessionId;
    };
}
