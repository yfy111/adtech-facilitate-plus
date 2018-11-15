package cn.com.adtech.domain.medicalinformation.response;

import cn.com.adtech.domain.medicalinformation.MedicalInformationShowData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 高血压随访的显示数据-症状
 * @Author chenguangxue
 * @CreateDate 2018/11/2 16:56
 */
@Data
public class HypertensionFollowUpSymptomShowData extends MedicalInformationShowData {
    @ApiModelProperty(value = "症状名称",required = true)
    private String hdsd0001047;

}
