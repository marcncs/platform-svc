package com.winsafe.drp.keyretailer.pojo;

import java.util.Date;

/**
 * SBonusAppraise entity. @author MyEclipse Persistence Tools
 */

public class SBonusAppraise implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer accountId;
	private String organId;
	private Double bonusTarget;
	private Double bonusPoint;
	private Double appraise;
	private Double actualPoint;
	private Integer period;
	private Date modifiedDate;
	private Integer version;
	private String backprofit;
	private Integer isSupported;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
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
	public Double getBonusTarget() {
		return bonusTarget;
	}
	public void setBonusTarget(Double bonusTarget) {
		this.bonusTarget = bonusTarget;
	}
	public Double getBonusPoint() {
		return bonusPoint;
	}
	public void setBonusPoint(Double bonusPoint) {
		this.bonusPoint = bonusPoint;
	}
	public Double getAppraise() {
		return appraise;
	}
	public void setAppraise(Double appraise) {
		this.appraise = appraise;
	}
	public Double getActualPoint() {
		return actualPoint;
	}
	public void setActualPoint(Double actualPoint) {
		this.actualPoint = actualPoint;
	}
	public String getBackprofit() {
		return backprofit;
	}
	public void setBackprofit(String backprofit) {
		this.backprofit = backprofit;
	}
	public Integer getIsSupported() {
		return isSupported;
	}
	public void setIsSupported(Integer isSupported) {
		this.isSupported = isSupported;
	}
}