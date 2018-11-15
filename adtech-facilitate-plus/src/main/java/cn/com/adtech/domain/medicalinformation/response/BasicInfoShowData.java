package cn.com.adtech.domain.medicalinformation.response;

import cn.com.adtech.annotation.DesensitizationIdentity;
import cn.com.adtech.domain.medicalinformation.MedicalInformationShowData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 基本信息的显示数据模型
 * @Author chenguangxue
 * @CreateDate 2018/11/2 16:43
 */
@Data
public class BasicInfoShowData extends MedicalInformationShowData {

    @ApiModelProperty(value = "extsid",required = true)
    private String extsid;
    @ApiModelProperty(value = "extmpi",required = true)
    private String extmpi;
    @ApiModelProperty(value = "城乡居民健康档案编号",required = true)
    private String hdsd0001001;
    @ApiModelProperty(value = "本人姓名",required = true)
    private String hdsd0001002;
    @ApiModelProperty(value = "性别代码",required = true)
    private String hdsd0001003;
    @ApiModelProperty(value = "出生日期",required = true)
    private String hdsd0001004;
    @ApiModelProperty(value = "身份证件类型代码",required = true)
    private String hdsd0001005;
    @ApiModelProperty(value = "身份证件号码",required = true)
    @DesensitizationIdentity
    private String hdsd0001006;
    @ApiModelProperty(value = "工作单位名称",required = true)
    private String hdsd0001007;
    @ApiModelProperty(value = "本人电话号码",required = true)
    private String hdsd0001008;
    @ApiModelProperty(value = "联系人姓名",required = true)
    private String hdsd0001009;
    @ApiModelProperty(value = "联系人电话号码",required = true)
    private String hdsd0001010;
    @ApiModelProperty(value = "民族",required = true)
    private String hdsd0001012;
    @ApiModelProperty(value = "ABO血型代码",required = true)
    private String hdsd0001013;
    @ApiModelProperty(value = "RH血型代码",required = true)
    private String hdsd0001014;
    @ApiModelProperty(value = "学历代码",required = true)
    private String hdsd0001015;
    @ApiModelProperty(value = "职业类别代码",required = true)
    private String hdsd0001016;
    @ApiModelProperty(value = "婚姻状况代码",required = true)
    private String hdsd0001017;
    @ApiModelProperty(value = "药物过敏史标志",required = true)
    private String hdsd0001019;
    @ApiModelProperty(value = "遗传性疾病史",required = true)
    private String hdsd0001035;
    @ApiModelProperty(value = "建档人",required = true)
    private String exhdsd0001022;
    @ApiModelProperty(value = "建档单位名称",required = true)
    private String exhdsd0001027;
    @ApiModelProperty(value = "药物过敏源代码",required = true)
    private String hdsd0001020;
    @ApiModelProperty(value = "家族性疾病名称代码",required = true)
    private String hdsd0001033;
    @ApiModelProperty(value = "家族性疾病名称",required = true)
    private String exhdsd0001003;
    @ApiModelProperty(value = "残疾情况代码",required = true)
    private String hdsd0001036;
    @ApiModelProperty(value = "职业病名称代码",required = true)
    private String hdsd0001440;

}
