package cn.com.adtech.domain.login.doctorscan;

import cn.com.adtech.stereotype.RequestParameter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description 扫码枪扫码之后，直接请求登录医生工作站的请求参数
 * @Author chenguangxue
 * @CreateDate 2018/11/2 13:37
 */
@Data
public class DoctorScanLoginParameter extends RequestParameter {

    @ApiModelProperty(value = "身份证", required = true)
    private String cardCode;

    @ApiModelProperty(value = "姓名", required = true)
    private String userName;

    // 电话号码（这个参数可以不传，后台也不做校验）
    private String phoneCode;

    // 系统编码
    private String systemCode;

    // 医疗机构编码
    private String institutionsCode;
}
