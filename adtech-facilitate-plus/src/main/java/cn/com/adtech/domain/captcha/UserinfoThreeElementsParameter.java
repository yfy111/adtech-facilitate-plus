package cn.com.adtech.domain.captcha;

import cn.com.adtech.domain.login.smscaptcha.IdentitySmsCaptchaLoginParameter;
import lombok.Data;

/**
 * @Description 用户的三要素信息：身份证、姓名、手机号
 * @Author chenguangxue
 * @CreateDate 2018/11/7 09:05
 */
@Data
public class UserinfoThreeElementsParameter {

    private String name;
    private String identity;
    private String phone;

    public static UserinfoThreeElementsParameter of(SmsCaptchaParameter smsCaptchaParameter) {
        UserinfoThreeElementsParameter parameter = new UserinfoThreeElementsParameter();
        parameter.name = smsCaptchaParameter.getRealName();
        parameter.identity = smsCaptchaParameter.getIdentity();
        parameter.phone = smsCaptchaParameter.getPhone();
        return parameter;
    }

    public static UserinfoThreeElementsParameter of(IdentitySmsCaptchaLoginParameter smsCaptchaLoginParameter) {
        UserinfoThreeElementsParameter parameter = new UserinfoThreeElementsParameter();
        parameter.name = smsCaptchaLoginParameter.getRealName();
        parameter.identity = smsCaptchaLoginParameter.getIdentityCard();
        parameter.phone = smsCaptchaLoginParameter.getUserPhone();
        return parameter;
    }
}
