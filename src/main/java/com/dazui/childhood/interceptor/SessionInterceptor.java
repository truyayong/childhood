package com.dazui.childhood.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dazui.childhood.user.controller.UserController;

public class SessionInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute(UserController.SESSION_KEY) != null) {
        	return true;
        }
		return false;
	}
}
