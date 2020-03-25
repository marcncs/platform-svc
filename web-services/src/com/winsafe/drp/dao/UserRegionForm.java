package com.winsafe.drp.dao;

import java.io.Serializable;

public class UserRegionForm implements Serializable {
	
	private Integer id;
	private String userid;
	private String username;
	private String userlogin;
	private Long   regionid;
	private String regionname;
	
	
	private  Integer IsChecked=0;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserlogin() {
		return userlogin;
	}
	public void setUserlogin(String userlogin) {
		this.userlogin = userlogin;
	}
	public Long getRegionid() {
		return regionid;
	}
	public void setRegionid(Long regionid) {
		this.regionid = regionid;
	}
	public String getRegionname() {
		return regionname;
	}
	public void setRegionname(String regionname) {
		this.regionname = regionname;
	}
	public Integer getIsChecked() {
		return IsChecked;
	}
	public void setIsChecked(Integer isChecked) {
		IsChecked = isChecked;
	}

}
