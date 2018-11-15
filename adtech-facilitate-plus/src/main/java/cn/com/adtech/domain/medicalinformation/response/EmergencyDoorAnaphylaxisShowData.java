package cn.com.adtech.domain.medicalinformation.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 门急诊病历-过敏史
 */
@Data
public class EmergencyDoorAnaphylaxisShowData {

    @ApiModelProperty(value="过敏史",required = true)
    private String hdsd0003014;
}
