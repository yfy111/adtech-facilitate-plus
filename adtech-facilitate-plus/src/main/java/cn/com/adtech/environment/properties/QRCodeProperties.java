package cn.com.adtech.environment.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author chenguangxue
 * @CreateDate 2018/11/12 18:02
 */
@Component
@ConfigurationProperties(prefix = "facilitate.qr-code")
@Data
public class QRCodeProperties {

    private boolean includeLogo = true;

    // 这是logo路径，正式环境的时候需要专门配置一个路径
    private String logoUrl = "/Users/cgx/Downloads/qr-code-logo.png";
}
