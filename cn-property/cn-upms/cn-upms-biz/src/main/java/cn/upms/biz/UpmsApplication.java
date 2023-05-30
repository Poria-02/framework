package cn.upms.biz;

import cn.shanxincd.plat.common.feign.annotation.EnablePlatFeignClients;
import cn.shanxincd.plat.common.security.annotation.EnablePlatResourceServer;
import cn.shanxincd.plat.common.swagger.annotation.EnablePaltDoc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 用户统一管理系统
 */
@Slf4j
@EnablePaltDoc
@EnablePlatResourceServer
@EnablePlatFeignClients
@SpringBootApplication
public class UpmsApplication {
	public static void main(String[] args) throws UnknownHostException {
		SpringApplication app = new SpringApplication(UpmsApplication.class);
		Environment env = app.run(args).getEnvironment();
		log.info(
				"\n----------------------------------------------------------\n\t"
						+ "Application '{}' is running! Access URLs:\n\t"
						+ "Local: \t\thttp://localhost:{}\n\t"
						+ "External: \thttp://{}:{}\n----------------------------------------------------------",
				env.getProperty("spring.application.name"), env.getProperty("server.port"),
				InetAddress.getLocalHost().getHostAddress(), env.getProperty("server.port"));
	}
}
