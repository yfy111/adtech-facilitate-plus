package cn.com.adtech.domain.medicalinformation.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 高血压随访参数
 */
@Data
public class HypertensionFollowUpParameter {
    @ApiModelProperty(value = "随访日期", required = true)
    private String checkDate;
    @ApiModelProperty(value = "身份证", required = true)
    private String identityCard;
}
