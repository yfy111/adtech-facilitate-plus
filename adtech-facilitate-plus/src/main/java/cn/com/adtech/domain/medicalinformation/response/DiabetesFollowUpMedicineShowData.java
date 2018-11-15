package cn.com.adtech.domain.medicalinformation.response;

import cn.com.adtech.domain.medicalinformation.MedicalInformationShowData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 糖尿病随访记录的显示数据-用药记录
 * @Author chenguangxue
 * @CreateDate 2018/11/2 16:57
 */
@Data
public class DiabetesFollowUpMedicineShowData extends MedicalInformationShowData {

    @ApiModelProperty(value = "药物名称",required = true)
    private String hdsd0001194;
    @ApiModelProperty(value = "药物使用频率",required = true)
    private String hdsd0001195;
    @ApiModelProperty(value = "药物使用剂量单位",required = true)
    private String hdsd0001196;
    @ApiModelProperty(value = "药物使用次剂量",required = true)
    private String hdsd0001197;
    @ApiModelProperty(value = "药物使用总剂量",required = true)
    private String hdsd0001198;

}
