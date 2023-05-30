package cn.shanxincd.plat.common.security.constant;

/**
 * @author zhangchunlei
 * @date 2021年06月05日 12:03 下午
 */

public interface PlatLoginType {

	String ADMIN_PASSWORD = "ADMIN_PWD";

	String USER_PASSWORD = "USER_PWD";

	String STAFF_PASSWORD = "STAFF_PWD";

	String STAFF_VERIFY_CODE = "STAFF_VERIFY_CODE";

	/**
	 * 企业端用户名密码登陆
	 */
	String COMPANY_USER_NAME = "COMPANY_USER_NAME";

	/**
	 * 小程序登陆
	 */
	String MA_USER = "MA_USER";

	/**
	 * 小程序内部自动登陆使用
	 */
	String MA_USER_INNER = "MA_USER_INNER";

	/**
	 * 支付宝登录
	 */
	String ALI_PAY_USER = "ALI_PAY_USER";

	/**
	 *
	 */
	String ALI_PAY_USER_INNER = "ALI_PAY_USER_INNER";

}
