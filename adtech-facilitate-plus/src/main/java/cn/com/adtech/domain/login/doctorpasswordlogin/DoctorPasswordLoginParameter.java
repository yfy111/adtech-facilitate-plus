package cn.com.adtech.domain.login.doctorpasswordlogin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 医生端密码登录的请求参数
 * @Author chenguangxue
 * @CreateDate 2018/11/7 17:20
 */
@Data
public class DoctorPasswordLoginParameter {

    @ApiModelProperty(value = "身份证", required = true)
    private String identity;

    @ApiModelProperty(value = "密码", required = true)
    private String password;
}
