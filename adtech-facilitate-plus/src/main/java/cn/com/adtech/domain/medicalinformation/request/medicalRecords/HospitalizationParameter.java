package cn.com.adtech.domain.medicalinformation.request.medicalRecords;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 住院病案参数
 */
@Data
public class HospitalizationParameter {
    @ApiModelProperty(value="extsid",required = true)
    private String extsid;
}
