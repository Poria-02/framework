package cn.shanxincd.plat.common.gateway.annotation;

import cn.shanxincd.plat.common.gateway.configuration.DynamicRouteAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启动态路由
 */
@Inherited
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(DynamicRouteAutoConfiguration.class)
public @interface EnablePlatDynamicRoute {}