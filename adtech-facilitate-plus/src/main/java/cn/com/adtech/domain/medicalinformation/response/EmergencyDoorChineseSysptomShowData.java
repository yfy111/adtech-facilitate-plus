package cn.com.adtech.domain.medicalinformation.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 门急诊病历-中医证候
 */
@Data
public class EmergencyDoorChineseSysptomShowData {

    @ApiModelProperty(value="中医证候",required = true)
    private String exhdsd0202243;
}
