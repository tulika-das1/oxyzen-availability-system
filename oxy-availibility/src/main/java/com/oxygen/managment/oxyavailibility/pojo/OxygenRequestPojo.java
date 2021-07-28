package com.oxygen.managment.oxyavailibility.pojo;

import java.util.Date;

public class OxygenRequestPojo {
	
	@Override
	public String toString() {
		return "OxygenRequestPojo [cylinderRequestNo=" + cylinderRequestNo + ", requestDate=" + requestDate + "]";
	}
	private int requestId;
	private int cylinderRequestNo;
	private Date requestDate;
	private String requester;
	private int userid;
	private boolean isDataInserted;
	private String errorMsg;
	private String reqStatus;
	private boolean stockIncreaseRequest;
	private String stockIncreaseComment;
	private String approvalRejectionComment;
	
	
	
	public String getApprovalRejectionComment() {
		return approvalRejectionComment;
	}
	public void setApprovalRejectionComment(String approvalRejectionComment) {
		this.approvalRejectionComment = approvalRejectionComment;
	}
	public boolean isStockIncreaseRequest() {
		return stockIncreaseRequest;
	}
	public void setStockIncreaseRequest(boolean stockIncreaseRequest) {
		this.stockIncreaseRequest = stockIncreaseRequest;
	}
	public String getStockIncreaseComment() {
		return stockIncreaseComment;
	}
	public void setStockIncreaseComment(String stockIncreaseComment) {
		this.stockIncreaseComment = stockIncreaseComment;
	}
	public String getReqStatus() {
		return reqStatus;
	}
	public void setReqStatus(String reqStatus) {
		this.reqStatus = reqStatus;
	}
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
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
