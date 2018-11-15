package cn.com.adtech.domain.medicalinformation.request;

import cn.com.adtech.domain.medicalinformation.MedicalInformationShowData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 预防接种列表参数
 * @Author chenguangxue
 * @CreateDate 2018/11/2 16:54
 */
@Data
public class VaccinationListParameter extends MedicalInformationShowData {

    @ApiModelProperty(value = "身份证", required = true)
    private String identityCard;

    @ApiModelProperty(value = "mpi", required = true)
    private String mpis;

    @ApiModelProperty(value = "页码")
    private Integer pageNum;
}
