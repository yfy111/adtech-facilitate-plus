package cn.com.adtech.annotation;

import java.lang.annotation.*;

/**
 * @Description
 * @Author chenguangxue
 * @CreateDate 2018/11/9 00:17
 */
@Target(ElementType.FIELD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldDisplayProperty {

    // 是否隐藏该字段，默认为false
    boolean hidden() default false;

    // 显示的高度：对于li就是高度的倍数，对于td就是rowspan的值
    int height() default 1;

    // 显示的宽度：：对于li就是宽度的倍数，对于td就是colspan的值
    int width() default 1;

    //标题宽度
    int titleWidth() default 1;

    //标题高度
    int titleHeight() default 1;

    String title() default "";
}
