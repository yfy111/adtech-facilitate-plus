package cn.com.adtech.domain.medicalinformation.response;

import cn.com.adtech.annotation.DesensitizationIdentity;
import cn.com.adtech.annotation.DesensitizationProperty;
import cn.com.adtech.util.CommonVariable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class MedicalRecordsShowNewData {

    @ApiModelProperty(value = "创建时间", required = true)
    private String createdate;

    @ApiModelProperty(value = "姓名", required = true)
    private String username;

    @ApiModelProperty(value = "身份证号", required = true)
    @DesensitizationIdentity
    private String identityCard;

    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_HOSPITAL_NAME)
    @ApiModelProperty(value = "医院名称", required = true)
    private String hospitalName;

    @ApiModelProperty(value = "就诊记录类型", required = true)
    private List<MedicalRecordsShowNewBltypeData> typeList;
}
