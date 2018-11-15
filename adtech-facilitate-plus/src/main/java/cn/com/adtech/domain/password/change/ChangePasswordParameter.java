package cn.com.adtech.domain.password.change;

import cn.com.adtech.stereotype.RequestParameter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 修改密码操作的请求参数
 * @Author chenguangxue
 * @CreateDate 2018/11/2 14:14
 */
@Data
public class ChangePasswordParameter extends RequestParameter {

    @ApiModelProperty(value = "身份证号", required = true)
    private String identityCard;

    @ApiModelProperty(value = "姓名")
    private String realName;

    @ApiModelProperty(value = "旧密码", required = true)
    private String oldPassword;

    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;

    @ApiModelProperty(value = "确认密码", required = true)
    private String confirmPassword;

    private String systemCode;
}
