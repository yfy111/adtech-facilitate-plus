package cn.com.adtech.domain.login.identitypassword;

import lombok.Data;

/**
 * @Description 第三方app使用账号密码方式进行登录校验
 * @Author chenguangxue
 * @CreateDate 2018/11/13 16:19
 */
@Data
public class AppPasswordParameter {

    private String userName;
    private String cardCode;
    private String passWord;
    private String systemCode;
}
