package cn.common.gateway;

//工具包配置缺少
import cn.common.gateway.annotation.EnableIhDynamicRoute;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 网关应用
 */
@EnableIhDynamicRoute
@SpringCloudApplication
public class Gateway {

	public static void main(String[] args) {
		SpringApplication.run(Gateway.class, args);
	}
}
