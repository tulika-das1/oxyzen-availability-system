package com.oxygen.managment.oxyavailibility.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Types;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import com.oxygen.managment.oxyavailibility.pojo.CustomUser;
import com.oxygen.managment.oxyavailibility.pojo.OxygenRequestPojo;

@Repository
public class OxygenRequestDao {

	JdbcTemplate jdbcTemplate;
	
	public OxygenRequestDao(JdbcTemplate jdbcTemplate) {
		
		this.jdbcTemplate = jdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	public OxygenRequestPojo generateRequest(OxygenRequestPojo req) {
		String query= DaoQueryBuilder.GENERATE_OXYGEN_CYLINDER_REQUEST;
		
		 long millis= req.getRequestDate().getTime();  
	        java.sql.Date date=new java.sql.Date(millis);  
		
		int  i = jdbcTemplate.update(query,
				new Object[]{req.getUserid(),req.getCylinderRequestNo(),date}//
				//new Object[]{Types.INTEGER, Types.INTEGER,Types.DATE}
				);
		if(i == 1) {
			req.setDataInserted(true);
		}else {
			req.setErrorMsg("error in data insertion");
			req.setDataInserted(false);
		}
		
		return req;
		
	}
	
	
	
	
}
