package cn.upms.biz.service;

import cn.upms.biz.api.dto.UserInfo;
import cn.upms.biz.api.entity.SysSocialDetails;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 系统社交登录账号表
 */
public interface SysSocialDetailsService extends IService<SysSocialDetails> {
	/**
	 * 绑定社交账号
	 */
	Boolean bindSocial(String state, String code);

	/**
	 * 根据入参查询用户信息
	 */
	UserInfo getUserInfo(String inStr);
}

