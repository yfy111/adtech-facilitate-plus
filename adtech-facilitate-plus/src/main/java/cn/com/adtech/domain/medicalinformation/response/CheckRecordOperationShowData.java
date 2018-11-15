package cn.com.adtech.domain.medicalinformation.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 检查记录-手术记录
 */
@Data
public class CheckRecordOperationShowData {

    @ApiModelProperty(value = "麻醉方法--麻醉方式代码",required = true)
    private String hdsd0005060;
    @ApiModelProperty(value = "麻醉医师签名",required = true)
    private String hdsd0005062;

}
