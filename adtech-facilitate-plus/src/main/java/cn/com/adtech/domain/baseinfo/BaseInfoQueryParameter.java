package cn.com.adtech.domain.baseinfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 基本信息参数
 * @Author PYH
 * @CreateDate 2018/11/6 16:55
 */
@Data
public class BaseInfoQueryParameter {

    @ApiModelProperty(value = "身份证", required = true)
    private String identityCard;

    @ApiModelProperty(value = "建档日期", required = false)
    private String checkDate;
}
