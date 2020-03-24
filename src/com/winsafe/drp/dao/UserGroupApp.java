package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date; 

public class UserGroupApp implements Serializable {

    private Integer id; 

    private Integer userGroupId;

    private Date makeDate;
    
    private Integer appId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(Integer userGroupId) {
		this.userGroupId = userGroupId;
	}

	public Date getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}
    
    
}
