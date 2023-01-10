package cn.fuling.cn.common.core.constant;

/**
 * 缓存的key 常量
 */
public interface CacheConstants {
	/**
	 * 全局缓存，在缓存名称上加上该前缀表示该缓存不区分租户，比如:
	 */
	String GLOBALLY = "gl:";

	/**
	 * 验证码前缀
	 */
	String DEFAULT_CODE_KEY = "DEFAULT_CODE_KEY:";

	/**
	 * 菜单信息缓存
	 */
	String MENU_DETAILS = "menu_details";

	/**
	 * 用户信息缓存
	 */
	String USER_DETAILS = "user_details";

	/**
	 * 字典信息缓存
	 */
	String DICT_DETAILS = "dict_details";

	/**
	 * 标签信息缓存
	 */
	String TAG_ITEMS = "tag_items";

	/**
	 * app版本
	 */
	String APP_VERSION = "app_version";

	/**
	 * 广告
	 */
	String BANNER_ITEM = "banner_item";

	/**
	 * 分级字典
	 */
	String TREE_DICT_DETAILS_TREE = "tree_dict_details_tree";
	String TREE_DICT_DETAILS_LIST = "tree_dict_details_list";
	String TREE_DICT_DETAILS_CITY = "tree_dict_details_city";

	/**
	 * oauth 客户端信息
	 */
	String CLIENT_DETAILS_KEY = "ih_oauth:client:details";

	/**
	 * spring boot admin 事件key
	 */
	String EVENT_KEY = "event_key";

	/**
	 * 路由存放
	 */
	String ROUTE_KEY = "gateway_route_key";

	/**
	 * redis reload 事件
	 */
	String ROUTE_REDIS_RELOAD_TOPIC = "gateway_redis_route_reload_topic";

	/**
	 * 内存reload 时间
	 */
	String ROUTE_JVM_RELOAD_TOPIC = "gateway_jvm_route_reload_topic";

	/**
	 * 参数缓存
	 */
	String PARAMS_DETAILS = "params_details";

	/**
	 * 租户缓存 (不区分租户)
	 */
	String TENANT_DETAILS = GLOBALLY + "tenant_details";

	String HIS_CONFIG_DETAILS = "hisConfig_details";

	/**
	 * 支付渠道
	 */
	String PAY_CHANNEL = "pay_channel";

	/**
	 * 药店缓存
	 */
	String DRUGSTORE_CONFIG_DETAILS = "drugstore_config_details";

	String DRUGSTORE_DETAILS = "drugstore_details";
}
