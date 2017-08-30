package com.dazui.childhood.user.domin;

import java.io.Serializable;
/**
 * 
 * @author lidazui
 *
 */
public class User implements Serializable {

	private static final long serialVersionUID = 6788293813570785123L;
	
	private Integer id;
	
	private String name;
	
	private String password;
	
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
