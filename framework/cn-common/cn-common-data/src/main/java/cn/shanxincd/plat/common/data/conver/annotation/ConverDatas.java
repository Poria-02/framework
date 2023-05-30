package cn.shanxincd.plat.common.data.conver.annotation;

import java.lang.annotation.*;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConverDatas {
	ConverData[] value();
}
