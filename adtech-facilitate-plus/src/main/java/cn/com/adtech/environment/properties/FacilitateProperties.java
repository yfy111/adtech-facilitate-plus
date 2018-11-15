package cn.com.adtech.environment.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 项目的环境变量
 * @Author chenguangxue
 * @CreateDate 2018/11/1 22:06
 */
@Configuration
@ConfigurationProperties(prefix = "facilitate")
@EnableConfigurationProperties
@Data
public class FacilitateProperties {

    // 是否使用模拟数据，开发模式中使用
    private boolean useMockData = false;

    // 系统名称
    private String projectName = "便民查询系统";

    //用户过期登录时间
    private int loginTimeOut = 3600;

    private String baseUrl = "http://127.0.0.1:9082";
}
