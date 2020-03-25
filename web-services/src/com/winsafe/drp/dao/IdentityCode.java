package com.winsafe.drp.dao;

import java.util.Date;

public class IdentityCode {
	
	public static final long PWRIOD_OF_VALIDITY = 600000l;
	public static final long INTERVAL = 60000l;
	private String identifyCode; 
	private String mobile;
	private Date creationTime;
	
	public IdentityCode(String identifyCode, String mobile, Date creationTime) {
		super();
		this.identifyCode = identifyCode;
		this.mobile = mobile;
		this.creationTime = creationTime;
	}
	public String getIdentifyCode() {
		return identifyCode;
	}
	public void setIdentifyCode(String identifyCode) {
		this.identifyCode = identifyCode;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public boolean isExpired(Date date) {
		return (date.getTime() - creationTime.getTime()) > PWRIOD_OF_VALIDITY;
	}
	public boolean isInRange(Date date) {
		return (date.getTime() - creationTime.getTime()) < INTERVAL;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
