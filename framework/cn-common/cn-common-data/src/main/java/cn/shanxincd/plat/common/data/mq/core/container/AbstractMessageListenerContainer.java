package cn.shanxincd.plat.common.data.mq.core.container;

import lombok.Data;
import cn.hutool.json.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;

@Data
public abstract class AbstractMessageListenerContainer {
	private volatile boolean quit;

	private String id;
	private Object bean;
	private Method method;
	private String[] queueName;

	private int retryTimes;
	private int maxConsumer;

	private RedisTemplate<String, String> redisTemplate;

	public abstract void doStart();

	public void start() {
		doStart();
	}

	protected void invoke(JSONObject json) throws Exception {
		try {
			method.invoke(bean, json);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
