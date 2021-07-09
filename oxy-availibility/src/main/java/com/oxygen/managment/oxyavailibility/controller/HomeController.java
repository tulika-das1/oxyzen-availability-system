package com.oxygen.managment.oxyavailibility.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.oxygen.managment.oxyavailibility.pojo.CustomUser;
import com.oxygen.managment.oxyavailibility.pojo.OxygenRequestPojo;
import com.oxygen.managment.oxyavailibility.service.OxygenRequestService;

@Controller
public class HomeController {
	/*
	 * @GetMapping("/home") public String home() { return "home"; }
	 * 
	 */
	
	@Autowired
	OxygenRequestService reqService;
	
	@GetMapping("/request")
	public String request() {
		System.out.println(reqService.getReqDao().getJdbcTemplate());
		return "request";
		
	}
	
	@CrossOrigin
	@PostMapping("/generate_request")
	public OxygenRequestPojo genrateRequest(@RequestBody OxygenRequestPojo req) {
		
		req  = reqService.generateRequest(req);
		
		CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return req;
		
	}
	
}
