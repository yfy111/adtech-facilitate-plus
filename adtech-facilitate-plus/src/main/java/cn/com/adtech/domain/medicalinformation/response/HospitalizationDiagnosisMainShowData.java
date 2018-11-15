package cn.com.adtech.domain.medicalinformation.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 西医病案首页-主要诊断
 */
@Data
public class HospitalizationDiagnosisMainShowData {

    @ApiModelProperty(value = "主要诊断", required = true)
    private String exhdsd0203148;
}
