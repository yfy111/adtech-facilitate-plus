package cn.com.adtech.controller.web;

import cn.com.adtech.annotation.FreeAccess;
import cn.com.adtech.domain.captcha.SmsCaptchaParameter;
import cn.com.adtech.service.CaptchaService;
import cn.com.adtech.stereotype.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 发送验证码的控制器
 * @Author chenguangxue
 * @CreateDate 2018/11/1 22:59
 */
@RestController
@RequestMapping(value = "/captcha")
@Api(value = "验证码接口", tags = "验证码接口")
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;
    @Autowired
    private HttpServletRequest request;

    // 发送图形验证码
    @GetMapping(value = "/image")
    @FreeAccess
    @ApiOperation(value = "生成图形验证码的接口")
    public void generateImageCaptcha(@RequestParam(required = false) String key, HttpServletResponse response) {
        // 如果key为null或者空白，则说明是web端的请求，这种情况下使用sessinId为key
        if (StringUtils.isEmpty(key)) {
            key = request.getSession().getId();
        }
        captchaService.generateImageCaptcha(key, response);
    }
    // 发送图形验证码
    @GetMapping(value = "/image2")
    @FreeAccess
    @ApiOperation(value = "生成图形验证码的接口")
    public void generateImageCaptcha2(@RequestParam(required = false) String key, HttpServletResponse response) throws IOException {
        // 如果key为null或者空白，则说明是web端的请求，这种情况下使用sessinId为key
        if (StringUtils.isEmpty(key)) {
            key = request.getSession().getId();
        }
        captchaService.generateImageCaptcha2(key, response);
    }
    // 发送短信验证码
    @PostMapping(value = "/sms")
    @ApiOperation(value = "获取短信验证码的接口")
    public ResponseResult sendSmsCaptcha(@RequestBody SmsCaptchaParameter parameter) {
        return captchaService.sendSmsCaptcha(parameter);
    }

    // 生成二维码的随机序列号
    @PostMapping(value = "/qr-code-serial-number")
    @ResponseBody
    public ResponseResult generateQRCodeSerialNumber() {
        return captchaService.generateQRCodeSerialNumber();
    }

    // 生成登录二维码
    @GetMapping(value = "/qr-code")
    @ApiOperation(value = "生成登录二维码的接口")
    public void generateLoginQRCode(@RequestParam String serialNumber, HttpServletResponse response) throws IOException {
        captchaService.generateLoginQRCode(serialNumber, response);
    }

    // 轮询请求指定序列号的二维码的处理结果
    @PostMapping(value = "/qr-code-result")
    @ResponseBody
    public ResponseResult queryQRCodeResult(@RequestParam String serialNumber) {
        return captchaService.queryQRCodeResult(serialNumber);
    }
}
