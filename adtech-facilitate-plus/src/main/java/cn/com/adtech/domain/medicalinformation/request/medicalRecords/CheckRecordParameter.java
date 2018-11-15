package cn.com.adtech.domain.medicalinformation.request.medicalRecords;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 检查记录参数
 */
@Data
public class CheckRecordParameter {
    @ApiModelProperty(value="extsid",required = true)
    private String extsid;
}
