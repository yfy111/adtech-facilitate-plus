package cn.com.adtech.domain.medicalinformation.response;

import cn.com.adtech.annotation.DesensitizationProperty;
import cn.com.adtech.domain.medicalinformation.MedicalInformationShowData;
import cn.com.adtech.util.CommonVariable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description
 * @Author chenguangxue
 * 出生医学证明
 * @CreateDate 2018/11/2 16:53
 */
@Data
public class BirthCertificateShowData extends MedicalInformationShowData {

    @ApiModelProperty(value = "extsid")
    private String extsid;
    @ApiModelProperty(value = "extmpi")
    private String extmpi;
    @ApiModelProperty(value = "新生儿姓名", required = true)
    private String exhdsd0001003;
    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_SEX)
    @ApiModelProperty(value = "新生儿性别", required = true)
    private String exhdsd0001004;
    @ApiModelProperty(value = "新生儿出生时间", required = true)
    private String exhdsd0001005;
    @ApiModelProperty(value = "母亲姓名", required = true)
    private String exhdsd0001016;
    @ApiModelProperty(value = "母亲年龄", required = true)
    private String exhdsd0001019;
    @DesensitizationProperty(rule= CommonVariable.REDIS_KEY_HM_NATIONALITY)
    @ApiModelProperty(value = "母亲国籍", required = true)
    private String exhdsd0001020;
    @DesensitizationProperty(rule= CommonVariable.REDIS_KEY_HM_NATION)
    @ApiModelProperty(value = "母亲民族", required = true)
    private String exhdsd0001021;
    @ApiModelProperty(value = "父亲姓名", required = true)
    private String exhdsd0001029;
    @ApiModelProperty(value = "父亲年龄", required = true)
    private String exhdsd0001031;
    @DesensitizationProperty(rule= CommonVariable.REDIS_KEY_HM_NATIONALITY)
    @ApiModelProperty(value = "父亲国籍", required = true)
    private String exhdsd0001032;
    @DesensitizationProperty(rule= CommonVariable.REDIS_KEY_HM_NATION)
    @ApiModelProperty(value = "父亲民族", required = true)
    private String exhdsd0001033;
    @ApiModelProperty(value = "母亲出生日期", required = true)
    private String exhdsd0001018;
    @ApiModelProperty(value = "母亲户籍所在地", required = true)
    private String exhdsd0001022;
    @ApiModelProperty(value = "医疗机构名称", required = true)
    private String exhdsd0001047;
}
