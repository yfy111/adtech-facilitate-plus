package cn.com.adtech.domain.medicalinformation.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 中药处方
 */
@Data
public class ChineseMedicineShowData {

    @ApiModelProperty(value = "中药处方基本信息",required = true)
    private ChineseMedicineBaseShowData chineseMedicineBaseShowData;

    @ApiModelProperty(value = "中药处方明细",required = true)
    private List<ChineseMedicineItemListShowData> chineseMedicineItemListShowDataList;

}
