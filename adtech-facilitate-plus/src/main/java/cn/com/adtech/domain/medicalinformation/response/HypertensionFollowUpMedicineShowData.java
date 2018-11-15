package cn.com.adtech.domain.medicalinformation.response;

import cn.com.adtech.domain.medicalinformation.MedicalInformationShowData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 高血压随访的显示数据-用药记录
 * @Author chenguangxue
 * @CreateDate 2018/11/2 16:56
 */
@Data
public class HypertensionFollowUpMedicineShowData extends MedicalInformationShowData {
    private String hdsd0001194;
    @ApiModelProperty(value = "药物使用频率",required = true)
    private String hdsd0001195;
    @ApiModelProperty(value = "药物使用次剂量",required = true)
    private String hdsd0001197;
    @ApiModelProperty(value = "药物使用总量",required = true)
    private String hdsd0001198;
    @ApiModelProperty(value = "药物使用剂量单位",required = true)
    private String hdsd0001196;

}
