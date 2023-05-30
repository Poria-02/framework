package cn.shanxincd.plat.common.data.conver.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DefaultValue {


	String field();

	String key();

}
