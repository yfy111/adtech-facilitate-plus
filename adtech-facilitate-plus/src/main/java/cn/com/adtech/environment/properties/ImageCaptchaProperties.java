package cn.com.adtech.environment.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author chenguangxue
 * @CreateDate 2018/11/6 10:40
 */
@Configuration
@ConfigurationProperties(prefix = "facilitate.image.captcha")
@Data
public class ImageCaptchaProperties {

    // 图形验证码使用的字符
    private String characterScope = "23456789QWERTYUPASDFGHJKLZXCVBNM";

    // 图形验证码的字符个数
    private int characterCount = 4;

    // 干扰线的数量
    private int lineCount = 8;

    // 绘制的图形宽度
    private int width = 90;

    // 绘制的图形高度
    private int height = 40;

    // 验证码中的字体大小
    private int fontSize = 30;
}
