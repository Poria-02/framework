package cn.shanxincd.plat.common.data.mq.core;

import cn.shanxincd.plat.common.data.mq.core.container.AbstractMessageListenerContainer;
import cn.shanxincd.plat.common.data.mq.core.endpoint.RedisListenerEndpoint;
import cn.shanxincd.plat.common.data.mq.core.factory.RedisListenerContainerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RedisListenerEndpointRegistrar {
	private final Map<String, AbstractMessageListenerContainer> listenerContainers = new ConcurrentHashMap<>();

	public void startContainer() {
		for (AbstractMessageListenerContainer container : listenerContainers.values()) {
			container.start();
		}
	}

	public void registerEndpoint(RedisListenerEndpoint endpoint, RedisListenerContainerFactory<?> factory) {
		synchronized (this.listenerContainers) {
			if (listenerContainers.containsKey(endpoint.getId())) {
				return;
			}

			AbstractMessageListenerContainer listenerContainer = factory.createListenerContainer(endpoint);
			listenerContainers.put(endpoint.getId(), listenerContainer);
		}
	}

	public void stopAll() {
		startOrStop(true);
	}

	public void startAll() {
		startOrStop(false);
	}

	private void startOrStop(boolean quit) {
		for (AbstractMessageListenerContainer container : listenerContainers.values()) {
			container.setQuit(quit);
		}
	}

	public void startOne(String id) {
		AbstractMessageListenerContainer container = get(id);
		if (container != null) {
			container.setQuit(false);
		}
	}

	public void stopOne(String id) {
		AbstractMessageListenerContainer container = get(id);
		if (container != null) {
			container.setQuit(true);
		}
	}

	public AbstractMessageListenerContainer get(String id) {
		return listenerContainers.get(id);
	}

	public Map<String, AbstractMessageListenerContainer> getListenerContainers() {
		return listenerContainers;
	}
}
