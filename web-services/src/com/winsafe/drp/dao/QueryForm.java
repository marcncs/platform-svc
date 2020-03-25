package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

public class QueryForm implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String proNumber;
	private Date findDt;
	private String telNumber;
	private int chkTrue;
	private String findType;
	private int queryNum;
	private String compFlag;
	private String profile;
	private String remark;
	private String areas;
	private String cartonCode;
	private String mPin;
	private String primaryCode;
	private String productName;
	private String specmode;
	private String city;
	
	public QueryForm(){
		
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
	public void setChkTrue(int chkTrue) {
		this.chkTrue = chkTrue;
	}
	public int getChkTrue() {
		return chkTrue;
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
	public void setQueryNum(int queryNum) {
		this.queryNum = queryNum;
	}
	public int getQueryNum() {
		return queryNum;
	}
	public String getCartonCode() {
		return cartonCode;
	}
	public void setCartonCode(String cartonCode) {
		this.cartonCode = cartonCode;
	}
	public String getmPin() {
		return mPin;
	}
	public void setmPin(String mPin) {
		this.mPin = mPin;
	}
	public String getPrimaryCode() {
		return primaryCode;
	}
	public void setPrimaryCode(String primaryCode) {
		this.primaryCode = primaryCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSpecmode() {
		return specmode;
	}
	public void setSpecmode(String specmode) {
		this.specmode = specmode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	
}