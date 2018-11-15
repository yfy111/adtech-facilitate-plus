package cn.com.adtech.domain.login.directlogin;

import lombok.Data;

/**
 * @Description 免密方式登录验证的请求参数
 * @Author chenguangxue
 * @CreateDate 2018/11/14 15:49
 */
@Data
public class DirectLoginParameter {

    private String userName;
    private String cardCode;
    private String systemCode;
}
