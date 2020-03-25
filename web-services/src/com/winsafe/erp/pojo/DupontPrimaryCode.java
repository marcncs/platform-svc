package com.winsafe.erp.pojo;

import java.util.Date;

public class DupontPrimaryCode {
	
	private String primaryCode;
	private String dpCartoncode;
	private String bcsCartoncode;
	private String bcsTDCode;
	private String dpPalletCode;
	private String materialCode;
	private Date productionDate;
	private String dpBatch;
	private String bcsBatch;
	private Date makeDate;
	private Integer uploadId;
	private int lineNo;
	public String getPrimaryCode() {
		return primaryCode;
	}
	public void setPrimaryCode(String primaryCode) {
		this.primaryCode = primaryCode;
	}
	public String getDpCartoncode() {
		return dpCartoncode;
	}
	public void setDpCartoncode(String dpCartoncode) {
		this.dpCartoncode = dpCartoncode;
	}
	public String getBcsCartoncode() {
		return bcsCartoncode;
	}
	public void setBcsCartoncode(String bcsCartoncode) {
		this.bcsCartoncode = bcsCartoncode;
	}
	public String getBcsTDCode() {
		return bcsTDCode;
	}
	public void setBcsTDCode(String bcsTDCode) {
		this.bcsTDCode = bcsTDCode;
	}
	public String getDpPalletCode() {
		return dpPalletCode;
	}
	public void setDpPalletCode(String dpPalletCode) {
		this.dpPalletCode = dpPalletCode;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public Date getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}
	public String getDpBatch() {
		return dpBatch;
	}
	public void setDpBatch(String dpBatch) {
		this.dpBatch = dpBatch;
	}
	public String getBcsBatch() {
		return bcsBatch;
	}
	public void setBcsBatch(String bcsBatch) {
		this.bcsBatch = bcsBatch;
	}
	public Date getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}
	public Integer getUploadId() {
		return uploadId;
	}
	public void setUploadId(Integer uploadId) {
		this.uploadId = uploadId;
	}
	public int getLineNo() {
		return lineNo;
	}
	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}
	
}
