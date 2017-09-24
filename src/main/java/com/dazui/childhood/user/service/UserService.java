package com.dazui.childhood.user.service;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dazui.childhood.user.domin.User;
import com.dazui.childhood.user.mapper.UserMapper;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	public User add(User user) {
		userMapper.add(user);
		return userMapper.findOne(user);
	}
	
	public User findById(int id) {
		User user =new User();
		user.setId(id);
		return userMapper.findOne(user);
	}
	
	public User findByName(String name) {
		User user = new User();
		user.setName(name);
		return userMapper.findOne(user);
	}
	
	public User updateAvaterUrl(User user, String avaterUrl) {
		User dbUser = userMapper.findOne(user);
		dbUser.setAvaterUrl(avaterUrl);
		userMapper.updateAvaterUrl(dbUser);
		return dbUser;
	}
	
	public boolean comparePassword(@NotNull User remoteUser,@NotNull User locacalUser) {
		return remoteUser.getPassword().equals(locacalUser.getPassword());
	}
}
