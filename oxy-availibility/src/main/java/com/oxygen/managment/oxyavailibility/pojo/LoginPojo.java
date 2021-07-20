package com.oxygen.managment.oxyavailibility.pojo;

public class LoginPojo {
	
	@Override
	public String toString() {
		return "LoginPojo [name=" + name + ", userId=" + userId + ", password=" + password + ", askedForAdminRights="
				+ askedForAdminRights + "]";
	}
	String name;
	String userId;
	String password;
	boolean askedForAdminRights;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isAskedForAdminRights() {
		return askedForAdminRights;
	}
	public void setAskedForAdminRights(boolean askedForAdminRights) {
		this.askedForAdminRights = askedForAdminRights;
	}
	

}
