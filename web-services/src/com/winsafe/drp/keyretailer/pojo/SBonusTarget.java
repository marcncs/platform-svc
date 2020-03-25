package com.winsafe.drp.keyretailer.pojo;

import java.util.Date;

/**
 * SBonusTarget entity. @author MyEclipse Persistence Tools
 */

public class SBonusTarget implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer accountId;
	private String fromOrganId;
	private String fromOrganName;
	private String toOrganId;
	private String toOrganName;
	private Integer year;
	private String productName;
	private String spec;
	private Integer countUnit;
	private String countUnitName;
	private Double targetAmount;
	private Date modifiedDate;
	private Integer version; 
	private Double bonusPoint;
	private Double finalBonusPoint;
	private Integer isSupported;
	private Double rebate;
	private Integer isConfirmed;
	private Integer isComplete;
	
	private Date supportDate;
	private Date confirmDate;
	private Date completeDate;

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

	public String getFromOrganId() {
		return fromOrganId;
	}

	public void setFromOrganId(String fromOrganId) {
		this.fromOrganId = fromOrganId;
	}

	public String getToOrganId() {
		return toOrganId;
	}

	public void setToOrganId(String toOrganId) {
		this.toOrganId = toOrganId;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public Integer getCountUnit() {
		return countUnit;
	}

	public void setCountUnit(Integer countUnit) {
		this.countUnit = countUnit;
	}

	public Double getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(Double targetAmount) {
		this.targetAmount = targetAmount;
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

	public String getFromOrganName() {
		return fromOrganName;
	}

	public void setFromOrganName(String fromOrganName) {
		this.fromOrganName = fromOrganName;
	}

	public String getToOrganName() {
		return toOrganName;
	}

	public void setToOrganName(String toOrganName) {
		this.toOrganName = toOrganName;
	}

	public String getCountUnitName() {
		return countUnitName;
	}

	public void setCountUnitName(String countUnitName) {
		this.countUnitName = countUnitName;
	}

	public Double getBonusPoint() {
		return bonusPoint;
	}

	public void setBonusPoint(Double bonusPoint) {
		this.bonusPoint = bonusPoint;
	}

	public Double getFinalBonusPoint() {
		return finalBonusPoint;
	}

	public void setFinalBonusPoint(Double finalBonusPoint) {
		this.finalBonusPoint = finalBonusPoint;
	}

	public Integer getIsSupported() {
		return isSupported;
	}

	public void setIsSupported(Integer isSupported) {
		this.isSupported = isSupported;
	}

	public Double getRebate() {
		return rebate;
	}

	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}


	public Integer getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(Integer isComplete) {
		this.isComplete = isComplete;
	}

	public Integer getIsConfirmed() {
		return isConfirmed;
	}

	public void setIsConfirmed(Integer isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	public Date getSupportDate() {
		return supportDate;
	}

	public void setSupportDate(Date supportDate) {
		this.supportDate = supportDate;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

}