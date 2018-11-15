package cn.com.adtech.service;

import cn.com.adtech.component.*;
import cn.com.adtech.component.ImageCaptchaGenerator.ImageCaptchaResult;
import cn.com.adtech.domain.captcha.SmsCaptchaParameter;
import cn.com.adtech.domain.captcha.UserinfoThreeElementsParameter;
import cn.com.adtech.enums.LoginType;
import cn.com.adtech.enums.ResponseResultStatus;
import cn.com.adtech.environment.properties.FacilitateProperties;
import cn.com.adtech.environment.properties.QRCodeProperties;
import cn.com.adtech.environment.properties.SmsProperties;
import cn.com.adtech.stereotype.ResponseResult;
import cn.com.adtech.stereotype.ResultStatus;
import cn.com.adtech.util.HttpUtils;
import cn.com.adtech.util.QRCodeUtils;
import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.zxing.common.BitMatrix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Description 验证码相关的业务流程
 * @Author chenguangxue
 * @CreateDate 2018/11/2 09:19
 */
@Service
@Transactional
@Slf4j
public class CaptchaService {

    @Autowired
    private ImageCaptchaGenerator imageCaptchaGenerator;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private DefaultKaptcha captchaProducer;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private LoginVerifier loginVerifier;
    @Autowired
    private SmsSender smsSender;
    @Autowired
    private SmsProperties smsProperties;
    @Autowired
    private FacilitateProperties facilitateProperties;
    @Autowired
    private QRCodeProperties qrCodeProperties;
    @Autowired
    private CacheKey cacheKey;
    @Autowired
    private StringRedisTemplate redisTemplate;

    public void generateImageCaptcha(String key, HttpServletResponse response) {
        ImageCaptchaResult result = imageCaptchaGenerator.createVerifyObjects();
        cacheService.saveImageCaptcha(key, result.getCode());
        HttpUtils.outputImage.accept(response, result.getImage());
    }
    public void generateImageCaptcha2(String key, HttpServletResponse response) throws IOException {
        response.setDateHeader("Expires", 0);// 在代理服务器端防止缓冲
        // 不缓存页面
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");

        // 设置返回文件类型
        response.setContentType("image/jpeg");

        String capText = captchaProducer.createText();

        // 将验证码的文本保存在session中
        cacheService.saveImageCaptcha(key, capText);
        System.out.println("验证码为：" + capText);

        // 将文本渲染到图片上
        BufferedImage bufferedImage = captchaProducer.createImage(capText);
        ServletOutputStream outputStream = null;

        outputStream = response.getOutputStream();
        ImageIO.write(bufferedImage, "jpeg", outputStream);
        outputStream.flush();
    }

    public ResponseResult sendSmsCaptcha(SmsCaptchaParameter parameter) {
        // 校验手机号是否合法
        UserinfoThreeElementsParameter threeElementsParameter = UserinfoThreeElementsParameter.of(parameter);
        ResultStatus phoneResultStatus = loginVerifier.verifyUserThreeElements.apply(threeElementsParameter);
        if (phoneResultStatus.isFail()) {
            return ResponseResult.result(phoneResultStatus);
        }

        // 生成短信验证码的内容
        String smsCaptcha = smsSender.generateSmsCaptcha();
        log.info("短信验证码为：{}", smsCaptcha);

        // 将验证码保存到缓存中
        cacheService.saveSmsCaptcha(parameter.getPhone(), smsCaptcha);

        // 手机号合法就发送短信
        if (facilitateProperties.isUseMockData()) {
            return ResponseResult.result(ResponseResultStatus.SEND_SMS_CAPTCHA_SUCCESS).data(smsCaptcha);
        }
        else {
            String sendContent = String.format(smsProperties.getCaptchaContent(), smsCaptcha);
            return smsSender.send(parameter.getPhone(), sendContent).data(smsCaptcha);
        }

    }

    public ResponseResult generateLoginQRCode(String serialNumber, HttpServletResponse response) throws IOException {
        log.info("请求生成登录二维码");

        // 直接以页面请求的sessionId为serverCode
        log.info("serverCode：{}", serialNumber);

        String timestamp = String.valueOf(System.currentTimeMillis());
        String url = facilitateProperties.getBaseUrl() + "/scan-login";
        String method = HttpMethod.POST.name();

        JSONObject content = new JSONObject();
        content.put("serverCode", serialNumber);
        content.put("timestamp", timestamp);
        content.put("url", url);
        content.put("method", method);

        String jsonString = content.toJSONString();
        log.info("登录二维码信息为：" + jsonString);

        BitMatrix bitMatrix = QRCodeUtils.setBitMatrix(jsonString, 300, 300);
        BufferedImage bufferedImage = QRCodeUtils.toBufferedImage(bitMatrix);
        QRCodeUtils.addLogo(bufferedImage, qrCodeProperties.getLogoUrl());
        ImageIO.write(bufferedImage, "png", response.getOutputStream());

        Map<String, Object> data = new HashMap<>();
        data.put("serverCode", serialNumber);
        return ResponseResult.mockSuccess().data(serialNumber);
    }

    // 生成二维码所对应的序列号
    public ResponseResult generateQRCodeSerialNumber() {
        String sessionId = request.getSession().getId();
        String key = cacheKey.qrCodeSerialNumberKey.apply(sessionId);

        // 生成的二维码序列号保存到redis
        redisTemplate.opsForValue().set(key, QR_CODE_PROCESSING_TAG, 60 * 20, TimeUnit.SECONDS);

        return ResponseResult.mockSuccess().data(key);
    }

    private static final String QR_CODE_PROCESSING_TAG = "QR_CODE_PROCESSING_TAG";

    // 查询二维码的处理结果
    public ResponseResult queryQRCodeResult(String serialNumber) {
        // 从redis中获取指定序列号的处理结果
        String result = redisTemplate.opsForValue().get(serialNumber);

        if (StringUtils.isEmpty(result)) {
            return ResponseResult.result(ResponseResultStatus.QR_CODE_EXPIRE);
        }

        if (Objects.equals(result, QR_CODE_PROCESSING_TAG)) {
            return ResponseResult.result(ResponseResultStatus.QR_CODE_PROCESSING);
        }

        // 到这里说明二维码已经处理完成
        return ResponseResult.result(ResponseResultStatus.QR_CODE_FINISH).data(LoginType.APP_SCAN_LOGIN.name());
    }
}
