package cn.com.adtech.domain.medicalinformation.response;

import cn.com.adtech.annotation.DesensitizationProperty;
import cn.com.adtech.util.CommonVariable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 检查记录
 */
@Data
public class CheckRecordShowData {

    @ApiModelProperty(value = "检查报告单号",required = true)
    private String exhdsd0202279;
    @ApiModelProperty(value = "电子申请单编号",required = true)
    private String hdsd0005016;
    @ApiModelProperty(value = "患者姓名",required = true)
    private String exhdsd0202250;
    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_SEX)
    @ApiModelProperty(value = "性别",required = true)
    private String exhdsd0202251;
    @ApiModelProperty(value = "年龄(岁)",required = true)
    private String exhdsd0202253;
    @ApiModelProperty(value = "门（急）诊号",required = true)
    private String exhdsd0202155;
    @ApiModelProperty(value = "住院号",required = true)
    private String exhdsd0202156;
    @ApiModelProperty(value = "申请科室名称",required = true)
    private String hdsd0005036;
    @ApiModelProperty(value = "检查申请科室",required = true)
    private String exhdsd0202288;
    @ApiModelProperty(value = "检查类别",required = true)
    private String hdsd0005033;
    @ApiModelProperty(value = "检查项目",required = true)
    private String exhdsd0202159;
    @ApiModelProperty(value = "疾病诊断--西医诊断代码",required = true)
    private String exhdsd0202169;
    @ApiModelProperty(value = "检查医师签名日期时间--检查报告日期",required = true)
    private String exhdsd0202283;
    @ApiModelProperty(value = "病区名称",required = true)
    private String exhdsd1001024;
    @ApiModelProperty(value = "病房号",required = true)
    private String exhdsd1001025;
    @ApiModelProperty(value = "病床号",required = true)
    private String exhdsd1001026;
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
    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_ANAESTHESIA)
    @ApiModelProperty(value = "麻醉方法--麻醉方式代码",required = true)
    private String hdsd0005060;
    @ApiModelProperty(value = "麻醉医师签名",required = true)
    private String hdsd0005062;

}
