package cn.com.adtech.domain.medicalinformation.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 门急诊病历－中医病名
 */
@Data
public class EmergencyDoorChineseNameShowData {

    @ApiModelProperty(value="中医病名",required = true)
    private String exhdsd0202243;
}
