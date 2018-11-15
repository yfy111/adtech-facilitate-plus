package cn.com.adtech.component;

import cn.com.adtech.domain.captcha.sms.SmsSendParameter;
import cn.com.adtech.enums.ResponseResultStatus;
import cn.com.adtech.environment.properties.SmsProperties;
import cn.com.adtech.stereotype.ResponseResult;
import cn.com.adtech.util.EncryptUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * @Description 短信发送者
 * @Author chenguangxue
 * @CreateDate 2018/11/7 09:48
 */
@Component
@Slf4j
public class SmsSender {

    @Autowired
    private SmsProperties smsProperties;
    @Autowired
    private RestTemplate restTemplate;

    private Random random = new Random();

    // 生成短信验证码
    public String generateSmsCaptcha() {
        StringBuilder builder = new StringBuilder();
        IntStream.range(0, smsProperties.getCaptchaCount()).forEach(index -> {
            builder.append(random.nextInt(10));
        });
        return builder.toString();
    }

    public ResponseResult send(String receiver, String content) {
        String timestamp = Long.toString(System.currentTimeMillis());
        String accessToken = EncryptUtils.md5(timestamp + smsProperties.getPassword());

        SmsSendParameter sendParameter = new SmsSendParameter();
        sendParameter.setAccount(smsProperties.getAccount());
        sendParameter.setExtcode("");
        sendParameter.setReceiver(receiver);
        sendParameter.setSmscontent(content);
        sendParameter.setTimestamp(timestamp);
        sendParameter.setAccess_token(accessToken);

        String requestAddress = smsProperties.getUrl() + smsProperties.getMethod();

        String parameterString = sendParameter.toString();
        String sendResult = restTemplate.postForObject(requestAddress + parameterString, null, String.class);
        JSONObject jsonObject = JSON.parseObject(sendResult);

        String res_code = jsonObject.getString("res_code");
        if ("1".equals(res_code)) {
            String res_message = jsonObject.getString("res_message");
            log.info("短信发送失败：{}", res_message);
            return ResponseResult.fail(res_message);
        }
        else {
            log.info("短信发送成功");
            return ResponseResult.result(ResponseResultStatus.SEND_SMS_CAPTCHA_SUCCESS);
        }
    }
}
