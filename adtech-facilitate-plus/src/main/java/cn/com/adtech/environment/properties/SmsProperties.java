package cn.com.adtech.environment.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author chenguangxue
 * @CreateDate 2018/11/7 09:43
 */
@Configuration
@ConfigurationProperties(prefix = "facilitate.sms")
@Data
public class SmsProperties {

    private String url = "http://www.ke400.com:8001/";
    private String method = "interface/sendSms";

    private String account = "mt6235";
    private String password = "jg2wtv";

    // 短信验证码的位数
    private int captchaCount = 6;

    // 短信验证码的格式
    private String captchaContent = "您的个人健康档案查询验证码为：%s";
}
