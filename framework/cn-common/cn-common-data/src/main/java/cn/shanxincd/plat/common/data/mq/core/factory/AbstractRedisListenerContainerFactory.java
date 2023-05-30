package cn.shanxincd.plat.common.data.mq.core.factory;

import cn.shanxincd.plat.common.data.mq.core.endpoint.RedisListenerEndpoint;
import cn.shanxincd.plat.common.data.mq.core.container.AbstractMessageListenerContainer;
import lombok.Data;

@Data
public abstract class AbstractRedisListenerContainerFactory<V extends AbstractMessageListenerContainer> implements RedisListenerContainerFactory<V> {

    private int retryTimes;
    private int maxConsumer;

    protected abstract V createContainerInstance();

    @Override
    public V createListenerContainer(RedisListenerEndpoint endpoint) {
        V instance = createContainerInstance();

        instance.setId(endpoint.getId());
        instance.setBean(endpoint.getBean());
        instance.setMethod(endpoint.getMethod());
        instance.setQueueName(endpoint.getQueueName());
		instance.setRedisTemplate(endpoint.getRedisTemplate());

        instance.setRetryTimes(getRetryTimes());
        instance.setMaxConsumer(getMaxConsumer());

        return instance;
    }
}
