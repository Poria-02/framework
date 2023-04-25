package cn.common.fegin;

import cn.common.fegin.endpoint.FeignClientEndpoint;
import feign.Feign;
//import openfegin.CnFeignClientsRegistrar;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * feign 自动化配置
 */
@Configuration
@ConditionalOnClass(Feign.class)
//@Import(CnFeignClientsRegistrar.class)
@AutoConfigureAfter(EnableFeignClients.class)
public class CnFeignAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnAvailableEndpoint
    public FeignClientEndpoint feignClientEndpoint(ApplicationContext context) {
        return new FeignClientEndpoint(context);
    }
}
