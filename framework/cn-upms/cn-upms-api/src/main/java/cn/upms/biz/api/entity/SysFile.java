package cn.upms.biz.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 文件管理
 */
@Data
@Schema(description = "文件")
@EqualsAndHashCode(callSuper = true)
public class SysFile extends Model<SysFile> {
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId(type = IdType.AUTO)
	@Schema(description =  "文件编号")
	private Long id;

	/**
	 * 文件名
	 */
	@Schema(description =  "文件名")
	private String fileName;

	/**
	 * 原文件名
	 */
	@Schema(description =  "原始文件名")
	private String original;

	/**
	 * 容器名称
	 */
	@Schema(description =  "存储桶名称")
	private String bucketName;

	/**
	 * 文件类型
	 */
	@Schema(description =  "文件类型")
	private String type;

	/**
	 * 文件大小
	 */
	@Schema(description =  "文件大小")
	private Long fileSize;

	/**
	 * 上传人
	 */
	@Schema(description =  "创建者")
	private String createUser;

	/**
	 * 上传时间
	 */
	@Schema(description =  "创建时间")
	private LocalDateTime createTime;

	/**
	 * 更新人
	 */
	@Schema(description =  "更新者")
	private String updateUser;

	/**
	 * 更新时间
	 */
	@Schema(description =  "更新时间")
	private LocalDateTime updateTime;

	/**
	 * 删除标识：1-删除，0-正常
	 */
	@TableLogic
	@Schema(description =  "删除标记,1:已删除,0:正常")
	private Integer delFlag;
}
