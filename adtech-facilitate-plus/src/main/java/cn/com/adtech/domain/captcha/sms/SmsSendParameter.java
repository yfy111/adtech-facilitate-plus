package cn.com.adtech.domain.captcha.sms;

import lombok.Data;

/**
 * @Description 发送短信的请求参数
 * @Author chenguangxue
 * @CreateDate 2018/11/7 09:55
 */
@Data
public class SmsSendParameter {

    private String account;
    private String timestamp;
    private String access_token;
    private String receiver;
    private String smscontent;
    private String extcode;

    @Override
    public String toString() {
        return "?account=" + account +
                "&timestamp=" + timestamp +
                "&access_token=" + access_token +
                "&receiver=" + receiver +
                "&smscontent=" + smscontent +
                "&extcode=";
    }
}
