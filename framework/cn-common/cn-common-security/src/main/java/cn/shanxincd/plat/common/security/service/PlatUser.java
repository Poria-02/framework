package cn.shanxincd.plat.common.security.service;

import cn.hutool.core.util.StrUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 扩展用户信息
 */
public class PlatUser extends User implements OAuth2AuthenticatedPrincipal {
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	/**
	 * 用户ID
	 */
	@Getter
	private String id;

	/**
	 * 部门ID
	 */
	@Getter
	private Long deptId;

	/**
	 * 手机号
	 */
	@Getter
	private String phone;

	/**
	 * 头像
	 */
	@Getter
	private String avatar;

	//----------------------
	/**
	 * 网易云信Token
	 */
	@Getter
	@Setter
	private String imToken;

	/**
	 * 医护ID
	 */
	@Getter
	@Setter
	private String staffId;

	/**
	 * 医护类型
	 */
	@Getter
	@Setter
	private String staffType;

	@Getter
	@Setter
	private String userType;

	@Setter
	@Getter
	private String name;

	//----------------------

	public Long iDeptId() {
		if (deptId == null) {
			return null;
		}

		return deptId;
	}

	public Integer iId() {
		if (StrUtil.isBlank(id)) {
			return null;
		}

		return Integer.parseInt(id);
	}

	/**
	 *
	 * @param id 用户ID
	 * @param deptId 部门ID
	 * @param phone 手机号
	 * @param avatar 头像
	 * @param username username
	 * @param name 姓名
	 * @param password 密码
	 * @param enabled 是否可用
	 * @param accountNonExpired 账号是否过期
	 * @param credentialsNonExpired 密码是否过期
	 * @param accountNonLocked 账号是否锁定
	 * @param authorities 权限列表
	 */
	public PlatUser(
			String id,
			Long deptId,
			String phone,
			String avatar,
			String username,
			String name,
			String password,
			boolean enabled,
			boolean accountNonExpired,
			boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities
	)
	{
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

		this.id 		= id;
		this.deptId 	= deptId;
		this.phone 		= phone;
		this.avatar 	= avatar;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return new HashMap<>();
	}

	@Override
	public String getName() {
		return this.getUsername();
	}
}
