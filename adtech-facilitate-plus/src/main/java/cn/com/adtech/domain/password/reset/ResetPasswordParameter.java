package cn.com.adtech.domain.password.reset;

import cn.com.adtech.stereotype.RequestParameter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 重置密码的请求参数
 * @Author chenguangxue
 * @CreateDate 2018/11/2 14:22
 */
@Data
public class ResetPasswordParameter extends RequestParameter {

    @ApiModelProperty(value = "身份证号", required = true)
    private String identityCard;

    @ApiModelProperty(value = "姓名")
    private String realName;

    @ApiModelProperty(value = "手机号")
    private String userPhone;

    @ApiModelProperty(value = "短信验证码", required = true)
    private String smsCaptcha;

    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;
}
