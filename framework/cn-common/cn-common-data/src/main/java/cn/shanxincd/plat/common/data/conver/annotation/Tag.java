package cn.shanxincd.plat.common.data.conver.annotation;


import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Tag {

	/**
	 * 字典编码
	 * @return
	 */
	String tagKey();

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

	/**
	 * 标签取值字段
	 */
	String itemKey();


}
