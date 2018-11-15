package cn.com.adtech.domain.medicalinformation.response;

import cn.com.adtech.annotation.DesensitizationProperty;
import cn.com.adtech.domain.medicalinformation.MedicalInformationShowData;
import cn.com.adtech.util.CommonVariable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 门急诊
 */
@Data
public class EmerencyDoorShowData extends MedicalInformationShowData {

    @ApiModelProperty(value = "extid",required = true)
    private String extid;
    @ApiModelProperty(value = "extmpi",required = true)
    private String extmpi;
    @ApiModelProperty(value = "extsid",required = true)
    private String extsid;
    @ApiModelProperty(value = "医疗机构名称",required = true)
    private String exhdsd0202239;
    @ApiModelProperty(value = "科室名称",required = true)
    private String hdsd0002055;
    @ApiModelProperty(value = "门急诊号",required = true)
    private String hdsd0003025;
    @ApiModelProperty(value = "患者姓名",required = true)
    private String exhdsd0202250;
    @ApiModelProperty(value = "出生日期",required = true)
    private String exhdsd0202252;
    @ApiModelProperty(value = "年龄",required = true)
    private String exhdsd0202253;
    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_SEX)
    @ApiModelProperty(value = "性别",required = true)
    private String exhdsd0202251;
    @ApiModelProperty(value = "就诊日期时间",required = true)
    private String hdsd0003023;
    @ApiModelProperty(value = "治则治法",required = true)
    private String hdsd0003056;
    @ApiModelProperty(value = "主诉",required = true)
    private String hdsd0003057;
    @ApiModelProperty(value = "现病史",required = true)
    private String hdsd0003064;
    @ApiModelProperty(value = "中医四诊观察结果",required = true)
    private String hdsd0003060;
    @ApiModelProperty(value = "责任医师姓名",required = true)
    private String exhdsd0203052;
    @ApiModelProperty(value = "过敏史",required = true)
    private String hdsd0003014;
    @ApiModelProperty(value = "既往史",required = true)
    private String history;
    @ApiModelProperty(value = "西医诊断名称",required = true)
    private String exhdsd0202243;
    @ApiModelProperty(value = "中医病名名称",required = true)
    private String chinesedisease;
    @ApiModelProperty(value = "中医证候名称",required = true)
    private String chinesesymptom;
    @ApiModelProperty(value = "辅助检查项目",required = true)
    private String hdsd0003013;
    @ApiModelProperty(value = "辅助检查结果",required = true)
    private String hdsd0003012;

}
