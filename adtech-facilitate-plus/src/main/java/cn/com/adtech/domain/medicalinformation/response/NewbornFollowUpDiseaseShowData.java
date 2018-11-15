package cn.com.adtech.domain.medicalinformation.response;

import cn.com.adtech.domain.medicalinformation.MedicalInformationShowData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 新生儿随访记录-疾病筛查
 * @Author chenguangxue
 * @CreateDate 2018/11/2 16:55
 */
@Data
public class NewbornFollowUpDiseaseShowData extends MedicalInformationShowData {
    @ApiModelProperty(value = "疾病筛查项目代码",required = true)
    private String hdsb0103025;

}
