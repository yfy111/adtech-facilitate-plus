package cn.com.adtech.environment.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author chenguangxue
 * @CreateDate 2018/11/6 17:59
 */
@Configuration
@ConfigurationProperties(prefix = "facilitate.cache")
@Data
public class CacheProperties {

    // 图形验证码的有效期（秒）
    private int imageCaptchaPeriod = 60 * 3;

    // 短信验证码的有效期（秒）
    private int smsCaptchaPeriod = 60 * 2;
}
