package cn.com.adtech.domain.medicalinformation.response;

import cn.com.adtech.annotation.DesensitizationProperty;
import cn.com.adtech.util.CommonVariable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 检查记录项目
 */
@Data
public class InspectionRecordItemListShowData {
    @ApiModelProperty(value = "检验定量结果",required = true)
    private	String	hdsd0005046	;
    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_INSPECTION_RESULT)
    @ApiModelProperty(value = "检验结果代码",required = true)
    private	String	hdsd0005050	;
    @DesensitizationProperty(rule = CommonVariable.REDIS_KEY_HM_INSPECTION_ITEM)
    @ApiModelProperty(value = "检验项目代码",required = true)
    private	String	hdsd0005055	;
    @ApiModelProperty(value = "检验定量结果计量单位",required = true)
    private	String	hdsd0005047	;
    @ApiModelProperty(value = "参考值",required = true)
    private	String exhdsd0202244;

}
