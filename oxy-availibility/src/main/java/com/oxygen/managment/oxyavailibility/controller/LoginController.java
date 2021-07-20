package com.oxygen.managment.oxyavailibility.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oxygen.managment.oxyavailibility.pojo.LoginPojo;
import com.oxygen.managment.oxyavailibility.service.RegistrationService;

@Controller
public class LoginController {
	@Autowired
	public RegistrationService registationService;

	@GetMapping("/login")
	public String login() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//String currentPrincipalName = authentication.getName();
		return "index";
	}
	
	@CrossOrigin
	@PostMapping("/register_user")
	@ResponseBody
	public String registerUser(@RequestBody LoginPojo loginData) {
		
		System.out.println("inside register user"+loginData); 
		registationService.registerUser(loginData);
		return "success";
	}
		
}
