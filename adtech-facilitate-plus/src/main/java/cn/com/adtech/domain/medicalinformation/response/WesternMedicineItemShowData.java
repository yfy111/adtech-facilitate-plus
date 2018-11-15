package cn.com.adtech.domain.medicalinformation.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 西药处方-明细
 */
@Data
public class WesternMedicineItemShowData {

    @ApiModelProperty(value = "处方药品组号",required = true)
    private String hdsd0004012;
    @ApiModelProperty(value = "药物规格",required = true)
    private String hdsd0004021;
    @ApiModelProperty(value = "剂型代码",required = true)
    private String hdsd0004022;
    @ApiModelProperty(value = "药物名称",required = true)
    private String hdsd0004023;
    @ApiModelProperty(value = "次剂量",required = true)
    private String hdsd0004024;
    @ApiModelProperty(value = "单位",required = true)
    private String hdsd0004025;
    @ApiModelProperty(value = "使用频次",required = true)
    private String hdsd0004026;
    @ApiModelProperty(value = "使用途径描述",required = true)
    private String exhdsd0201173;
    @ApiModelProperty(value = "用药途径代码",required = true)
    private String hdsd0004027;
    @ApiModelProperty(value = "总剂量",required = true)
    private String hdsd0004028;


}
