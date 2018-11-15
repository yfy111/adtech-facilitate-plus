package cn.com.adtech.domain.family.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 添加家庭成员
 * @Author PYH
 * @CreateDate 2018/11/6 16:31
 */
@Data
public class FamilyMemberAddParameter {

    @ApiModelProperty(value = "群主ID",required = true)
    private String masterId;

    @ApiModelProperty(value = "家庭成员称谓",required = true)
    private String appellation;

    @ApiModelProperty(value="身份证",required = true)
    private String identityCard;

    @ApiModelProperty(value="姓名",required = true)
    private String trueName;

    @ApiModelProperty(value="手机号",required = true)
    private String userPhone;

    @ApiModelProperty(value="手机号验证码",required = true)
    private String phoneCode;

}
