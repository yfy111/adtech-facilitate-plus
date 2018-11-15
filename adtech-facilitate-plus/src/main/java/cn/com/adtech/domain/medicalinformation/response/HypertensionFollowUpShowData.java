package cn.com.adtech.domain.medicalinformation.response;

import cn.com.adtech.annotation.DesensitizationProperty;
import cn.com.adtech.annotation.FieldDisplayProperty;
import cn.com.adtech.domain.medicalinformation.MedicalInformationShowData;
import cn.com.adtech.util.CommonVariable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 高血压随访的显示数据
 * @Author chenguangxue
 * @CreateDate 2018/11/2 16:56
 */
@Data
public class HypertensionFollowUpShowData extends MedicalInformationShowData {
    @ApiModelProperty(value = "extsid",required = true)
    @FieldDisplayProperty(hidden = true)
    private String extsid;
    @ApiModelProperty(value = "extmpi",required = true)
    @FieldDisplayProperty(hidden = true)
    private String extmpi;
    @ApiModelProperty(value = "健康档案编号",required = true)
    private String hdsd0001001;
    @ApiModelProperty(value = "本人姓名",required = true)
    private String hdsd0001002;
    @ApiModelProperty(value = "本次随访日期",required = true)
    private String hdsd0001466;
    @ApiModelProperty(value = "随访方式代码",required = true)
    private String hdsd0001467;
    @ApiModelProperty(value = "心率(次/min)",required = true)
    private String hdsd0001107;
    @ApiModelProperty(value = "收缩压(mmHg)",required = true)
    private String hdsd0001342;
    @ApiModelProperty(value = "舒张压(mmHg)",required = true)
    private String hdsd0001343;
    @ApiModelProperty(value = "身高(cm)",required = true)
    private String hdsd0001055;
    @ApiModelProperty(value = "体重(kg)",required = true)
    private String hdsd0001056;
    @ApiModelProperty(value = "目标体重(kg)",required = true)
    private String hdsd0001209;
    @ApiModelProperty(value = "体质指数",required = true)
    private String hdsd0001058;
    @ApiModelProperty(value = "目标体质指数",required = true)
    private String hdsd0001468;
    @ApiModelProperty(value = "日吸烟量(支)",required = true)
    private String hdsd0001071;
    @ApiModelProperty(value = "日饮酒量(两)",required = true)
    private String hdsd0001075;
    @ApiModelProperty(value = "其他阳性体征",required = true)
    private String hdsd0001469;
    @ApiModelProperty(value = "运动频率代码",required = true)
    private String hdsd0001065;
    @ApiModelProperty(value = "运动时长(min)",required = true)
    private String hdsd0001066;
    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_SALT_UPTAKE)
    @ApiModelProperty(value = "摄盐量分级代码",required = true)
    private String hdsd0001474;
    @ApiModelProperty(value = "辅助检查结果",required = true)
    private String hdsd0001460;
    @ApiModelProperty(value = "辅助检查项目",required = true)
    private String hdsd0001478;
    @ApiModelProperty(value = "检查(测)人员姓名",required = true)
    private String hdsd0001479;
    @ApiModelProperty(value = "检查(测)日期",required = true)
    private String hdsd0001045;
    @ApiModelProperty(value = "药物不良反应描述",required = true)
    private String hdsd0001481;
    @ApiModelProperty(value = "症状名称",required = true)
    private String hdsd0001047;
    @ApiModelProperty(value = "药物名称",required = true)
    private String hdsd0001194;
    @ApiModelProperty(value = "药物使用频率",required = true)
    private String hdsd0001195;
    @ApiModelProperty(value = "药物使用次剂量",required = true)
    private String hdsd0001197;
    @ApiModelProperty(value = "药物使用总量",required = true)
    private String hdsd0001198;
    @FieldDisplayProperty(width = 3)
    @ApiModelProperty(value = "药物使用剂量单位",required = true)
    private String hdsd0001196;

}
