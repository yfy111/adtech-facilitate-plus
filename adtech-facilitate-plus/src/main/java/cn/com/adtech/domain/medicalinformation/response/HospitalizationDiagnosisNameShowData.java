package cn.com.adtech.domain.medicalinformation.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 西医病案首页-门诊诊断名称
 */
@Data
public class HospitalizationDiagnosisNameShowData {

    @ApiModelProperty(value = "门诊诊断名称", required = true)
    private String hdsd0012083;
}
