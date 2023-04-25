package cn.common.fegin.annotation;

//import openfegin.CnFeignClientsRegistrar;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 自定义feign注解
 * 添加basePackages路径
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableFeignClients
//@Import(CnFeignClientsRegistrar.class)
public @interface EnableCnFeignClients {
    String[] value() default {};

    String[] basePackages() default {"cn.framwork"};

    Class<?>[] basePackageClasses() default {};

    Class<?>[] defaultConfiguration() default {};

    Class<?>[] clients() default {};
}
