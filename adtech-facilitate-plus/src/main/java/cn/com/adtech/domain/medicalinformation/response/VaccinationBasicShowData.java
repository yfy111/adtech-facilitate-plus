package cn.com.adtech.domain.medicalinformation.response;

import cn.com.adtech.annotation.DesensitizationProperty;
import cn.com.adtech.domain.medicalinformation.MedicalInformationShowData;
import cn.com.adtech.util.CommonVariable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 预防接种个人基本信息
 * @Author chenguangxue
 * @CreateDate 2018/11/2 16:54
 */
@Data
public class VaccinationBasicShowData extends MedicalInformationShowData {

    @ApiModelProperty(value = "本人姓名", required = true)
    private String hdsd0001002;
    @ApiModelProperty(value = "性别", required = true)
    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_SEX)
    private String hdsd0001003;
    @ApiModelProperty(value = "出生日期", required = true)
    private String hdsd0001004;
    @ApiModelProperty(value = "现住地址", required = true)
    private String exhdsd0001012;
    @ApiModelProperty(value = "监护人姓名", required = true)
    private String hdsd0001372;
    @ApiModelProperty(value = "监护人与本人关系", required = true)
    private String hdsd0001373;
}
