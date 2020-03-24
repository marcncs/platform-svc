package com.winsafe.drp.keyretailer.pojo;

import java.util.Date;

/**
 * SBonusDetail entity. @author MyEclipse Persistence Tools
 */

public class SBonusDetail implements java.io.Serializable {

	// Fields

	private Long id;
	private String organId;
	private String oppOrganId;
	private Double bonusPoint;
	private Integer bonusType;
	private String mcode;
	private String productName;
	private String spec;
	private Double amount;
	private Integer year;
	private Integer month;
	private Integer uploadId;
	private Integer accountId;
	private Integer version;
	private Date makeDate;
	private String remark;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	
	public String getOppOrganId() {
		return oppOrganId;
	}
	public void setOppOrganId(String oppOrganId) {
		this.oppOrganId = oppOrganId;
	}
	public Double getBonusPoint() {
		return bonusPoint;
	}
	public void setBonusPoint(Double bonusPoint) {
		this.bonusPoint = bonusPoint;
	}
	public Integer getBonusType() {
		return bonusType;
	}
	public void setBonusType(Integer bonusType) {
		this.bonusType = bonusType;
	}
	public String getMcode() {
		return mcode;
	}
	public void setMcode(String mcode) {
		this.mcode = mcode;
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
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getUploadId() {
		return uploadId;
	}
	public void setUploadId(Integer uploadId) {
		this.uploadId = uploadId;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Date getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}

	// Constructors

}