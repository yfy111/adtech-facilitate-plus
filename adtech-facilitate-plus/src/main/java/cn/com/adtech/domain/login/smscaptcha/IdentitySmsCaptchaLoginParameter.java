package cn.com.adtech.domain.login.smscaptcha;

import cn.com.adtech.stereotype.RequestParameter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 以身份证、姓名、手机号、短信验证码方式进行登录的请求参数
 * @Author chenguangxue
 * @CreateDate 2018/11/2 13:16
 */
@Data
public class IdentitySmsCaptchaLoginParameter extends RequestParameter {

    @ApiModelProperty(value = "身份证号", required = true)
    private String identityCard;

    @ApiModelProperty(value = "姓名", required = true)
    private String realName;

    @ApiModelProperty(value = "手机号", required = true)
    private String userPhone;

    @ApiModelProperty(value = "短信验证码", required = true)
    private String smsCaptcha;

    @ApiModelProperty(value = "app编号", required = true)
    private String appId;

    @ApiModelProperty(value = "医疗机构编号")
    private String institutionCode;
}
