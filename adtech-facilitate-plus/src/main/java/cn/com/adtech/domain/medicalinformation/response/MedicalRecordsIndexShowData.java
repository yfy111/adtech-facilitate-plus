package cn.com.adtech.domain.medicalinformation.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 *  就诊记录列表基本信息
 */
@Data
public class MedicalRecordsIndexShowData {

    @ApiModelProperty(value = "就诊类型",required = true)
    private Map<String,String> type;

    @ApiModelProperty(value = "医院名称",required = true)
    private Map<String,String> hospitalName;

    @ApiModelProperty(value = "生命周期",required = true)
    private Map<String,String> lifeCycle;

}
