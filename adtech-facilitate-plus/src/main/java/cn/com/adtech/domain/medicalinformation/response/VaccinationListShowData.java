package cn.com.adtech.domain.medicalinformation.response;

import cn.com.adtech.annotation.DesensitizationProperty;
import cn.com.adtech.domain.medicalinformation.MedicalInformationShowData;
import cn.com.adtech.util.CommonVariable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 预防接种列表数据
 * @Author chenguangxue
 * @CreateDate 2018/11/2 16:54
 */
@Data
public class VaccinationListShowData extends MedicalInformationShowData {

    @ApiModelProperty(value = "疫苗接种单位名称",required = true)
    private String hdsd0001204;
    @DesensitizationProperty(rule= CommonVariable.REDIS_KEY_HM_VACCINE_NAME)
    @ApiModelProperty(value = "疫苗名称",required = true)
    private String hdsd0001201;
    @ApiModelProperty(value = "疫苗批号",required = true)
    private String hdsd0001202;
    @ApiModelProperty(value = "生产企业",required = true)
    private String exhdsd0001015;
    @ApiModelProperty(value = "疫苗接种日期",required = true)
    private String hdsd0001203;
    @DesensitizationProperty(rule= CommonVariable.REDIS_KEY_HM_BODY_PARTS)
    @ApiModelProperty(value = "接种部位",required = true)
    private String hdsd0001388;
    @ApiModelProperty(value = "接种剂次",required = true)
    private String hdsd0001389;

}
