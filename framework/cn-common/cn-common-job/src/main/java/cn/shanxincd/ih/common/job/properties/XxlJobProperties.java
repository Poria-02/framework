package cn.shanxincd.ih.common.job.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProperties {
	private XxlAdminProperties admin = new XxlAdminProperties();
	private XxlExecutorProperties executor = new XxlExecutorProperties();
}