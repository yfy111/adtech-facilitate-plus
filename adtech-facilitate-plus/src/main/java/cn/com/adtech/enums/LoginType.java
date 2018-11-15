package cn.com.adtech.enums;

import org.springframework.util.StringUtils;

/**
 * @Description 登录类型
 * @Author chenguangxue
 * @CreateDate 2018/11/13 17:59
 */
public enum LoginType {

    DOCTOR_SCAN_LOGIN,
    APP_SCAN_LOGIN,
    APP_PASSWORD_LOGIN,
    OTHER_DIRECT_LOGIN,
    ;

    private static final String CONNECTOR = "zxcvbnm";

    // 将登录方式和登录身份证拼接起来
    public String join(String identity) {
        return this.name() + CONNECTOR + identity;
    }

    // 将拼接的结果拆分为登录类型和身份证号
    public String[] split(String joinString) {
        if (StringUtils.isEmpty(joinString)) {
            return new String[0];
        }
        else {
            return joinString.split(CONNECTOR);
        }
    }
}
