package cn.shanxincd.plat.common.security.exception;

import org.springframework.security.authentication.AccountStatusException;

public class AccountPwdExpireException  extends AccountStatusException {


	public AccountPwdExpireException(String msg) {
		super(msg);
	}

	public AccountPwdExpireException(String msg, Throwable t) {
		super(msg, t);
	}

}
