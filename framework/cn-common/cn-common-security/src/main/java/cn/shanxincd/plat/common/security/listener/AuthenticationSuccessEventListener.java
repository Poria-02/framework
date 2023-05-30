//package cn.shanxincd.plat.common.security.listener;
//
//import cn.hutool.core.collection.CollUtil;
//import cn.shanxincd.plat.common.security.handler.AuthenticationSuccessHandler;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//
///**
// * 认证成功事件监听器
// */
//public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {
//
//	@Autowired(required = false)
//	private AuthenticationSuccessHandler successHandler;
//
//	/**
//	 * Handle an application event.
//	 * @param event the event to respond to
//	 */
//	@Override
//	public void onApplicationEvent(AuthenticationSuccessEvent event) {
//		Authentication authentication = (Authentication) event.getSource();
//		if (CollUtil.isNotEmpty(authentication.getAuthorities()) && successHandler != null) {
//			ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//			HttpServletRequest request 	 = requestAttributes.getRequest();
//			HttpServletResponse response = requestAttributes.getResponse();
//
//			successHandler.handle(authentication, request, response);
//		}
//	}
//}
