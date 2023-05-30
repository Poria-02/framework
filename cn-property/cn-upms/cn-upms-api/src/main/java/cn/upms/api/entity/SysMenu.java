package cn.upms.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 菜单权限表
 */
@Data
@Schema(description = "菜单")
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends Model<SysMenu> {
	private static final long serialVersionUID = 1L;

	/**
	 * 菜单ID
	 */
	@TableId(value = "menu_id", type = IdType.AUTO)
	@Schema(description =  "菜单id")
	private Long menuId;

	/**
	 * 菜单名称
	 */
	@NotBlank(message = "菜单名称不能为空")
	@Schema(description =  "菜单名称")
	private String name;

	/**
	 * 菜单权限标识
	 */
	@Schema(description =  "菜单权限标识")
	private String permission;

	/**
	 * 父菜单ID
	 */
	@NotNull(message = "菜单父ID不能为空")
	@Schema(description =  "菜单父id")
	private Long parentId;

	/**
	 * 图标
	 */
	@Schema(description =  "菜单图标")
	private String icon;

	/**
	 * 前端路由标识路径，默认和 comment 保持一致
	 * 过期
	 */
	@Schema(description =  "前端路由标识路径")
	private String path;

	/**
	 * 排序值
	 */
	@Schema(description =  "排序值")
	private Integer sort;

	/**
	 * 菜单类型 （0菜单 1按钮）
	 */
	@NotNull(message = "菜单类型不能为空")
	@Schema(description =  "菜单类型,0:菜单 1:按钮")
	private String type;

	/**
	 * 路由缓冲
	 */
	@Schema(description =  "路由缓冲")
	private String keepAlive;

	/**
	 * 创建时间
	 */
	@Schema(description =  "创建时间")
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@Schema(description =  "更新时间")
	private LocalDateTime updateTime;

	/**
	 * 0--正常 1--删除
	 */
	@TableLogic
	@Schema(description =  "删除标记,1:已删除,0:正常")
	private String delFlag;
}
