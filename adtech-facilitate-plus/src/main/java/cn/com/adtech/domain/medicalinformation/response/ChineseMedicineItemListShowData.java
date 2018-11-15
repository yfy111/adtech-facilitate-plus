package cn.com.adtech.domain.medicalinformation.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 中药处方明细
 */
@Data
public class ChineseMedicineItemListShowData {

    @ApiModelProperty(value = "中药饮片描述",required = true)
    private String exhdsd0201204;
    @ApiModelProperty(value = "中药饮片剂数",required = true)
    private String hdsd0004032;
    @ApiModelProperty(value = "中药饮片煎煮法",required = true)
    private String hdsd0004033;
    @ApiModelProperty(value = "中药用药方法",required = true)
    private String hdsd0004034;

}
