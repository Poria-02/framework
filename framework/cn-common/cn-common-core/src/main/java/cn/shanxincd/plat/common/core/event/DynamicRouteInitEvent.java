package cn.shanxincd.plat.common.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author zhangchunlei
 * @date 2023年03月01日 5:55 PM
 */
public class DynamicRouteInitEvent extends ApplicationEvent {

	public DynamicRouteInitEvent(Object source) {
		super(source);
	}

}
