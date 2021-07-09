package com.oxygen.managment.oxyavailibility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCRYPTTest {

	public static void main(String[] args) {
	String b = new BCryptPasswordEncoder().encode("password");
	System.out.println(b);
	

	}

}
