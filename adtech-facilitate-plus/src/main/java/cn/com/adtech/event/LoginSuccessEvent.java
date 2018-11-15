package cn.com.adtech.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @Description 登录成功的事件
 * @Author chenguangxue
 * @CreateDate 2018/11/7 14:31
 */
@Getter
public class LoginSuccessEvent extends ApplicationEvent {

    // 登录用户的身份证
    private final String identity;

    // loginToken的key
    private final String loginTokenKey;
    private final String appId;

    public LoginSuccessEvent(Object source, String identity, String loginTokenKey,String appId) {
        super(source);
        this.identity = identity;
        this.loginTokenKey = loginTokenKey;
        this.appId=appId;
    }
}
