package com.winsafe.sms.pojo;

import java.util.Date;

public class Sms {
	
	private Long id;
	private String mobileNo;
	private String content;
	private Date sendTime;
	private Integer sendStatus;
	private Date createDate;
	private Integer tryCount;
	private Integer returnVal;
	private Integer createId;
	private Integer type;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public Integer getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getTryCount() {
		return tryCount;
	}
	public void setTryCount(Integer tryCount) {
		this.tryCount = tryCount;
	}
	public Integer getReturnVal() {
		return returnVal;
	}
	public void setReturnVal(Integer returnVal) {
		this.returnVal = returnVal;
	}
	public Integer getCreateId() {
		return createId;
	}
	public void setCreateId(Integer createId) {
		this.createId = createId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}
