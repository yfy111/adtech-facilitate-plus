package cn.com.adtech.domain.medicalinformation.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 西医病案首页-过敏药物
 */
@Data
public class HospitalizationAntihistamineShowData {

    @ApiModelProperty(value = "过敏药物", required = true)
    private String hdsd0012044;
}
