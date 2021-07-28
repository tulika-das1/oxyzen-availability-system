package com.oxygen.managment.oxyavailibility.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.stereotype.Repository;

import com.oxygen.managment.oxyavailibility.BCRYPTTest;
import com.oxygen.managment.oxyavailibility.pojo.CustomUser;
import com.oxygen.managment.oxyavailibility.pojo.LoginPojo;
import com.oxygen.managment.oxyavailibility.pojo.OxygenRequestPojo;

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
	
	
	
	public LoginPojo registerUser(LoginPojo loginData) {
		String registerUserQuery=DaoQueryBuilder.REGISTER_USER_TO_USER_TABlE;
		String grantUserAccessQuery = DaoQueryBuilder.GRANT_ACCESS_TO_REGISTERED_USER;
		
		
		
		//encrypt password
		String encryptedPassword = BCRYPTTest.encryptData(loginData.getPassword());
		
		String askedForAdminRights = loginData.isAskedForAdminRights()== true ? "Y":"N";
		int userdataInserted = 0;
		int grantUserAccessGiven = 0;
		
		userdataInserted = jdbcTemplate.update(registerUserQuery,new Object[]{ loginData.getName(),loginData.getUserId(),encryptedPassword,askedForAdminRights});
		if(userdataInserted>0) {
			 grantUserAccessGiven = jdbcTemplate.update(grantUserAccessQuery,new Object[]{loginData.getUserId(),"ROLE_USER"});
		}
		
		/*
		 * System.out.println(grantUserAccessGiven);
		 * System.out.println(userdataInserted);
		 */
	    
	    if(userdataInserted == 1 && grantUserAccessGiven == 1) {
	    	loginData.setDataInserted(true);
	    }else {
	    	loginData.setErrorMsg("Registration failure");
	    }
	    
	    return loginData;
		
	}
	
	
	public CustomUser findUserByReqId(OxygenRequestPojo data) {
		String query = DaoQueryBuilder.SELECT_USER_FROM_REQUEST;
		CustomUser user = jdbcTemplate.queryForObject(query,  ( rs,  rowNum)->{
			String username1 = rs.getString("OAU_MOBILE_NO");
			String displayName = rs.getString("OAU_NAME");
			int id = rs.getInt("OAR_USERID");
			CustomUser received =  new CustomUser(username1, "", true, true, true, true, AuthorityUtils.NO_AUTHORITIES);
			received.setDisplayName(displayName);
			received.setId(id);
			return received;
		},new Object[]{data.getRequestId()});
		
		return user;
	}
	
	

}
