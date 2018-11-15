package cn.com.adtech.domain.medicalinformation.request.medicalRecords;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 门急诊参数
 */
@Data
public class EmergencyDoorMedicalRecordParameter {
    @ApiModelProperty(value="extsid",required = true)
    private String extsid;
}
