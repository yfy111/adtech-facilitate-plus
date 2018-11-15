package cn.com.adtech.environment.config;

import org.jooq.DSLContext;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;
import java.util.function.Function;

/**
 * @Description
 * @Author chenguangxue
 * @CreateDate 2018/11/5 17:02
 */
@Configuration
public class JooqConfiguration {

    @Autowired
    @Qualifier(value = "primaryDataSource")
    private DataSource primaryDataSource;

    @Autowired
    @Qualifier(value = "secondDataSource")
    private DataSource secondDataSource;

    private Function<DataSource, DefaultDSLContext> dsl = dataSource -> {
        DataSourceConnectionProvider provider = new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
        jooqConfiguration.set(provider);
        return new DefaultDSLContext(jooqConfiguration);
    };

    // 提供可读取的jooq
    @Bean
    @Qualifier(value = "primaryJooq")
    public DSLContext primaryJooq() {
        return dsl.apply(primaryDataSource);
    }

    // 提供只读的jooq
    @Bean
    @Qualifier(value = "secondJooq")
    public DSLContext secondJooq() {
        return dsl.apply(secondDataSource);
    }
}
