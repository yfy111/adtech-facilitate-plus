package cn.com.adtech.domain.captcha.sms;

import lombok.Data;

/**
 * @Description
 * @Author chenguangxue
 * @CreateDate 2018/11/7 10:43
 */
@Data
public class SmsSendResult {

    private String res_code;
    private String res_message;
    private String identifier;
}
