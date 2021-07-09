package com.oxygen.managment.oxyavailibility.pojo;

import java.util.Date;

public class OxygenRequestPojo {
	
	@Override
	public String toString() {
		return "OxygenRequestPojo [cylinderRequestNo=" + cylinderRequestNo + ", requestDate=" + requestDate + "]";
	}
	private int cylinderRequestNo;
	private Date requestDate;
	private String requester;
	private int userid;
	private boolean isDataInserted;
	private String errorMsg;
	
	public boolean isDataInserted() {
		return isDataInserted;
	}
	public void setDataInserted(boolean isDataInserted) {
		this.isDataInserted = isDataInserted;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getRequester() {
		return requester;
	}
	public void setRequester(String requester) {
		this.requester = requester;
	}
	public int getCylinderRequestNo() {
		return cylinderRequestNo;
	}
	public void setCylinderRequestNo(int cylinderRequestNo) {
		this.cylinderRequestNo = cylinderRequestNo;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	
	
	

}
