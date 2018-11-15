package cn.com.adtech.domain.medicalinformation.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 检查记录-就诊信息
 */
@Data
public class CheckRecordVisitShowData {

    @ApiModelProperty(value = "病区名称",required = true)
    private String exhdsd1001024;
    @ApiModelProperty(value = "病房号",required = true)
    private String exhdsd1001025;
    @ApiModelProperty(value = "病床号",required = true)
    private String exhdsd1001026;

}
