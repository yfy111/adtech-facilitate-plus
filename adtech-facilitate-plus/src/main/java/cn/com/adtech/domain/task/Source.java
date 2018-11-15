package cn.com.adtech.domain.task;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Description  统计明细表
 * @Author LJL
 */
@Data
public class Source {

    private String parentId;
    private String cityId;
    private String extcda;
    private String platformId;
    private int stayTime;
    private String userId;
    private String userPhone;
    private String caeateTime;
    private String userType;
    private String institutionCode;
    private String institutionName;
    private String institutionType;
    private String ip;
    private String idCard;
    private Date time;
    private String count;
}
