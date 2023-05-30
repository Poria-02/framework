package cn.shanxincd.plat.common.data.mq.core.factory;

import cn.shanxincd.plat.common.data.mq.core.container.SimpleMessageListenerContainer;

public class SimpleRedisListenerContainerFactory extends AbstractRedisListenerContainerFactory<SimpleMessageListenerContainer> {

    @Override
    public SimpleMessageListenerContainer createContainerInstance() {
        return new SimpleMessageListenerContainer();
    }

}
