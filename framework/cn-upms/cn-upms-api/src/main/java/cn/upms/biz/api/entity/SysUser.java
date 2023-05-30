package cn.upms.biz.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用户表
 */
@Data
public class SysUser implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "user_id", type = IdType.AUTO)
	@Schema(description = "主键id")
	private Long userId;

	/**
	 * 用户名
	 */
	@Schema(description = "用户名")
	private String username;

	/**
	 * 密码
	 */
	@Schema(description = "密码")
	private String password;

	/**
	 * 随机盐
	 */
	@JsonIgnore
	@Schema(description = "随机盐")
	private String salt;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	@Schema(description = "修改时间")
	private LocalDateTime updateTime;

	/**
	 * 0-正常，1-删除
	 */
	@TableLogic
	@Schema(description ="删除标记,1:已删除,0:正常")
	private String delFlag;

	/**
	 * 锁定标记
	 */
	@Schema(description =  "锁定标记")
	private String lockFlag;

	/**
	 * 手机号
	 */
	@Schema(description =  "手机号")
	private String phone;

	/**
	 * 头像
	 */
	@Schema(description =  "头像地址")
	private String avatar;

	/**
	 * 部门ID
	 */
	@Schema(description =  "用户所属部门id")
	private Long deptId;

	/**
	 * 租户ID
	 */
	@Schema(description =  "用户所属租户id")
	private Integer tenantId;

	/**
	 * 微信openid
	 */
	@Schema(description =  "微信openid")
	private String wxOpenid;

	/**
	 * 微信小程序openId
	 */
	@Schema(description =  "微信小程序openid")
	private String miniOpenid;

	/**
	 * QQ openid
	 */
	@Schema(description =  "QQ openid")
	private String qqOpenid;

	/**
	 * 码云唯一标识
	 */
	@Schema(description =  "码云唯一标识")
	private String giteeLogin;

	/**
	 * 开源中国唯一标识
	 */
	@Schema(description =  "开源中国唯一标识")
	private String oscId;

	/**
	 * 网易云信Token
	 */
	@Schema(description =  "网易云信Token")
	private String yxToken;

	private Date lastPwdUpdateTime;
}
