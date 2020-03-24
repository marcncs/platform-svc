package com.winsafe.drp.keyretailer.pojo;

import java.util.Date;

/**
 * SUserArea entity. @author MyEclipse Persistence Tools
 */

public class UserCustomer implements java.io.Serializable {

	// Fields
	private Integer id;
	private Integer userId;
	private String organId;
	private Date makeDate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public Date getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}
}