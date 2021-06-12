package com.oxygen.managment.oxyavailibility.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {
	/*
	 * @GetMapping("/home") public String home() { return "home"; }
	 * 
	 */
	@GetMapping("/request")
	public String request() {
		
		return "request";
		
	}
	
}
