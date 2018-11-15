package cn.com.adtech.enums;

import cn.com.adtech.stereotype.ResultStatus;
import lombok.Getter;

/**
 * @Description 响应的结果状态
 * @Author chenguangxue
 * @CreateDate 2018/11/2 09:38
 */
@Getter
public enum ResponseResultStatus implements ResultStatus {

    // 默认表示操作成功的结果状态
    SUCCESS("操作成功", true),

    // 默认表示操作失败的结果状态
    FAIL("操作失败"),

    // 校验三要素的结果状态
    USERINFO_NOT_EXIST("用户信息不存在"),
    USERINFO_IDENTITY_NAME_MISMATCH("用户的姓名和身份证不匹配"),
    USERINFO_PHONE_MISMATCH("用户的手机号不正确"),
    USERINFO_CORRECT("用户信息正确", true),

    // 发送短信验证码的结果状态
    SEND_SMS_CAPTCHA_SUCCESS("短信验证码发送成功", true),

    SEND_SMS_CAPTCHA_FAIL("短信验证码发送失败"),

    // 短信验证码的验证结果
    SMS_CAPTCHA_EXPIRE("短信验证码已过期"),
    SMS_CAPTCHA_MISMATCH("短信验证码不正确"),
    SMS_CAPTCHA_CORRECT("短信验证码正确", true),

    // 图形验证码的验证结果
    IMAGE_CAPTCHA_EXPIRE("图形验证码已过期"),
    IMAGE_CAPTCHA_MISMATCH("图形验证码不正确"),
    IMAGE_CAPTCHA_CORRECT("图形验证码正确", true),

    // 二维码的处理结果
    QR_CODE_NOT_EXIST("二维码编号不存在"),
    QR_CODE_EXPIRE("二维码已过期"),
    QR_CODE_PROCESSING("二维码正在处理"),
    QR_CODE_FINISH("二维码处理完成", true),

    // 是否为初次登录的校验结果
    IS_FIRST_LOGIN("初次访问系统", true),

    IS_NOT_FIRST_LOGIN("不是初次访问系统"),

    // 修改密码的结果状态
    TWICE_PASSWORD_MISMATCH("两次输入的密码不一致"),
    PASSWORD_EQUAL_IDENTITY_LAST_6("密码不能是身份证的后6位"),
    PASSWORD_CHANGE_SUCCESS("密码修改成功", true),
    PASSWORD_CHANGE_FAIL("密码修改失败"),

    // 手机号的验证结果
    PHONE_VERIFY_SUCCESS("手机号验证成功", true),

    PHONE_NOT_EXIST("手机号不存在"),

    // 登录验证的结果状态
    USER_NOT_EXIST("用户信息不存在"),

    USER_PASSWORD_MISMATCH("查询密码不正确"),

    USER_NAME_IDENTITY_MISMATCH("用户的姓名和身份证不匹配"),

    // 反馈问题的处理结果状态
    FEEDBACK_QUESTION_SUCCESS("反馈问题提交成功", true),
    FEEDBACK_QUESTION_FAIL("反馈问题提交失败"),

    FEEDBACK_TYPE_SUCCESS("反馈类型提交成功", true),
    FEEDBACK_TYPE_FAIL("反馈类型提交失败"),

    // 切换用户
    CHANGE_USER_SUCCESS("切换用户成功", true),
    CHANGE_USER_FAIL("切换用户失败"),

    // 登录结果
    LOGIN_SUCCESS("登录成功", true),

    // 退出系统的处理结果
    LOGOUT_SUCCESS("退出系统成功", true),
    LOGOUT_FAIL("退出系统失败"),
    ;

    private final String code;
    private final String message;
    private final boolean success;

    ResponseResultStatus(String message, boolean success) {
        this.code = this.name().toUpperCase();
        this.message = message;
        this.success = success;
    }

    ResponseResultStatus(String message) {
        this(message, false);
    }
}
