package cn.com.adtech.domain.task;

import lombok.Data;

import java.util.Date;

/**
 * @Description 机构基层统计明细表
 * @Author LJL
 */
@Data
public class SourceCount {

    private String parentId;
    private String cityId;
    private String extcda;
    private String platfromId;
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
    private Date lasttime;
    private String count;
    private String readcount;
    private String peoplecount;
    private String archivesCount;
    private Date countTime;
}
