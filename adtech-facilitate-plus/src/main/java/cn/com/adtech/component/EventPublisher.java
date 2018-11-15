package cn.com.adtech.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * @Description 事件发布者
 * @Author chenguangxue
 * @CreateDate 2018/11/7 14:36
 */
@Component
@Slf4j
public class EventPublisher {

    @Autowired
    private WebApplicationContext context;

    public void publish(ApplicationEvent event) {
        context.publishEvent(event);
    }
}
