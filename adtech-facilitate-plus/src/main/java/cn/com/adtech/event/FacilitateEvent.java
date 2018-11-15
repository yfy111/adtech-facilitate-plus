package cn.com.adtech.event;

import org.springframework.context.ApplicationEvent;

/**
 * @Description
 * @Author chenguangxue
 * @CreateDate 2018/11/7 14:38
 */
public abstract class FacilitateEvent extends ApplicationEvent {

    public FacilitateEvent(Object source) {
        super(source);
    }
}
