package com.winsafe.drp.dao;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.upload.FormFile;

/** @author Hibernate CodeGenerator */
public class ProductPriceHistoryForm {

	// Fields

	private Long id;
	private String productId;
	private Integer unitId;
	private Double unitPrice;
	private Date startTime;
	private Date endTime;
	private Long makeUserId;
	private Date makeDate;
	private Integer makeOrganId;
	private String memo;
	private String productName;
	private String unitName;

	// Property accessors

	
	
	 private FormFile usrsfile;
	 
	 
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getUnitId() {
		return this.unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public Double getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getMakeUserId() {
		return this.makeUserId;
	}

	public void setMakeUserId(Long makeUserId) {
		this.makeUserId = makeUserId;
	}

	public Date getMakeDate() {
		return this.makeDate;
	}

	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}

	public Integer getMakeOrganId() {
		return this.makeOrganId;
	}

	public void setMakeOrganId(Integer makeOrganId) {
		this.makeOrganId = makeOrganId;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public FormFile getUsrsfile() {
		return usrsfile;
	}

	public void setUsrsfile(FormFile usrsfile) {
		this.usrsfile = usrsfile;
	}
	

}
