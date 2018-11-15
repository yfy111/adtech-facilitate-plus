package cn.com.adtech.domain.po;

import lombok.Data;

/**
 * 字典实体
 */
@Data
public class DictionaryDetail {
    private String id;
    private String typeCode;
    private String explan;
    private String typeName;
    private String typeValue;
    private String isDiscontinuation;
}
