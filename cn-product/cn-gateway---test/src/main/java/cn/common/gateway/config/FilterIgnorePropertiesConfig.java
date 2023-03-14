package cn.common.gateway.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 网关不校验终端配置
 */
@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "ignore")
@ConditionalOnExpression("!'${ignore}'.isEmpty()")    //ignore不为空时生效
public class FilterIgnorePropertiesConfig {
	private List<String> clients = new ArrayList<>();

	private List<String> swaggerProviders = new ArrayList<>();
}
