package cn.shanxincd.plat.common.data.conver.annotation;

import cn.shanxincd.plat.common.data.conver.DictType;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Dict {


	/**
	 * 字典类型
	 * @return
	 */
	DictType dictType() default DictType.ITEM;

	/**
	 * 字典编码
	 * @return
	 */
	String dictCode();

	/**
	 * 字典code取值字段
	 * @return
	 */
	String key();

	/**
	 * 字典值绑定字段
	 * @return
	 */
	String value();


}
