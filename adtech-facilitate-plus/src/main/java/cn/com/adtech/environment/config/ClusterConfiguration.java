package cn.com.adtech.environment.config;

import cn.com.adtech.environment.properties.RedisClusterProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * @Description
 * @Author chenguangxue
 * @CreateDate 2018/11/5 17:42
 */
@Configuration
public class ClusterConfiguration {

    @Autowired
    private RedisClusterProperties properties;

    @Bean
    public RedisConnectionFactory connectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory(new RedisClusterConfiguration(properties.getNodes()));
        return factory;
    }
}
