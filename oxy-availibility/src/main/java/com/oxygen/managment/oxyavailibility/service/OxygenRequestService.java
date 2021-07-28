package com.oxygen.managment.oxyavailibility.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.mail.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oxygen.managment.oxyavailibility.dao.LoginDao;
import com.oxygen.managment.oxyavailibility.dao.OxygenRequestDao;
import com.oxygen.managment.oxyavailibility.pojo.CustomUser;
import com.oxygen.managment.oxyavailibility.pojo.OxygenRequestPojo;
import com.oxygen.managment.oxyavailibility.utility.ApplicationConstant;
import com.oxygen.managment.oxyavailibility.utility.MailUtility;

@Service
public class OxygenRequestService {
	
	@Autowired
	private OxygenRequestDao reqDao;
	@Autowired
	private LoginDao loginDao;
	
	public OxygenRequestDao getReqDao() {
		return reqDao;
	}

	public void setReqDao(OxygenRequestDao reqDao) {
		this.reqDao = reqDao;
	}
	
	
	public OxygenRequestPojo generateRequest(OxygenRequestPojo req) {
		
		return reqDao.generateRequest(req);
		
	}
	
	public int deleteRequest(OxygenRequestPojo req) {
		
		return reqDao.deleteRequest(req);
	}
	
	public List<OxygenRequestPojo> activeOxygenRequest(){
		return reqDao.getActiveRequestList();
	}
	
	public OxygenRequestPojo updateOxygenRequest(OxygenRequestPojo req){
		return reqDao.updateRequest(req);
	}

	public OxygenRequestPojo updateRequestStatus(OxygenRequestPojo req) {
		
		int reqCylinderNo = req.getCylinderRequestNo();
		
		
		
		List<OxygenRequestPojo> totalReqList = reqDao.getActiveRequestList();
		List<OxygenRequestPojo> totalStockList =totalReqList.stream().
		filter(elem ->elem.getReqStatus()!=null 
			&& elem.getReqStatus().equalsIgnoreCase(ApplicationConstant.REQ_STATUS_STOCK_ADD)).collect(Collectors.toList());
		
		
		List<OxygenRequestPojo> totalApprovedList =totalReqList.stream().
				filter(elem ->elem.getReqStatus()!=null 
					&& elem.getReqStatus().equalsIgnoreCase(ApplicationConstant.REQ_STATUS_APPROVED)).collect(Collectors.toList());
		
		
		int totalStockCount = totalStockList.stream().mapToInt(elem->elem.getCylinderRequestNo()).sum();
		int totalApprovedCount = totalApprovedList.stream().mapToInt(elem->elem.getCylinderRequestNo()).sum();
		
		
		if(ApplicationConstant.REQ_STATUS_APPROVED.equalsIgnoreCase(req.getReqStatus()) 
				&&((totalStockCount - totalApprovedCount)>=reqCylinderNo)) {
			
			OxygenRequestPojo resposne = reqDao.updateRequestStats(req);
			
			CustomUser requestedUser = loginDao.findUserByReqId(req);
			
			
			Session session = MailUtility.configureMail();
			String subject = MailUtility.configureMailSubject(req);
			String body = MailUtility.configureMailBody(req,requestedUser);
			MailUtility.sendEmail(session, requestedUser.getUsername(), subject, body);
			return resposne;
		}else if(ApplicationConstant.REQ_STATUS_CANCEL.equalsIgnoreCase(req.getReqStatus())) {
			CustomUser requestedUser = loginDao.findUserByReqId(req);
			
			OxygenRequestPojo resposne  = reqDao.updateRequestStats(req);
			Session session = MailUtility.configureMail();
			String subject = MailUtility.configureMailSubject(req);
			String body = MailUtility.configureMailBody(req,requestedUser);
			MailUtility.sendEmail(session, requestedUser.getUsername(), subject, body);
			
			return resposne ;
		}
		else if(ApplicationConstant.REQ_STATUS_COMPLETE.equalsIgnoreCase(req.getReqStatus())) {
			OxygenRequestPojo resposne  = reqDao.updateRequestStats(req);
			CustomUser requestedUser = loginDao.findUserByReqId(req);
		
			Session session = MailUtility.configureMail();
			String subject = MailUtility.configureMailSubject(req);
			String body = MailUtility.configureMailBody(req,requestedUser);
			MailUtility.sendEmail(session, requestedUser.getUsername(), subject, body);

			return resposne ;
		}
		else {
			req.setErrorMsg("Request cant be Approved as stock is empty");
			req.setDataInserted(false);
			return req;
		}
		
		
	}

}
