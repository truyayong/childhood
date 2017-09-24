package com.dazui.childhood.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.dazui.childhood.interceptor.SessionInterceptor;
import com.dazui.childhood.solr.controller.SolrController;
/**
 * 登陆session配置
 * @author lidazui
 *
 */
@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter {
	
	public final static String SESSION_USER_ID = "user_id";
	public final static String SESSION_USER_NAME = "user_name";
	public final static String SESSION_USER_OBJ = "user_obj";
	
	private Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration interceptorRegistration = registry.addInterceptor(getSessionInterceptor());
		//排除配置
//		interceptorRegistration.excludePathPatterns("/error");
//		interceptorRegistration.excludePathPatterns("/login**");
//		interceptorRegistration.excludePathPatterns("/register**");
//		interceptorRegistration.excludePathPatterns("/getStsToken**");
//		interceptorRegistration.excludePathPatterns("/callback**");
//		interceptorRegistration.excludePathPatterns("/testSolr**");
		//拦截配置
		interceptorRegistration.addPathPatterns("/loginiiiii**");
	}
	
	@Bean
	public SessionInterceptor getSessionInterceptor() {
		return new SessionInterceptor();
	}
}
