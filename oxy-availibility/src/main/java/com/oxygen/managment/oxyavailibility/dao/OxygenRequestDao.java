package com.oxygen.managment.oxyavailibility.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import com.oxygen.managment.oxyavailibility.pojo.CustomUser;
import com.oxygen.managment.oxyavailibility.pojo.OxygenRequestPojo;
import com.oxygen.managment.oxyavailibility.utility.ApplicationConstant;

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
				new Object[]{req.getUserid(),req.getCylinderRequestNo(),date,
						req.getReqStatus(),req.getStockIncreaseComment()}
				);
	        
		 query = DaoQueryBuilder.GET_GENERATED_REQUEST_ID;
		Integer insertedReqId = jdbcTemplate.queryForObject(query,Integer.class);
		
		 
		
		if(i == 1) {
			req.setRequestId(insertedReqId);
			req.setDataInserted(true);
		}else {
			req.setErrorMsg("error in data insertion");
			req.setDataInserted(false);
		}
		
		return req;
		
	}
	
	public int deleteRequest(OxygenRequestPojo req) {
		String query = DaoQueryBuilder.DELETE_REQUEST_BY_REQUEST_ID;
		int i = jdbcTemplate.update(query,
			new Object[]{req.getRequestId()}
				);
		
		return i;
	}
	
	
	public List<OxygenRequestPojo> getActiveRequestList(){
		String query = DaoQueryBuilder.SELECT_ACTIVE_REQUEST;
		List<OxygenRequestPojo> oxygenReqList = jdbcTemplate.query(query, (rs,rownum)->{
			int reqId = rs.getInt("OAR_REQUEST_ID");
			String displayName = rs.getString("OAU_NAME");
			int noOfCylider = rs.getInt("OAR_CYLINDER_NO");
			String reqStatus = rs.getString("OAR_APPROVAL_STATE");
			Date reqDate = rs.getDate("OAR_REQ_DATE");
			int userId = rs.getInt("OAR_USERID");
			/////
			OxygenRequestPojo dto = new OxygenRequestPojo();
			dto.setCylinderRequestNo(noOfCylider);
			dto.setRequestId(reqId);
			dto.setRequestDate(reqDate);
			dto.setRequester(displayName);
			dto.setReqStatus(reqStatus);
			dto.setUserid(userId);
			return dto;
		});
		return oxygenReqList;
	}
	
	public OxygenRequestPojo updateRequest(OxygenRequestPojo req) {
		
		String query = DaoQueryBuilder.UPDATE_REQUEST_BY_ID;
		
		int  i = jdbcTemplate.update(query,
				new Object[]{req.getCylinderRequestNo(),req.getRequestId()}
				);
		
		return req;
	}

	public OxygenRequestPojo updateRequestStats(OxygenRequestPojo req) {
		String approvalQuery = DaoQueryBuilder.APPROVE_REQUEST_STATUS_BY_ID;
		String rejectQuery = DaoQueryBuilder.REJECT_REQUEST_STATUS_BY_ID;
		String reqCompletionQuery = DaoQueryBuilder.COMPLETE_REQUEST_STATUS_BY_ID;
		
		
		String query = ApplicationConstant.REQ_STATUS_APPROVED.equalsIgnoreCase(req.getReqStatus()) 
				? approvalQuery 
				:ApplicationConstant.REQ_STATUS_COMPLETE.equalsIgnoreCase(req.getReqStatus()) 
					?reqCompletionQuery:rejectQuery;
		
		int  i = jdbcTemplate.update(query,
				new Object[]{req.getApprovalRejectionComment(),req.getReqStatus(),req.getRequestId()}
				);
		if(i == 0) {
			req.setDataInserted(false);
			req.setErrorMsg("No Updation done on this request");
		}
		
		return req;
	}
	
	
	
	
}
