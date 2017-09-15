package com.dazui.childhood.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dazui.childhood.config.WebSecurityConfig;
import com.dazui.childhood.user.controller.UserController;

public class SessionInterceptor extends HandlerInterceptorAdapter{
	
	private Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
        HttpSession session = request.getSession();
        logger.info("[truyayong] preHandle");
        if (session.getAttribute(UserController.SESSION_KEY) != null) {
        	logger.info("[truyayong] preHandle true");
        	return true;
        }
        logger.info("[truyayong] preHandle false");
		return false;
	}
}
