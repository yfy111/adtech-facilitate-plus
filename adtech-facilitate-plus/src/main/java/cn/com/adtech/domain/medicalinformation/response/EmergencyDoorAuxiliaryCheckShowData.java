package cn.com.adtech.domain.medicalinformation.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 门急诊病历-辅助检查项目
 */
@Data
public class EmergencyDoorAuxiliaryCheckShowData {

    @ApiModelProperty(value = "辅助检查项目", required = true)
    private String hdsd0003013;

    @ApiModelProperty(value = "辅助检查结果", required = true)
    private String hdsd0003012;
}
