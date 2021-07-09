package com.oxygen.managment.oxyavailibility.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.stereotype.Repository;

import com.oxygen.managment.oxyavailibility.pojo.CustomUser;

@Repository
public class LoginDao {
	
JdbcTemplate jdbcTemplate;
	
	public LoginDao(JdbcTemplate jdbcTemplate) {
		
		this.jdbcTemplate = jdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	public CustomUser findUserById(String userId) {
		String query = DaoQueryBuilder.FIND_USER_BY_ID;
		CustomUser user = jdbcTemplate.queryForObject(query,  ( rs,  rowNum)->{
			String username1 = rs.getString(1);
			String password = rs.getString(2);
			boolean enabled = rs.getBoolean(3);
			String displayName = rs.getString("OAU_NAME");
			int id = rs.getInt("OAU_USER_ID");
			CustomUser received =  new CustomUser(username1, password, enabled, true, true, true, AuthorityUtils.NO_AUTHORITIES);
			received.setDisplayName(displayName);
			received.setId(id);
			return received;
		},new Object[]{userId});
		
		return user;
	}
	public List<GrantedAuthority> findAuthorityByUserId(String userId) {
		String query = DaoQueryBuilder.FIND_AUTHORITIES_BY_USER_ID;
		return getJdbcTemplate().query(query, new String[] { userId }, (rs, rowNum) -> {
			String roleName = ""+ rs.getString(2);
			return new SimpleGrantedAuthority(roleName);
		});
		//return null;
		
	}

}
