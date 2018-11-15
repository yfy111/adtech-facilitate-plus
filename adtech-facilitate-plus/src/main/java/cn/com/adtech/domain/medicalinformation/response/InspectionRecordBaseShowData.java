package cn.com.adtech.domain.medicalinformation.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 检验记录基本信息
 */
@Data
public class InspectionRecordBaseShowData {
    @ApiModelProperty(value = "extid",required = true)
    private String extid;
    @ApiModelProperty(value = "extsid",required = true)
    private String extsid;
    @ApiModelProperty(value = "extmpi",required = true)
    private String extmpi;
    @ApiModelProperty(value = "患者姓名",required = true)
    private String exhdsd0202250;
    @ApiModelProperty(value = "患者性别",required = true)
    private String exhdsd0202251;
    @ApiModelProperty(value = "年龄（岁）",required = true)
    private String exhdsd0202253;
    @ApiModelProperty(value = "门（急）诊号标识",required = true)
    private String exhdsd0202247;
    @ApiModelProperty(value = "住院号",required = true)
    private String exhdsd0202193;
    @ApiModelProperty(value = "送检病区名称",required = true)
    private String exhdsd0202216;
    @ApiModelProperty(value = "送检科室名称",required = true)
    private String exhdsd0202214;
    @ApiModelProperty(value = "检验报告单编号",required = true)
    private String hdsd0005040;
    @ApiModelProperty(value = "标本类别",required = true)
    private String hdsd0005004;
    @ApiModelProperty(value = "标本采样日期时间",required = true)
    private String hdsd0005002;
    @ApiModelProperty(value = "检验报告结果",required = true)
    private String hdsd0005042;
    @ApiModelProperty(value = "检验报告科室名称",required = true)
    private String hdsd0005043;
    @ApiModelProperty(value = "检验技师名称",required = true)
    private String hdsd0005049;
    @ApiModelProperty(value = "检验日期",required = true)
    private String exhdsd0202231;
    @ApiModelProperty(value = "检验报告日期",required = true)
    private String hdsd0005044;

}
