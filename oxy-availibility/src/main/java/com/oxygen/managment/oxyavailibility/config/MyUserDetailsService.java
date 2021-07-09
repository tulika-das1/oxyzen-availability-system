package com.oxygen.managment.oxyavailibility.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oxygen.managment.oxyavailibility.dao.LoginDao;
import com.oxygen.managment.oxyavailibility.pojo.CustomUser;

@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private LoginDao loginDao;
	boolean enableAuthorities = true;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CustomUser customUser = loginDao.findUserById(username);
		if (customUser == null) {
			
			throw new UsernameNotFoundException("user not found");
		}
		UserDetails user = customUser;
		Set<GrantedAuthority> dbAuthsSet = new HashSet<>();
		if (this.enableAuthorities) {
			dbAuthsSet.addAll(loginDao.findAuthorityByUserId(user.getUsername()));
		}
		List<GrantedAuthority> dbAuths = new ArrayList<>(dbAuthsSet);
		if (dbAuths.size() == 0) {
			
			throw new UsernameNotFoundException("user has no authority and considered as not found ");
		}
		
		CustomUser custom =  new CustomUser(customUser.getUsername(), customUser.getPassword(), customUser.isEnabled(),
				 customUser.isAccountNonExpired(), customUser.isCredentialsNonExpired(),
				 customUser.isAccountNonLocked(), dbAuths);
		custom.setDisplayName(customUser.getDisplayName());
		custom.setId(customUser.getId());
		return custom;
		
		 
	}

}
