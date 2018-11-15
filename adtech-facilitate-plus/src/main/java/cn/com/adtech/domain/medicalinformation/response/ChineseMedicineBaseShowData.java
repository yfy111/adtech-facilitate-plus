package cn.com.adtech.domain.medicalinformation.response;

import cn.com.adtech.annotation.DesensitizationProperty;
import cn.com.adtech.util.CommonVariable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 中药处方-基本信息
 */
@Data
public class ChineseMedicineBaseShowData {

    @ApiModelProperty(value = "extid",required = true)
    private String extid;
    @ApiModelProperty(value = "extmpi",required = true)
    private String extmpi;
    @ApiModelProperty(value = "extsid",required = true)
    private String extsid;
    @ApiModelProperty(value = "医疗机构名称",required = true)
    private String exhdsd0202257;
    @ApiModelProperty(value = "处方开立科室名称",required = true)
    private String hdsd0004005;
    @ApiModelProperty(value = "门急诊号",required = true)
    private String hdsd0004016;
    @ApiModelProperty(value = "处方编号",required = true)
    private String hdsd0004002;
    @ApiModelProperty(value = "患者姓名",required = true)
    private String exhdsd0202250;
    @ApiModelProperty(value = "年龄（岁）",required = true)
    private String exhdsd0202253;
    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_SEX)
    @ApiModelProperty(value = "患者性别",required = true)
    private String exhdsd0202251;
    @ApiModelProperty(value = "处方开立日期",required = true)
    private String hdsd0004006;
    @ApiModelProperty(value = "处方有效天数",required = true)
    private String hdsd0004013;
    @ApiModelProperty(value = "处方金额",required = true)
    private String exhdsd0201147;
    @ApiModelProperty(value = "处方开立医师",required = true)
    private String hdsd0004007;

}
