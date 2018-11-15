package cn.com.adtech.domain.medicalinformation.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 检查记录-检查报告结果
 */
@Data
public class CheckRecordResultShowData {

    @ApiModelProperty(value = "诊疗过程描述",required = true)
    private String hdsd0005073;
    @ApiModelProperty(value = "检查方法名称",required = true)
    private String hdsd0005030;
    @ApiModelProperty(value = "报告医师签名",required = true)
    private String exhdsd0202182;
    @ApiModelProperty(value = "检查技师签名",required = true)
    private String hdsd0005031;
    @ApiModelProperty(value = "检查医师签名",required = true)
    private String hdsd0005038;
    @ApiModelProperty(value = "检查日期",required = true)
    private String hdsd0005034;

}
