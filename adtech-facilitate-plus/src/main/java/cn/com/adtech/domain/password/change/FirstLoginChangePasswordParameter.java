package cn.com.adtech.domain.password.change;

import lombok.Data;

/**
 * @Description
 * @Author chenguangxue
 * @CreateDate 2018/11/8 09:08
 */
@Data
public class FirstLoginChangePasswordParameter {

    private String identity;
    private String newPassword;
    private String confirmPassword;
    private String appId;
}
