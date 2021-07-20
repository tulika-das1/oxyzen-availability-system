package com.oxygen.managment.oxyavailibility.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.oxygen.managment.oxyavailibility.dao.LoginDao;
import com.oxygen.managment.oxyavailibility.pojo.LoginPojo;

@Service
public class RegistrationService {
	@Autowired
	public LoginDao loginDao;

	public LoginDao getLoginDao() {
		return loginDao;
	}

	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}
	
	public void registerUser(LoginPojo loginData) {
		
		loginDao.registerUser(loginData);
		
		
	}

}
