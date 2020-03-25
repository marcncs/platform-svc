package com.winsafe.drp.keyretailer.pojo;

import java.util.Date;

/**
 * SUserArea entity. @author MyEclipse Persistence Tools
 */

public class SUserArea implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userId;
	private Integer areaId;
	private String remark;
	private Date modifiedDate;
	private Integer version;
	private String loginName;
	private String realName;
	private String areaName;
	
	public SUserArea() {
		super();
	}
	public SUserArea(Integer id, Integer areaId, String loginName, String realName,
			String areaName) {
		super();
		this.id = id;
		this.areaId = areaId;
		this.loginName = loginName;
		this.realName = realName;
		this.areaName = areaName;
	}
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
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	// Constructors


}