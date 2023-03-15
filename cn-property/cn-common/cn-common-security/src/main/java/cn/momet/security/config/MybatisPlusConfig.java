package cn.momet.security.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:远山
 * @Date: 2023/3/15  9:04
 * @Version 1.0
 */
@Configuration
public class MybatisPlusConfig {
	/**
	 * 分页插件配置
	 *
	 * @return
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor(){
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		PaginationInnerInterceptor innerInterceptor=new PaginationInnerInterceptor();
		innerInterceptor.setDbType(DbType.MYSQL);
		innerInterceptor.setOverflow(true);
		interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
		interceptor.addInnerInterceptor(innerInterceptor);
		return interceptor;
	}
}
