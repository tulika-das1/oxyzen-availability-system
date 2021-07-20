package com.oxygen.managment.oxyavailibility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCRYPTTest {
	static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	public static void main(String[] args) {
	String b = new BCryptPasswordEncoder().encode("password");
	System.out.println(b);
	
	}
	
	
	public static String encryptData(String data) {
		String encodedData = encoder.encode(data);
		return encodedData;
	}
	
}
