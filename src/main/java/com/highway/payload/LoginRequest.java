package com.highway.payload;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

	@NotBlank(message = "Username can not be an empty.")
	private String username;
	@NotBlank(message = "Password can not be an empty.")
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
