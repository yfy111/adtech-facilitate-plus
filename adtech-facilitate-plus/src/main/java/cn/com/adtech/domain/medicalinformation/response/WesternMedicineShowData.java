package cn.com.adtech.domain.medicalinformation.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 西药处方
 */
@Data
public class WesternMedicineShowData {

    @ApiModelProperty(value = "西药处方基本信息",required = true)
    private WesternMedicineBaseShowData westernMedicineBaseShowData;

    @ApiModelProperty(value = "西药处方明细",required = true)
    private List<WesternMedicineItemShowData> westernMedicineItemShowData;

}
