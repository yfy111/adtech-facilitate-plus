package cn.com.adtech.domain.vo;

import lombok.Data;

/**
 * @Description 登录页面显示的用户信息模型
 * @Author chenguangxue
 * @CreateDate 2018/11/1 22:43
 */
@Data
public class LoginUserinfo {

    private String realName;
    private String identity;
    private String phone;
    private String smsCaptcha;
    private String password;
    private String imageCaptcha;

    // 创建一个默认的登录用户，包含默认的登录信息
    public static LoginUserinfo mock() {
        LoginUserinfo mockObject = new LoginUserinfo();
        mockObject.realName = "AA";
        mockObject.smsCaptcha = "";
        mockObject.identity = "510222196210200315";
        mockObject.password = "123456";
        mockObject.phone = "17338600226";
        mockObject.imageCaptcha = "";
        return mockObject;
    }
}
