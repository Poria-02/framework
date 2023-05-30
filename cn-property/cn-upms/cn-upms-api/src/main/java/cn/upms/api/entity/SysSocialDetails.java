package cn.upms.api.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 系统社交登录账号表
 */
@Data
@Schema(description = "第三方账号信息")
@EqualsAndHashCode(callSuper = true)
public class SysSocialDetails extends Model<SysSocialDetails> {
	private static final long serialVersionUID = 1L;

	/**
	 * 主鍵
	 */
	@TableId
	@Schema(description =  "主键")
	private Integer id;

	/**
	 * 类型
	 */
	@NotBlank(message = "类型不能为空")
	@Schema(description =  "账号类型")
	private String type;

	/**
	 * 描述
	 */
	@Schema(description =  "描述")
	private String remark;

	/**
	 * appid
	 */
	@NotBlank(message = "账号不能为空")
	@Schema(description =  "appId")
	private String appId;

	/**
	 * app_secret
	 */
	@NotBlank(message = "密钥不能为空")
	@Schema(description =  "app secret")
	private String appSecret;

	/**
	 * 回调地址
	 */
	@Schema(description =  "回调地址")
	private String redirectUrl;

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
	 * 删除标记
	 */
	@TableLogic
	@Schema(description =  "删除标记,1:已删除,0:正常")
	private String delFlag;
}
