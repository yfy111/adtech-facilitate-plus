package cn.com.adtech.annotation;


import cn.com.adtech.util.CommonVariable;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface DesensitizationProperty {

    //转换来源字典
    String loadTableName() default CommonVariable.LOAD_TABLE_NAME_RRS_DIC_CODE;

    //脱敏/字典对应
    String rule() default "";

    //脱敏：DZ，字典转换：DT
    //默认为字典转换
    String doSomeThing() default CommonVariable.DECTIONALRY_CAST;
}
