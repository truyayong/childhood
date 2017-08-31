package com.dazui.childhood.user.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.JSONObject;
import com.dazui.childhood.config.WebSecurityConfig;
import com.dazui.childhood.user.domin.User;
import com.dazui.childhood.user.service.UserService;

@RestController
@RequestMapping("")
public class UserController {
	
	public final static String SESSION_KEY = "user";
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public Object register(@RequestBody User user, HttpSession session) {
		logger.info("[truyayong] register");
		JSONObject jsonObject = new JSONObject();
		if(userService.findByName(user.getName()) != null) {
			jsonObject.put("message", "用户名已被使用");
			return jsonObject;
		}
		userService.add(user);
		jsonObject.put("message", "success");
		return jsonObject;
	}
	
	@PostMapping("/login")
	public Object login(@RequestBody User user, HttpSession session) {
		System.out.println("truyayong" + user.toString());
		if (user == null) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("message", "登录异常");
			return jsonObject;
		}
		User lUser = userService.findByName(user.getName());
		JSONObject jsonObject = new JSONObject();
		if (lUser == null) {
			jsonObject.put("message", "用户名未注册");
			return jsonObject;
		} else {
			if (!userService.comparePassword(user, lUser)) {
				jsonObject.put("message", "密码错误");
				return jsonObject;
			} else {
				session.setAttribute(SESSION_KEY, lUser.getName());
				session.getAttribute(SESSION_KEY);
				jsonObject.put("message", "登陆成功");
				return jsonObject;
			}
		}
	
	}
	
	@GetMapping("/logout")
	public Object logout(HttpSession session) {
		JSONObject jsonObject =new JSONObject();
		//remove session
		if (session != null) {
			jsonObject.put("message", "logout success");
			session.removeAttribute(WebSecurityConfig.SESSION_KEY);
		} else {
			jsonObject.put("message", "logout fail");
		}
		return jsonObject;
		
	}
}
