package com.winsafe.drp.keyretailer.pojo;

import java.util.Date;

/**
 * SBonusSetting entity. @author MyEclipse Persistence Tools
 */
public class SBonusSetting {

	// Fields
	private Integer id;
	private Integer year;
	private Integer month;
	private String productName;
	private String spec;
	private Double bonusPoint;
	private Integer countUnit;
	private Double amount;
	private Integer accountType;
	private Integer activeFlag;
	private Date modifiedDate;
	private Integer version;
	private String countUnitName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
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
	public Double getBonusPoint() {
		return bonusPoint;
	}
	public void setBonusPoint(Double bonusPoint) {
		this.bonusPoint = bonusPoint;
	}
	public Integer getCountUnit() {
		return countUnit;
	}
	public void setCountUnit(Integer countUnit) {
		this.countUnit = countUnit;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public Integer getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(Integer activeFlag) {
		this.activeFlag = activeFlag;
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
	public String getCountUnitName() {
		return countUnitName;
	}
	public void setCountUnitName(String countUnitName) {
		this.countUnitName = countUnitName;
	}


}
