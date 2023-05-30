package cn.upms.biz.handler;

import cn.upms.biz.api.dto.UserInfo;

/**
 * 登录处理器
 */
public interface LoginHandler {
	/***
	 * 数据合法性校验
	 * @param loginStr 通过用户传入获取唯一标识
	 */
	Boolean check(String loginStr);

	/**
	 * 通过用户传入获取唯一标识
	 */
	String identify(String loginStr);

	/**
	 * 通过openId 获取用户信息
	 */
	UserInfo info(String identify);

	/**
	 * 处理方法
	 */
	UserInfo handle(String loginStr);
}