package cn.com.adtech.domain.medicalinformation.request;

import cn.com.adtech.domain.medicalinformation.MedicalInformationShowData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 出生医学证明参数
 * @CreateDate 2018/11/2 16:53
 */
@Data
public class BirthCertificateParameter extends MedicalInformationShowData {

    @ApiModelProperty(value = "真实姓名", required = true)
    private String realName;

    @ApiModelProperty(value = "身份证", required = true)
    private String identityCard;

    @ApiModelProperty(value = "家庭成员关系名称", required = true)
    private String appellation;
}
