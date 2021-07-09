package com.oxygen.managment.oxyavailibility.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oxygen.managment.oxyavailibility.dao.OxygenRequestDao;
import com.oxygen.managment.oxyavailibility.pojo.OxygenRequestPojo;

@Service
public class OxygenRequestService {
	
	@Autowired
	private OxygenRequestDao reqDao;

	public OxygenRequestDao getReqDao() {
		return reqDao;
	}

	public void setReqDao(OxygenRequestDao reqDao) {
		this.reqDao = reqDao;
	}
	
	
	public OxygenRequestPojo generateRequest(OxygenRequestPojo req) {
		
		return reqDao.generateRequest(req);
		
	}

}
