package cn.common.security.annotation;

import java.lang.annotation.*;

/**
 * 内部认证注解
 *
 * @author ruoyi
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Inner {
    /**
     * 是否AOP统一处理
     * @return false, true
     */
    boolean value() default true;

    /**
     * 需要特殊判空的字段(预留)
     * @return {}
     */
    String[] field() default {};
}