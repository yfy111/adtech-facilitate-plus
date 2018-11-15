package cn.com.adtech.domain.medicalinformation.response;

import cn.com.adtech.annotation.DesensitizationIdentity;
import cn.com.adtech.annotation.DesensitizationProperty;
import cn.com.adtech.util.CommonVariable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 住院病案首页
 */
@Data
public class HospitalizationShowData {

    @ApiModelProperty(value = "extid",required = true)
    private String extid;
    @ApiModelProperty(value = "extmpi",required = true)
    private String extmpi;
    @ApiModelProperty(value = "extsid",required = true)
    private String extsid;
    @ApiModelProperty(value = "extspid",required = true)
    private String extspid;
    @ApiModelProperty(value = "医疗机构名称",required = true)
    private String exhdsd0202239;
    @ApiModelProperty(value = "科室名称",required = true)
    private String exhdsd0202314;
    @ApiModelProperty(value = "门急诊号",required = true)
    private String hdsd0003025;
    @ApiModelProperty(value = "健康卡号",required = true)
    private String exhdsd0202249;
    @ApiModelProperty(value = "入院日期时间",required = true)
    private String hdsd0012096;
    @ApiModelProperty(value = "入院科别",required = true)
    private String hdsd0012095;
    @ApiModelProperty(value = "入院病房",required = true)
    private String hdsd0012094;
    @ApiModelProperty(value = "出院日期时间",required = true)
    private String hdsd0011019;
    @ApiModelProperty(value = "患者姓名",required = true)
    private String hdsd0011110;
    @ApiModelProperty(value = "出生日期",required = true)
    private String exhdsd0202261;
    @ApiModelProperty(value = "年龄",required = true)
    private String hdsd0011079;
    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_SEX)
    @ApiModelProperty(value = "性别",required = true)
    private String exhdsd0202260;
    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_NATIONALITY)
    @ApiModelProperty(value = "籍贯",required = true)
    private String exhdsd0202287;
    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_NATION)
    @ApiModelProperty(value = "民族",required = true)
    private String exhdsd0202263;
    @DesensitizationProperty
    @ApiModelProperty(value = "身份证件号码",required = true)
    @DesensitizationIdentity
    private String exhdsd0202258;
    @ApiModelProperty(value = "电话号码",required = true)
    private String exhdsd0202257;
    @ApiModelProperty(value = "实际住院天数",required = true)
    private String hdsd0011087;
    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_BLOOD_TYPE)
    @ApiModelProperty(value = "ABO血型",required = true)
    private String hdsd0012003;
    @ApiModelProperty(value = "主治医师签名",required = true)
    private String hdsd0012165;
    @ApiModelProperty(value = "住院医师签名",required = true)
    private String hdsd0012168;
    @ApiModelProperty(value = "责任护士签名",required = true)
    private String hdsd0012135;
    @ApiModelProperty(value = "手术及操作日期",required = true)
    private String hdsd0011091;
    @ApiModelProperty(value = "手术及操作名称",required = true)
    private String hdsd0011090;
    @ApiModelProperty(value = "手术者名称",required = true)
    private String hdsd0011094;
    @ApiModelProperty(value = "麻醉医师姓名",required = true)
    private String hdsd0011074;
    @ApiModelProperty(value = "过敏药物",required = true)
    private String hdsd0012044;
    @ApiModelProperty(value = "门（急）诊诊断名称",required = true)
    private String hdsd0012083;
    @ApiModelProperty(value = "主要诊断",required = true)
    private String exhdsd0203148;
}
