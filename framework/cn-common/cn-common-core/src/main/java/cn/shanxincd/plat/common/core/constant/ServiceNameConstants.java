package cn.shanxincd.plat.common.core.constant;

/**
 * 服务名称
 */
public interface ServiceNameConstants {
	/** 认证中心 */
	String AUTH_SERVICE = "${UNHM_auth:http://unhm-auth:3000}";

	/** UMPS模块 */
	String UPMS_SERVICE = "${UNHM_UPMS:http://unhm-upms:4000}";

	/** 支付 **/
	String PAY_SERVICE = "${UNHM_PAY:http://unhm-pay:5010}";

	/** 订单 **/
	String ORDER_SERVICE = "${UNHM_ORDER:http://unhm-order:8002}";

	/** 机构 **/
	String ORGANIZATION_SERVICE = "${UNHM_ORGANIZATION:http://unhm-organization:8003}";

	/** 用户 **/
	String CUSTOMER_SERVICE = "${UNHM_CUSTOMER:http://unhm-customer:8004}";

	/** 医护 **/
	String STAFF_SERVICE = "${UNHM_STAFF:http://unhm-staff:8005}";

	String MESSAGE_SERVICE = "${UNHM_MESSAGE:http://unhm-message:8008}";

	String BASE_SERVICE = "${UNHM_BASE:http://unhm-base:8009}";

	/** 商品 **/
	String PRODUCT_SERVICE = "${UNHM_PRODUCT:http://unhm-product:8011}";

	/**
	 * 推荐
	 */
	String RECOMMEND_SERVICE = "${UNHM_RECOMMEND:http://unhm-recommend:8015}";
	/**
	 * 处方
	 */
	String PRESCRIPTION_SERVICE = "${UNHM_PRESCRIPTION:http://unhm-prescription:8020}";


	String RPQ_SERVICE = "${UNHM_RPQ:http://unhm-rpq:8021}";


	/**
	 * 微信小程序
	 */
	String WXMA_SERVICE = "${UNHM_WXMA:http://unhm-wxma:8023}";

	/**
	 * 物流
	 */
	String TRANSPORT_SERVICE = "${UNHM_TRANSPORT:http://unhm-transport:8025}";


	String THIRD_SERVICE = "${UNHM_THIRD:http://unhm-third:8026}";

	String ARCHIVE_SERVICE = "${UNHM_ARCHIVE:http://unhm-archive:8028}";

	String INQUIRY_SERVICE = "${UNHM_ARCHIVE:http://unhm-archive}";

	String ALIMA_SERVICE = "${UNHM_ALIMA:http://unhm-alima:8034}";

	String RIGHT_SERVICE = "${UNHM_RIGHT:http://unhm-right:8007}";









}