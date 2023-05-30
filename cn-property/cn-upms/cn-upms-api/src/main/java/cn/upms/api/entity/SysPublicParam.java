package cn.upms.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 公共参数配置
 */
@Data
@Schema(description = "公共参数")
@EqualsAndHashCode(callSuper = true)
public class SysPublicParam extends Model<SysPublicParam> {
	private static final long serialVersionUID = 1L;
	/**
	 * 编号
	 */
	@TableId
	@Schema(description =  "公共参数编号")
	private Long publicId;

	/**
	 * 公共参数名称
	 */
	@Schema(description =  "公共参数名称", required = true, example = "公共参数名称")
	private String publicName;

	/**
	 * 公共参数地址值,英文大写+下划线
	 */
	@Schema(description =  "键[英文大写+下划线]", required = true, example = "PIGX_PUBLIC_KEY")
	private String publicKey;

	/**
	 * 值
	 */
	@Schema(description =  "值", required = true, example = "999")
	private String publicValue;

	/**
	 * 状态（1有效；2无效；）
	 */
	@Schema(description =  "标识[1有效；2无效]", example = "1")
	private String status;

	/**
	 * 删除状态（0：正常 1：已删除）
	 */
	@TableLogic
	@Schema(description =  "状态[0-正常，1-删除]", example = "0")
	private String delFlag;

	/**
	 * 公共参数编码
	 */
	@Schema(description =  "编码", example = "^(PIG|PIGX)$")
	private String validateCode;

	/**
	 * 创建时间
	 */
	@Schema(description =  "创建时间", example = "2019-03-21 12:28:48")
	private Date createTime;

	/**
	 * 修改时间
	 */
	@Schema(description =  "修改时间", example = "2019-03-21 12:28:48")
	private Date updateTime;

	/**
	 * 是否是系统内置
	 */
	@TableField(value = "`system`")
	@Schema(description =  "是否是系统内置")
	private String system;

	/**
	 * 配置类型：0-默认；1-检索；2-原文；3-报表；4-安全；5-文档；6-消息；9-其他
	 */
	@Schema(description =  "类型[1-检索；2-原文...]", example = "1")
	private String publicType;
}
