package cn.com.adtech.domain.medicalinformation.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class MedicalRecordsShowNewBltypeData {

    @ApiModelProperty(value = "就诊类型名称")
    private String visitName;

    @ApiModelProperty(value = "对应extsid")
    List<String> extsid;
}
