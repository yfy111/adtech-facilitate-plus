package cn.com.adtech.domain.medicalinformation.request;

import cn.com.adtech.domain.medicalinformation.MedicalInformationShowData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 糖尿病随访记录参数
 * @Author chenguangxue
 * @CreateDate 2018/11/2 16:55
 */
@Data
public class DiabetesFollowUpParameter extends MedicalInformationShowData {
    @ApiModelProperty(value = "随访日期", required = true)
    private String checkDate;
    @ApiModelProperty(value = "身份证", required = true)
    private String identityCard;

}
