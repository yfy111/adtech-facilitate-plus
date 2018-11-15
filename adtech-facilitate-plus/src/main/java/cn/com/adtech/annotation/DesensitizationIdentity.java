package cn.com.adtech.annotation;

import cn.com.adtech.util.CommonVariable;

import java.lang.annotation.*;

/**
 * @Description 标记身份证需要脱敏的注解
 * @Author chenguangxue
 * @CreateDate 2018/11/12 16:11
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@DesensitizationProperty(rule = CommonVariable.DESENSITIZATION_ID_CARD, doSomeThing = CommonVariable.DESENSITIZATION)
public @interface DesensitizationIdentity {
}
