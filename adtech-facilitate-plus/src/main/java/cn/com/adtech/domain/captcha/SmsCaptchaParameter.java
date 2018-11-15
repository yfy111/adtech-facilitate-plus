package cn.com.adtech.domain.captcha;

import cn.com.adtech.stereotype.RequestParameter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 请求短信验证码的参数
 * @Author chenguangxue
 * @CreateDate 2018/11/2 09:16
 */
@Data
public class SmsCaptchaParameter extends RequestParameter {

    @ApiModelProperty(value = "手机号", required = true)
    private String phone;

    @ApiModelProperty(value = "身份证号", required = true)
    private String identity;

    @ApiModelProperty(value = "姓名", required = true)
    private String realName;
}
