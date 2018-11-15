package cn.com.adtech.annotation;

import java.lang.annotation.*;

/**
 * @Description 标记出可在未登录状态下访问的资源的注解
 * @Author chenguangxue
 * @CreateDate 2018/11/1 23:50
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface FreeAccess {
}
