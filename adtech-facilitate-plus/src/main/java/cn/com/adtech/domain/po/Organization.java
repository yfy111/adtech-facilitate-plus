package cn.com.adtech.domain.po;

import lombok.Data;

/**
 * 医院数据
 */
@Data
public class Organization {
    private String orgCode;
    private String orgName;
    private String parentId;
    private String areaCode;
    private String orgType;
}
