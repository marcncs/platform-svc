package com.winsafe.sap.pojo;

import java.util.Date;

public class CovertErrorLog {
	
	private Long id;
	private String tdCode;
	private String covertCode;
	private Integer errorType;
	private Long uploadPrId;
	private Date uploadDate;
	private Integer uploadUser;
	private String productionLine;
	private Integer covertUploadId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTdCode() {
		return tdCode;
	}
	public void setTdCode(String tdCode) {
		this.tdCode = tdCode;
	}
	public String getCovertCode() {
		return covertCode;
	}
	public void setCovertCode(String covertCode) {
		this.covertCode = covertCode;
	}
	public Integer getErrorType() {
		return errorType;
	}
	public void setErrorType(Integer errorType) {
		this.errorType = errorType;
	}
	public Long getUploadPrId() {
		return uploadPrId;
	}
	public void setUploadPrId(Long uploadPrId) {
		this.uploadPrId = uploadPrId;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public Integer getUploadUser() {
		return uploadUser;
	}
	public void setUploadUser(Integer uploadUser) {
		this.uploadUser = uploadUser;
	}
	public String getProductionLine() {
		return productionLine;
	}
	public void setProductionLine(String productionLine) {
		this.productionLine = productionLine;
	}
	public Integer getCovertUploadId() {
		return covertUploadId;
	}
	public void setCovertUploadId(Integer covertUploadId) {
		this.covertUploadId = covertUploadId;
	}
	
}
