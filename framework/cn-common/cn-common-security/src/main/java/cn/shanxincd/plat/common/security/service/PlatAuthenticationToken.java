package cn.shanxincd.plat.common.security.service;

import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author zhangchunlei
 * @date 2021年06月05日 10:08 上午
 */
public class PlatAuthenticationToken extends AbstractAuthenticationToken {


	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	@Getter
	private final AuthorizationGrantType authorizationGrantType;

	@Getter
	private final Authentication clientPrincipal;

	@Getter
	private final Set<String> scopes;

	@Getter
	private final Map<String, Object> additionalParameters;

	@Getter
	private PlatLoginModel loginModel ;

	public PlatAuthenticationToken(PlatLoginModel loginModel, AuthorizationGrantType authorizationGrantType,
                                 Authentication clientPrincipal, @Nullable Set<String> scopes,
                                 @Nullable Map<String, Object> additionalParameters) {

		super(Collections.emptyList());
		Assert.notNull(authorizationGrantType, "authorizationGrantType cannot be null");
		Assert.notNull(clientPrincipal, "clientPrincipal cannot be null");
		this.loginModel = loginModel;
		this.authorizationGrantType = authorizationGrantType;
		this.clientPrincipal = clientPrincipal;
		this.scopes = Collections.unmodifiableSet(scopes != null ? new HashSet<>(scopes) : Collections.emptySet());
		this.additionalParameters = Collections.unmodifiableMap(
				additionalParameters != null ? new HashMap<>(additionalParameters) : Collections.emptyMap());
	}

	@Override
	public Object getCredentials() {
		return this.clientPrincipal.getCredentials();
	}

	@Override
	public Object getPrincipal() {
		return this.clientPrincipal;
	}
}
