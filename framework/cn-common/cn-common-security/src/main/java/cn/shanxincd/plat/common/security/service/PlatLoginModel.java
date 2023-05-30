package cn.shanxincd.plat.common.security.service;

import lombok.Data;

import java.util.Map;

/**
 * @author zhangchunlei
 * @date 2021年06月05日 11:40 上午
 */
@Data
public class PlatLoginModel {

	//用户名
	private String username;

	//密码
	private String password;

	//登陆方式
	private String loginType;

	//用户类型
	private String userType;

	//验证码
	private String verifyCode;

	//验证码随机数
	private String randomStr;

	//appId
	private String appId;

	//scope
	private String scope;

	private Map<String, Object> additionalParameters;

	public PlatLoginModel(){

	}
}
