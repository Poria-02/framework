package cn.upms.biz.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 路由
 */
@Data
@Schema(description =  "网关路由信息")
@EqualsAndHashCode(callSuper = true)
public class SysRouteConf extends Model<SysRouteConf> {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@TableId(type = IdType.AUTO)
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
	@TableField(value = "`order`")
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

	/**
	 * 删除标识（0-正常,1-删除）
	 */
	@TableLogic
	@Schema(description =  "删除标记,1:已删除,0:正常")
	private String delFlag;
}
