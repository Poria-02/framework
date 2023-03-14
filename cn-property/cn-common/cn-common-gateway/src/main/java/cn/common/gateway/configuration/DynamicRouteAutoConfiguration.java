package cn.common.gateway.configuration;

import cn.common.core.constant.CacheConstants;
import cn.common.gateway.support.RouteCacheHolder;
import io.lettuce.core.ReadFrom;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.config.PropertiesRouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.time.Duration;

/**
 * 动态路由配置类
 */
@Slf4j
@Configuration
//@ComponentScan("cn.fuling.cn.common.gateway")
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class DynamicRouteAutoConfiguration {

	/**
	 * 配置文件设置为空 redis 加载为准
	 */
	@Bean
	public PropertiesRouteDefinitionLocator propertiesRouteDefinitionLocator() {
		return new PropertiesRouteDefinitionLocator(new GatewayProperties());
	}

	/**
	 * redis 监听配置
	 * @param redisConnectionFactory redis 配置
	 */
	@Bean
	public RedisMessageListenerContainer redisContainer(RedisConnectionFactory redisConnectionFactory) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(redisConnectionFactory);
		container.addMessageListener((message, bytes) -> {
			log.warn("接收到重新JVM 重新加载路由事件");
			RouteCacheHolder.removeRouteList();
		}, new ChannelTopic(CacheConstants.ROUTE_JVM_RELOAD_TOPIC));

		return container;
	}

	/**
	 * redis 连接池
	 */
	@Bean
	@ConditionalOnProperty(value = "spring.redis.cluster.enable", havingValue = "true")
	public LettuceConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
		RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(redisProperties.getCluster().getNodes());
		ClusterTopologyRefreshOptions clusterTopologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
			.enablePeriodicRefresh()
			.enableAllAdaptiveRefreshTriggers()
			.refreshPeriod(Duration.ofSeconds(5))
			.build();

		ClusterClientOptions clusterClientOptions = ClusterClientOptions.builder().topologyRefreshOptions(clusterTopologyRefreshOptions).build();
		LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
			.readFrom(ReadFrom.REPLICA_PREFERRED)
			.clientOptions(clusterClientOptions).build();

		return new LettuceConnectionFactory(redisClusterConfiguration, lettuceClientConfiguration);
	}
}
