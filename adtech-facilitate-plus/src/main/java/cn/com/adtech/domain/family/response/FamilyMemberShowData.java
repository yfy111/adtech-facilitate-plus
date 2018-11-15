package cn.com.adtech.domain.family.response;

import cn.com.adtech.annotation.DesensitizationIdentity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 家庭成员/授权管理vo
 */
@Data
public class FamilyMemberShowData {
    @ApiModelProperty(value = "ID", required = true)
    private String id;
    @ApiModelProperty(value = "extmpi", required = true)
    private String extmpi;
    @ApiModelProperty(value = "群主ID", required = true)
    private String masterId;
    @ApiModelProperty(value = "家庭成员ID", required = true)
    private String familyUserId;
    @ApiModelProperty(value = "家庭成员称谓", required = true)
    private String appellation;
    @ApiModelProperty(value = "家庭成员姓名", required = true)
    private String familyUsername;
    @ApiModelProperty(value = "家庭成员身份证", required = true)
    @DesensitizationIdentity
    private String familyIdcard;
    @ApiModelProperty(value = "家庭成员电话", required = true)
    private String familyUserphone;
    @ApiModelProperty(value = "验证码", required = true)
    private String verificationCode;

}
