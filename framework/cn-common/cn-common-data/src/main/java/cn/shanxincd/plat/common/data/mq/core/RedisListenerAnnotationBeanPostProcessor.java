package cn.shanxincd.plat.common.data.mq.core;

import cn.hutool.core.util.StrUtil;
import cn.shanxincd.plat.common.data.mq.core.endpoint.RedisListenerEndpoint;
import cn.shanxincd.plat.common.data.mq.core.factory.RedisListenerContainerFactory;
import jakarta.annotation.Resource;
import lombok.Getter;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Getter
public class RedisListenerAnnotationBeanPostProcessor implements BeanPostProcessor, Ordered, BeanFactoryAware, SmartInitializingSingleton {

	private boolean isStarted;
	private BeanFactory beanFactory;

	@Resource(name = "MQ")
	private RedisTemplate<String, String> redisTemplate;

	private final RedisListenerEndpointRegistrar registrar = new RedisListenerEndpointRegistrar();

	@Override
	public void afterSingletonsInstantiated() {
		if (!isStarted) {
			registrar.startContainer();
		}

		isStarted = true;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	//Bean初始化完成后，检查有队列注解
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		Class<?> targetClass = AopUtils.getTargetClass(bean);
		final TypeMetadata metadata = buildMetadata(targetClass);

		for (ListenerMethod listenerMethod : metadata.listenerMethods) {
			processRedisListener(listenerMethod.annotation, listenerMethod.method, bean, beanName);
		}

		return bean;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

	private void processRedisListener(RedisListener redisListener, Method method, Object bean, String beanName) {
		Method methodToUse = checkProxy(method, bean);
		RedisListenerEndpoint endpoint = new RedisListenerEndpoint();

		endpoint.setBean(bean);
		endpoint.setMethod(methodToUse);
		endpoint.setQueueName(redisListener.queues());
		endpoint.setId(methodToUse.getDeclaringClass().getName() + "#" + methodToUse.getName());

		//容器工厂
		RedisListenerContainerFactory<?> factory;
		String containerFactoryBeanName = redisListener.containerFactory();
		if (StrUtil.isNotBlank(containerFactoryBeanName)) {
			Assert.state(this.beanFactory != null, "BeanFactory 不能为空");
			try {
				factory = this.beanFactory.getBean(containerFactoryBeanName, RedisListenerContainerFactory.class);
			} catch (BeansException ex) {
				throw new BeanInitializationException("没有找到containerFactoryBean=" + containerFactoryBeanName, ex);
			}
		}

		//默认容器 SimpleMessageListenerContainer
		else {
			try {
				factory = this.beanFactory.getBean("redisListenerContainerFactory", RedisListenerContainerFactory.class);
			} catch (BeansException ex) {
				throw new BeanInitializationException("没有找到containerFactoryBean=redisListenerContainerFactory", ex);
			}
		}

		//设置RedisTemplate
		endpoint.setRedisTemplate(redisTemplate);
		registrar.registerEndpoint(endpoint, factory);
	}

	private Method checkProxy(Method method, Object bean) {
		if (AopUtils.isJdkDynamicProxy(bean)) {
			try {
				method = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
				Class<?>[] proxiedInterfaces = ((Advised) bean).getProxiedInterfaces();
				for (Class<?> iface : proxiedInterfaces) {
					try {
						method = iface.getMethod(method.getName(), method.getParameterTypes());
						break;
					} catch (NoSuchMethodException noMethod) {
						System.out.println(noMethod.toString());
					}
				}
			}
			catch (SecurityException ex) {
				ReflectionUtils.handleReflectionException(ex);
			}
			catch (NoSuchMethodException ex) {
				throw new IllegalStateException("方法检查错误");
			}
		}

		return method;
	}

	private TypeMetadata buildMetadata(Class<?> targetClass) {
		final List<ListenerMethod> listenerMethods = new ArrayList<>();
		Method[] methods = targetClass.getDeclaredMethods();

		if (methods.length > 0) {
			for (Method method : methods) {
				//获取注解
				RedisListener redisListener = AnnotationUtils.findAnnotation(method, RedisListener.class);
				if (redisListener != null) {
					listenerMethods.add(new ListenerMethod(method, redisListener));
				}
			}
		}

		return new TypeMetadata(listenerMethods.toArray(new ListenerMethod[0]));
	}

	private static class ListenerMethod {
		final Method method;
		final RedisListener annotation;

		ListenerMethod(Method method, RedisListener annotation) {
			this.method = method;
			this.annotation = annotation;
		}
	}

	private static class TypeMetadata {
		//带有注解的方法，有没有必要检查一下参数
		final ListenerMethod[] listenerMethods;

		TypeMetadata(ListenerMethod[] methods) {
			this.listenerMethods = methods;
		}
	}
}