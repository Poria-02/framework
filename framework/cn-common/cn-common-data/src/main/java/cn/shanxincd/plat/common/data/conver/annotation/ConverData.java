package cn.shanxincd.plat.common.data.conver.annotation;


import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConverData {

	/**
	 * 绑定原始字段
	 * @return
	 */
	String key();

	/**
	 * 绑定结果字段 ,如果该字段为空,key为Id结尾的话,该字段自动替换为Name结尾
	 * @return
	 */
	String value() default "";

	/**
	 * 数据库原始字段 如果为空将使用key转换成下划线的形式
	 * @return
	 */
	String dbKey() default "";

	/**
	 * 数据库绑定字段,如果为空将使用value转换成下划线的形式
	 * @return
	 */

	String dbValue() default  "";
	/**
	 * 服务名,如果为空则在本地数据库查询,
	 * @return
	 */
	String serviceId() default "";

	/**
	 * 表名
	 * @return
	 */
	String table() ;

}
