package cn.com.adtech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableConfigurationProperties
@EnableScheduling
public class FacilitatePlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(FacilitatePlusApplication.class, args);
    }
}
