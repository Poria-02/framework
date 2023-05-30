package cn.shanxincd.plat.common.data.mq.core.factory;

import cn.shanxincd.plat.common.data.mq.core.endpoint.RedisListenerEndpoint;
import cn.shanxincd.plat.common.data.mq.core.container.AbstractMessageListenerContainer;

public interface RedisListenerContainerFactory<V extends AbstractMessageListenerContainer> {

    V createListenerContainer(RedisListenerEndpoint endpoint);

    /** 最大线程数 */
    int getMaxConsumer();

    /** 死信重试次数 */
    int getRetryTimes();
}
