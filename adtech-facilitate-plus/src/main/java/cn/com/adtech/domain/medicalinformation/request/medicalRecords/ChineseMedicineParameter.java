package cn.com.adtech.domain.medicalinformation.request.medicalRecords;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 中药处方参数
 */
@Data
public class ChineseMedicineParameter {
    @ApiModelProperty(value="extsid",required = true)
    private String extsid;
}
