package cn.com.adtech.domain.medicalinformation.response;

import cn.com.adtech.annotation.DesensitizationProperty;
import cn.com.adtech.util.CommonVariable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 西药处方基本信息
 */
@Data
public class WesternMedicineBaseShowData {

    @ApiModelProperty(value = "extid",required = true)
    private String extid;
    @ApiModelProperty(value = "extmpi",required = true)
    private String extmpi;
    @ApiModelProperty(value = "extsid",required = true)
    private String extsid;
    @ApiModelProperty(value = "extspid",required = true)
    private String extspid;
    @ApiModelProperty(value = "医疗机构名称",required = true)
    private String exhdsd0202257;
    @ApiModelProperty(value = "科室名称",required = true)
    private String hdsd0004005;
    @ApiModelProperty(value = "门（急）诊号",required = true)
    private String hdsd0004016;
    @ApiModelProperty(value = "处方编号",required = true)
    private String hdsd0004002;
    @DesensitizationProperty(rule=CommonVariable.DESENSITIZATION_NAME,doSomeThing= CommonVariable.DESENSITIZATION)
    @ApiModelProperty(value = "患者姓名",required = true)
    private String exhdsd0202250;
    @ApiModelProperty(value = "年龄（岁）",required = true)
    private String exhdsd0202253;
    @DesensitizationProperty(rule=CommonVariable.REDIS_KEY_HM_SEX)
    @ApiModelProperty(value = "性别",required = true)
    private String exhdsd0202251;
    @ApiModelProperty(value = "处方开立日期",required = true)
    private String hdsd0004006;
    @ApiModelProperty(value = "处方有效天数",required = true)
    private String hdsd0004013;
    @ApiModelProperty(value = "处方药品金额",required = true)
    private String exhdsd0201147;
    @ApiModelProperty(value = "处方开立医师签名",required = true)
    private String hdsd0004007;

}
