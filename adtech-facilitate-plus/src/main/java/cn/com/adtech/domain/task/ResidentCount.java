package cn.com.adtech.domain.task;

import lombok.Data;

import java.util.Date;

/**
 * @Description  居民统计明细表
 * @Author LJL
 */
@Data
public class ResidentCount {

    private String parentId;
    private String cityId;
    private String extcda;
    private String platfromId;
    private int stayTime;
    private String userId;
    private String userPhone;
    private String caeateTime;
    private String userType;
    private String idCard;
    private Date lasttime;
    private String count;
    private String readcount;
    private String peoplecount;
    private String  querycount;
    private String archivesCount;
    private Date countTime;
    private String platformCount;
}
