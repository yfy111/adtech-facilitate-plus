package cn.com.adtech.domain.dictionary;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 字典实体VO
 */
@Data
public class DictionaryDetailShowData {

    @ApiModelProperty(value = "类别名称", required = true)
    private String typeName;
    
    @ApiModelProperty(value = "类别值", required = true)
    private String typeValue;
}
