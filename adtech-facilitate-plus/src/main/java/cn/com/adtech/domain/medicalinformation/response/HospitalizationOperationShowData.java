package cn.com.adtech.domain.medicalinformation.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 西医病案首页-手术
 */
@Data
public class HospitalizationOperationShowData {

    @ApiModelProperty(value = "手术及操作日期", required = true)
    private String hdsd0011091;
    @ApiModelProperty(value = "手术及操作名称", required = true)
    private String hdsd0011090;
    @ApiModelProperty(value = "手术者名称", required = true)
    private String hdsd0011094;
    @ApiModelProperty(value = "麻醉医师姓名", required = true)
    private String hdsd0011074;
}
