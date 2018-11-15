package cn.com.adtech.domain.medicalinformation.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 门急诊病历-既往史
 */
@Data
public class EmergencyDoorPassHistoryShowData {

    @ApiModelProperty(value="既往史",required = true)
    private String hdsd0003025;
}
