package cn.com.adtech.controller.app;

import cn.com.adtech.domain.login.directlogin.DirectLoginParameter;
import cn.com.adtech.domain.login.doctorscan.DoctorScanLoginParameter;
import cn.com.adtech.domain.login.identitypassword.AppPasswordParameter;
import cn.com.adtech.domain.login.identitypassword.IdentityPasswordLoginParameter;
import cn.com.adtech.domain.login.qrscan.AppScanLoginParameter;
import cn.com.adtech.domain.login.smscaptcha.IdentitySmsCaptchaLoginParameter;
import cn.com.adtech.service.LoginService;
import cn.com.adtech.stereotype.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 提供登录的rest控制器
 * @Author chenguangxue
 * @CreateDate 2018/11/2 11:44
 */
@RestController
@RequestMapping(value = "/rest")
@Api(value = "登录验证接口", tags = "登录验证接口")
public class LoginRestController {

    @Autowired
    private LoginService loginService;

    // 第三方系统免密方式执行登录验证：不提供密码，只检验用户信息是否匹配
    @PostMapping(value = "/direct-login")
    public ResponseResult doDirectLogin(@RequestBody DirectLoginParameter parameter) {
        return loginService.doDirectLogin(parameter);
    }

    // 提供给健康卡app的接口：使用身份证、密码、姓名、systemCode方式登录校验的接口
    @PostMapping(value = "/app-password-login")
    public ResponseResult doPasswordForAppLogin(@RequestBody AppPasswordParameter parameter) {
        return loginService.doAppPasswordLogin(parameter);
    }

    // 以身份证号、密码、图形验证码方式进行登录验证
    @PostMapping(value = "/identity-password-login")
    @ApiOperation(value = "姓名、身份证、密码、图形验证码方式登录验证")
    public ResponseResult doIdentityPasswordLogin(HttpServletRequest request, @RequestBody IdentityPasswordLoginParameter parameter) {
        if (StringUtils.isEmpty(parameter.getImageCaptchaKey())) {
            parameter.setImageCaptchaKey(request.getSession().getId());
        }
        return loginService.doIdentityPasswordLogin(parameter);
    }

    // 以身份证、姓名、手机号、短信验证码方式进行登录验证
    @PostMapping(value = "/identity-sms-captcha-login")
    @ApiOperation(value = "身份证、姓名、手机号、短信验证码方式登录验证")
    public ResponseResult doIdentitySmsCaptchaLogin(HttpServletRequest request, @RequestBody IdentitySmsCaptchaLoginParameter parameter) {
        return loginService.doIdentitySmsCaptchaLogin(parameter);
    }

    // 健康卡app扫码登录医生工站：身份证、姓名、手机号、二维码编号
    @PostMapping(value = "/app-scan-login")
    @ApiOperation(value = "手机扫码登录：身份证、姓名、手机号、二维码编号方式登录验证")
    public ResponseResult doScanLogin(HttpServletRequest request, @RequestBody AppScanLoginParameter parameter) {
        return loginService.doScanLogin(parameter);
    }

    // 扫码枪扫码之后，请求登录医生工作站
    @PostMapping(value = "/doctor-login")
    @ApiOperation(value = "医生端扫码登录登录验证")
    public ResponseResult doDoctorScanLogin(HttpServletRequest request, @RequestBody DoctorScanLoginParameter parameter) {
        return loginService.doDoctorScanLogin(parameter);
    }

    // 医生端身份证密码登录：

    @GetMapping(value = "/logout")
    @ApiOperation(value = "退出系统")
    public ResponseResult doLogout() {

        return loginService.doLogout();
    }
}
