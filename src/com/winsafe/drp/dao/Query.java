package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

public class Query implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String proNumber;
	private Date findDt;
	private String telNumber;
	private Integer chkTrue;
	private String findType;
	private Integer queryNum;
	private String compFlag;
	private String profile;
	private String remark;
	private String areas;
	private Integer isFirstQuery;
	private String city;
	private String productid;
	
	public Query(){
		
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setProNumber(String proNumber) {
		this.proNumber = proNumber;
	}
	public String getProNumber() {
		return proNumber;
	}
	public void setFindDt(Date findDt) {
		this.findDt = findDt;
	}
	public Date getFindDt() {
		return findDt;
	}
	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}
	public String getTelNumber() {
		return telNumber;
	}
	public void setFindType(String findType) {
		this.findType = findType;
	}
	public String getFindType() {
		return findType;
	}
	public void setCompFlag(String compFlag) {
		this.compFlag = compFlag;
	}
	public String getCompFlag() {
		return compFlag;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getProfile() {
		return profile;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRemark() {
		return remark;
	}
	public void setAreas(String areas) {
		this.areas = areas;
	}
	public String getAreas() {
		return areas;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public Integer getChkTrue() {
		return chkTrue;
	}
	public void setChkTrue(Integer chkTrue) {
		this.chkTrue = chkTrue;
	}
	public Integer getQueryNum() {
		return queryNum;
	}
	public void setQueryNum(Integer queryNum) {
		this.queryNum = queryNum;
	}
	public Integer getIsFirstQuery() {
		return isFirstQuery;
	}
	public void setIsFirstQuery(Integer isFirstQuery) {
		this.isFirstQuery = isFirstQuery;
	}
	
}
