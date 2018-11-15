package cn.com.adtech.domain.medicalinformation.response;

import cn.com.adtech.domain.medicalinformation.MedicalInformationShowData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 新生儿随访记录-黄疸部位
 * @Author chenguangxue
 * @CreateDate 2018/11/2 16:55
 */
@Data
public class NewbornFollowUpJaundiceShowData extends MedicalInformationShowData {
    @ApiModelProperty(value = "黄疸部位代码",required = true)
    private String hdsd0001252;

}
