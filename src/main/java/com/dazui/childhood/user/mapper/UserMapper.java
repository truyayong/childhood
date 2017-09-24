package com.dazui.childhood.user.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.dazui.childhood.user.domin.User;

@Mapper
public interface UserMapper {
	@Insert("INSERT INTO user(name,password,description,avaterUrl) VALUES(#{name}, #{password}, #{description}, #{avaterUrl})")
	void add(User user);
	
	@Select("SELECT * FROM user WHERE name = #{name}")
	User findUserByName(User user);
	
	@Update("UPDATE user SET name=#{name} WHERE id =#{id}")
	void updateName(User user);
	
	@Update("UPDATE user SET password=#{password} WHERE id =#{id}")
	void updatePassword(User user);
	
	@Update("UPDATE user SET description=#{description} WHERE id =#{id}")
	void updateDescription(User user);
	
	@Update("UPDATE user SET avaterUrl=#{avaterUrl} WHERE id =#{id}")
	void updateAvaterUrl(User user);
}
