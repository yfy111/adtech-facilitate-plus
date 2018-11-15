package cn.com.adtech.domain.medicalinformation.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 就诊记录参数
 */
@Data
public class MedicalRecordsParameter {
    @ApiModelProperty(value = "开始时间")
    private String beginDate;
    @ApiModelProperty(value = "结束时间")
    private String endDate;
    @ApiModelProperty(value = "医院名称")
    private String hospitalName;
    @ApiModelProperty(value = "类别编码")
    private String visitType;
    @ApiModelProperty(value = "生命周期 童年:childhood,少年:juvenile,青年:youth,中年:middleAge,old:老年")
    private String cycleType;
    @ApiModelProperty(value = "页码", required = true)
    private Integer pageNum;
    @ApiModelProperty(value = "每页多少条")
    private Integer pageSize;
    @ApiModelProperty(value = "身份证",required = true)
    private String identityCard;


}
