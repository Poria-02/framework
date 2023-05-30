package cn.upms.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 前端日志vo
 */
@Data
@Schema(description =  "前端日志展示对象")
public class PreLogVO {
	/**
	 * 请求url
	 */
	@Schema(description =  "请求url")
	private String url;

	/**
	 * 请求耗时
	 */
	@Schema(description =  "请求耗时")
	private String time;

	/**
	 * 请求用户
	 */
	@Schema(description =  "请求用户")
	private String user;

	/**
	 * 请求结果
	 */
	@Schema(description =  "请求结果0:成功9:失败")
	private String type;

	/**
	 * 请求传递参数
	 */
	@Schema(description =  "请求传递参数")
	private String message;

	/**
	 * 异常信息
	 */
	@Schema(description =  "异常信息")
	private String stack;

	/**
	 * 日志标题
	 */
	@Schema(description =  "日志标题")
	private String info;
}