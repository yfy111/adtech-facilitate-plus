package cn.com.adtech.domain.medicalinformation.request.medicalRecords;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 西药处方记录参数
 */
@Data
public class WesternPrescriptionParameter {
    @ApiModelProperty(value="extsid",required = true)
    private String extsid;
}
