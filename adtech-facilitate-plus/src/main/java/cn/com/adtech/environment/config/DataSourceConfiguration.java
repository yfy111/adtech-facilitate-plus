package cn.com.adtech.environment.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @Description
 * @Author chenguangxue
 * @CreateDate 2018/11/5 16:57
 */
@Configuration
public class DataSourceConfiguration {

    // 主数据库是读写库
    @Bean
    @Qualifier(value = "primaryDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primary() {
        return DruidDataSourceBuilder.create().build();
    }

    // 从数据库是只读库
    @Bean
    @Qualifier(value = "secondDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.second")
    public DataSource second() {
        return DruidDataSourceBuilder.create().build();
    }
}
