package com.hexaware.app.Dto;

import com.hexaware.app.Enums.UserRole;

public class AuthenticationResponse {
	private String jwt;
    private UserRole userRole;
    private Long userId;
    private String name;
    private String email;
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	public UserRole getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "AuthenticationResponse [jwt=" + jwt + ", userRole=" + userRole + ", userId=" + userId + ",email="+ email +",name="+ name +"]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
