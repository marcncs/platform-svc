package com.winsafe.drp.keyretailer.pojo;

import java.util.Date;

/**
 * SBonusAccount entity. @author MyEclipse Persistence Tools
 */

public class SBonusLog implements java.io.Serializable {
	// Fields
	private Long id;
	private String billNo;
	private String logMsg;
	private Integer bonusType;
	private Integer isSent;
	private Date makeDate;
	private String organId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getLogMsg() {
		return logMsg;
	}
	public void setLogMsg(String logMsg) {
		this.logMsg = logMsg;
	}
	public Integer getBonusType() {
		return bonusType;
	}
	public void setBonusType(Integer bonusType) {
		this.bonusType = bonusType;
	}
	public Date getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}
	public Integer getIsSent() {
		return isSent;
	}
	public void setIsSent(Integer isSent) {
		this.isSent = isSent;
	}
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
}