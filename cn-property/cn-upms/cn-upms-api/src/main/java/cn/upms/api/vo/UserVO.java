package cn.upms.api.vo;

import cn.shanxincd.plat.common.core.sensitive.Sensitive;
import cn.shanxincd.plat.common.core.sensitive.SensitiveTypeEnum;
import cn.upms.api.entity.SysRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "前端用户展示对象")
public class UserVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Schema(description = "主键")
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
    @Schema(description = "随机盐")
    private String salt;

    /**
     * 微信openid
     */
    @Schema(description = "微信open id")
    private String wxOpenid;

    /**
     * QQ openid
     */
    @Schema(description = "qq open id")
    private String qqOpenid;

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
    @Schema(description = "删除标记,1:已删除,0:正常")
    private String delFlag;

    /**
     * 锁定标记
     */
    @Schema(description = "锁定标记,0:正常,9:已锁定")
    private String lockFlag;

    /**
     * 手机号
     */
    @Sensitive(type = SensitiveTypeEnum.MOBILE_PHONE)
    @Schema(description = "手机号")
    private String phone;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

    /**
     * 部门ID
     */
    @Schema(description = "所属部门")
    private Integer deptId;

    /**
     * 租户ID
     */
    @Schema(description = "所属租户")
    private Integer tenantId;

    /**
     * 部门名称
     */
    @Schema(description = "所属部门名称")
    private String deptName;

    /**
     * 角色列表
     */
    @Schema(description = "拥有的角色列表")
    private List<SysRole> roleList;
}
