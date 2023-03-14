package cn.common.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 前端类型类型
 */
@Getter
@AllArgsConstructor
public enum StyleTypeEnum {
	/**
	 * 前端类型-avue 风格
	 */
	AVUE("0", "avue 风格"),

	/**
	 * 前端类型-element 风格
	 */
	ELEMENT("1", "element 风格");

	/**
	 * 类型
	 */
	private final String style;
	/**
	 * 描述
	 */
	private final String description;
}
