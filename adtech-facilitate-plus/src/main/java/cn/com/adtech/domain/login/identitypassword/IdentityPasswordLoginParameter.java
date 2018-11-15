package cn.com.adtech.domain.login.identitypassword;

import cn.com.adtech.stereotype.RequestParameter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 身份证和密码方式登录
 * @Author chenguangxue
 * @CreateDate 2018/11/2 11:47
 */
@Data
public class IdentityPasswordLoginParameter extends RequestParameter {

    @ApiModelProperty(value = "姓名", required = true)
    private String realName;

    @ApiModelProperty(value = "身份证号", required = true)
    private String identityCard;

    @ApiModelProperty(value = "查询密码", required = true)
    private String password;

    @ApiModelProperty(value = "验证码", required = true)
    private String imageCaptcha;

    @ApiModelProperty(value = "验证码编号", required = true)
    private String imageCaptchaKey;

    @ApiModelProperty(value = "app编号", required = true)
    private String appId;

    @ApiModelProperty(value = "医疗机构编号")
    private String institutionCode;
}
