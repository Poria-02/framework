package cn.shanxincd.plat.common.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangchunlei
 * @date 2023年03月01日 5:34 PM
 */
@Data
public class RouteDto implements Serializable {

	private static final long serialVersionUID = 1L;


	@Schema(description =  "主键")
	private Integer id;

	/**
	 * 路由ID
	 */
	@Schema(description =  "路由id")
	private String routeId;

	/**
	 * 路由名称
	 */
	@Schema(description =  "路由名称")
	private String routeName;

	/**
	 * 断言
	 */
	@Schema(description =  "断言")
	private String predicates;

	/**
	 * 过滤器
	 */
	@Schema(description =  "过滤器")
	private String filters;

	/**
	 * uri
	 */
	@Schema(description =  "请求uri")
	private String uri;

	/**
	 * 排序
	 */
	@Schema(description =  "排序值")
	private Integer order;


	/**
	 * 创建时间
	 */
	@Schema(description =  "创建时间")
	private Date createTime;

	/**
	 * 修改时间
	 */
	@Schema(description =  "修改时间")
	private Date updateTime;

	@Schema(description = "元数据")
	private String metadata;


}
