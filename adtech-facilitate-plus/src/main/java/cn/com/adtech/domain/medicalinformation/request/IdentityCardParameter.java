package cn.com.adtech.domain.medicalinformation.request;

import cn.com.adtech.domain.medicalinformation.MedicalInformationShowData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 身份证参数
 * @CreateDate 2018/11/2 16:54
 */
@Data
public class IdentityCardParameter extends MedicalInformationShowData {

    @ApiModelProperty(value = "身份证", required = true)
    private String identityCard;

    public static IdentityCardParameter of(String identity) {
        IdentityCardParameter parameter = new IdentityCardParameter();
        parameter.identityCard = identity;
        return parameter;
    }
}
