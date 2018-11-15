package cn.com.adtech.environment.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description
 * @Author chenguangxue
 * @CreateDate 2018/11/5 17:41
 */
@Component
@ConfigurationProperties(prefix = "spring.redis.cluster")
@Data
public class RedisClusterProperties {

    private List<String> nodes;
}
