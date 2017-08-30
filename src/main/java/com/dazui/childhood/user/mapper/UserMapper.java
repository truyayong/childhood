package com.dazui.childhood.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.dazui.childhood.user.domin.User;

@Mapper
public interface UserMapper {
	int add(User user);
	User findOne(User user);
}
