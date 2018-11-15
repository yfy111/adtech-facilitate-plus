package cn.com.adtech.domain.login.qrscan;

import cn.com.adtech.stereotype.RequestParameter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 健康卡app扫码登录的请求参数
 * @Author chenguangxue
 * @CreateDate 2018/11/2 13:24
 */
@Data
public class AppScanLoginParameter extends RequestParameter {

    @ApiModelProperty(value = "身份证号", required = true)
    private String cardCode;

    @ApiModelProperty(value = "姓名", required = true)
    private String userName;

    @ApiModelProperty(value = "手机号", required = true)
    private String phoneCode;

    @ApiModelProperty(value = "系统编号", required = true)
    private String systemCode;

    @ApiModelProperty(value = "二维码编号", required = true)
    private String serverCode;
}
