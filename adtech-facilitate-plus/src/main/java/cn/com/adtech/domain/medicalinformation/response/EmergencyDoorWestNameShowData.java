package cn.com.adtech.domain.medicalinformation.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 门急诊病历-西医诊断
 */
@Data
public class EmergencyDoorWestNameShowData {

    @ApiModelProperty(value="西医诊断",required = true)
    private String exhdsd0202243;
}
