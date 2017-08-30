package com.dazui.childhood.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.dazui.childhood.interceptor.SessionInterceptor;
/**
 * 登陆session配置
 * @author lidazui
 *
 */
@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter {
	
	public final static String SESSION_KEY = "user";

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration interceptorRegistration = registry.addInterceptor(getSessionInterceptor());
		//排除配置
		interceptorRegistration.excludePathPatterns("/error");
		interceptorRegistration.excludePathPatterns("/login**");
		//拦截配置
		interceptorRegistration.addPathPatterns("/**");
		super.addInterceptors(registry);
	}
	
	@Bean
	public SessionInterceptor getSessionInterceptor() {
		return new SessionInterceptor();
	}
}
