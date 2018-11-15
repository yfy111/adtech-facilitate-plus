package cn.com.adtech.domain.medicalinformation.response;

import cn.com.adtech.domain.medicalinformation.MedicalInformationShowData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 糖尿病随访记录的显示数据-症状表
 * @Author chenguangxue
 * @CreateDate 2018/11/2 16:57
 */
@Data
public class DiabetesFollowUpSymptomShowData extends MedicalInformationShowData {

    @ApiModelProperty(value = "症状名称",required = true)
    private String hdsd0001047;

}
