package cn.com.adtech.domain.baseinfo;

import cn.com.adtech.annotation.DesensitizationIdentity;
import cn.com.adtech.annotation.DesensitizationProperty;
import cn.com.adtech.util.CommonVariable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.util.Date;

/**
 * 基本信息vo
 */
@Data
public class BaseInfoShowData {

    @ApiModelProperty(value = "业务数据本地唯一ID", required = true)
    private String extsid;//业务数据本地唯一ID

    @ApiModelProperty(value = "城乡居民健康档案编号", required = true)
    private String hdsd0001001;//城乡居民健康档案编号

    @ApiModelProperty(value = "建档人", required = true)
    private String exhdsd0001022;//建档人

    @ApiModelProperty(value = "建档单位名称", required = true)
    private String exhdsd0001027;//建档单位名称

    @ApiModelProperty(value = "本人姓名", required = true)
    private String hdsd0001002;//本人姓名

    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_SEX)
    @ApiModelProperty(value = "性别代码", required = true)
    private String hdsd0001003;//性别代码

    @ApiModelProperty(value = "出生日期", required = true)
    private Date hdsd0001004;//出生日期

    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_DOCUMENT_TYPE)
    @ApiModelProperty(value = "身份证件类型代码", required = true)
    private String hdsd0001005;//身份证件类型代码

    @ApiModelProperty(value = "身份证件号码", required = true)
    @DesensitizationIdentity
    private String hdsd0001006;//身份证件号码

    @ApiModelProperty(value = "工作单位名称", required = true)
    private String hdsd0001007;//工作单位名称

    @ApiModelProperty(value = "本人电话号码", required = true)
    private String hdsd0001008;//本人电话号码

    @ApiModelProperty(value = "联系人姓名", required = true)
    private String hdsd0001009;//联系人姓名

    @ApiModelProperty(value = "联系人电话号码", required = true)
    private String hdsd0001010;//联系人电话号码

    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_NATION)
    @ApiModelProperty(value = "民族", required = true)
    private String hdsd0001012;//民族

    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_BLOOD_TYPE)
    @ApiModelProperty(value = "ABO血型代码", required = true)
    private String hdsd0001013;//ABO血型代码

    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_SEX)
    @ApiModelProperty(value = "RH血型代码", required = true)
    private String hdsd0001014;//RH血型代码

    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_EDUCATION)
    @ApiModelProperty(value = "学历代码", required = true)
    private String hdsd0001015;//学历代码

    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_OCCUPATION)
    @ApiModelProperty(value = "职业类别代码", required = true)
    private String hdsd0001016;//职业类别代码

    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_MARRIAGE)
    @ApiModelProperty(value = "婚姻状况代码", required = true)
    private String hdsd0001017;//婚姻状况代码

    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_HISTORY_OF_DRUG_ALLERGY)
    @ApiModelProperty(value = "药物过敏史标志", required = true)
    private String hdsd0001019;//药物过敏史标志

    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_DRUG_ALLERGEN)
    @ApiModelProperty(value = "药物过敏源代码", required = true)
    private String hdsd0001020;//药物过敏源代码（表T_HR_A01010102）

    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_FAMILIAL_DISEASES)
    @ApiModelProperty(value = "家族性疾病名称代码", required = true)
    private String hdsd0001033;//家族性疾病名称代码（T_HR_A01010108）

    @ApiModelProperty(value = "家族性疾病名称", required = true)
    private String exhdsd0001003;//家族性疾病名称（T_HR_A01010108）

    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_SEX)
    @ApiModelProperty(value = "遗传性疾病史", required = true)
    private String hdsd0001035;//遗传性疾病史

    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_DISABILITY)
    @ApiModelProperty(value = "残疾情况代码", required = true)
    private String hdsd0001036;//残疾情况代码（T_HR_A01010109）

    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_OCCUPATIONAL_DISEASES)
    @ApiModelProperty(value = "职业病名称代码", required = true)
    private String hdsd0001440;//职业病名称代码（T_HR_B030202）
}
