package cn.com.adtech.component;

import cn.com.adtech.environment.properties.CacheProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Description 缓存服务类
 * @Author chenguangxue
 * @CreateDate 2018/11/6 17:52
 */
@Component
@Slf4j
public class CacheService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private CacheKey cacheKey;
    @Autowired
    private CacheProperties cacheProperties;

    public void saveImageCaptcha(String captchaKey, String captcha) {
        String key = cacheKey.imageCaptcha.apply(captchaKey);
        log.info("保存图形验证码的key：{}", captchaKey);
        redisTemplate.opsForValue().set(key, captcha, cacheProperties.getImageCaptchaPeriod(), TimeUnit.SECONDS);
    }

    public void saveSmsCaptcha(String phone, String captcha) {
        String key = cacheKey.smsCaptcha.apply(phone);
        redisTemplate.opsForValue().set(key, captcha, cacheProperties.getSmsCaptchaPeriod(), TimeUnit.SECONDS);
    }
}
