package cn.common.core.util;

import cn.common.core.exception.ServiceException;
import cn.hutool.core.util.StrUtil;


/**
 * copy的 hutool的Assert类,将抛出异常改为ServiceException
 */
public class Assert {


	/**
	 * 断言是否为真，如果为 {@code false} 抛出 {@code IllegalArgumentException} 异常<br>
	 *
	 * <pre class="code">
	 * Assert.isTrue(i &gt; 0, "The value must be greater than zero");
	 * </pre>
	 *
	 * @param expression 布尔值
	 * @param errorMsgTemplate 错误抛出异常附带的消息模板，变量用{}代替
	 * @param params 参数列表
	 * @throws IllegalArgumentException if expression is {@code false}
	 */
	public static void isTrue(boolean expression, String errorMsgTemplate, Object... params) throws IllegalArgumentException {
		if (false == expression) {
			throw new ServiceException(StrUtil.format(errorMsgTemplate, params));
		}
	}


	/**
	 * 断言是否为假，如果为 {@code true} 抛出 {@code IllegalArgumentException} 异常<br>
	 *
	 * <pre class="code">
	 * Assert.isFalse(i &lt; 0, "The value must be greater than zero");
	 * </pre>
	 *
	 * @param expression 布尔值
	 * @param errorMsgTemplate 错误抛出异常附带的消息模板，变量用{}代替
	 * @param params 参数列表
	 * @throws IllegalArgumentException if expression is {@code false}
	 */
	public static void isFalse(boolean expression, String errorMsgTemplate, Object... params) throws IllegalArgumentException {
		if (expression) {
			throw new ServiceException(StrUtil.format(errorMsgTemplate, params));
		}
	}


	/**
	 * 断言对象是否不为{@code null} ，如果为{@code null} 抛出{@link ServiceException} 异常 Assert that an object is not {@code null} .
	 *
	 * <pre class="code">
	 * Assert.notNull(clazz, "The class must not be null");
	 * </pre>
	 *
	 * @param <T> 被检查对象泛型类型
	 * @param object 被检查对象
	 * @param errorMsgTemplate 错误消息模板，变量使用{}表示
	 * @param params 参数
	 * @return 被检查后的对象
	 * @throws IllegalArgumentException if the object is {@code null}
	 */
	public static <T> T notNull(T object, String errorMsgTemplate, Object... params) throws IllegalArgumentException {
		if (object == null) {
			throw new ServiceException(StrUtil.format(errorMsgTemplate, params));
		}
		return object;
	}


	/**
	 * 断言对象是否为{@code null} ，如果不为{@code null} 抛出{@link ServiceException} 异常
	 *
	 * <pre class="code">
	 * Assert.isNull(value, "The value must be null");
	 * </pre>
	 *
	 * @param object 被检查的对象
	 * @param errorMsgTemplate 消息模板，变量使用{}表示
	 * @param params 参数列表
	 * @throws IllegalArgumentException if the object is not {@code null}
	 */
	public static void isNull(Object object, String errorMsgTemplate, Object... params) throws IllegalArgumentException {
		if (object != null) {
			throw new ServiceException(StrUtil.format(errorMsgTemplate, params));
		}
	}


	/**
	 * 检查给定字符串是否为空白（null、空串或只包含空白符），为空抛出 {@link IllegalArgumentException}
	 *
	 * <pre class="code">
	 * Assert.notBlank(name, "Name must not be blank");
	 * </pre>
	 *
	 * @param text 被检查字符串
	 * @param errorMsgTemplate 错误消息模板，变量使用{}表示
	 * @param params 参数
	 * @return 非空字符串
	 * @see StrUtil#isNotBlank(CharSequence)
	 * @throws IllegalArgumentException 被检查字符串为空白
	 */
	public static void notBlank(String text, String errorMsgTemplate, Object... params) throws IllegalArgumentException {
		if (StrUtil.isBlank(text)) {
			throw new ServiceException(StrUtil.format(errorMsgTemplate, params));
		}
	}

}
