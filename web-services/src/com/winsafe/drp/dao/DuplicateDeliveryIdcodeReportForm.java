package com.winsafe.drp.dao;

import java.io.Serializable;

public class DuplicateDeliveryIdcodeReportForm implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String idcode;
	private String productId;
	private String batch;
	private String billNo;
	private String ncCode;
	private String outOid;
	private String outWid;
	private String outDate;
	private String inOid;
	private String InWid;
	private String receiveDate;
	private String mCode;
	private String specMode;
	private String productName;
	private String outOName;
	private String inOName;
	private String outWName;
	private String inWName;
	private String beginDate;
	private String endDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdcode() {
		return idcode;
	}
	public void setIdcode(String idcode) {
		this.idcode = idcode;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getNcCode() {
		return ncCode;
	}
	public void setNcCode(String ncCode) {
		this.ncCode = ncCode;
	}
	public String getOutOid() {
		return outOid;
	}
	public void setOutOid(String outOid) {
		this.outOid = outOid;
	}
	public String getOutWid() {
		return outWid;
	}
	public void setOutWid(String outWid) {
		this.outWid = outWid;
	}
	public String getOutDate() {
		return outDate;
	}
	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}
	public String getInOid() {
		return inOid;
	}
	public void setInOid(String inOid) {
		this.inOid = inOid;
	}
	public String getInWid() {
		return InWid;
	}
	public void setInWid(String inWid) {
		InWid = inWid;
	}
	public String getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}
	public String getmCode() {
		return mCode;
	}
	public void setmCode(String mCode) {
		this.mCode = mCode;
	}
	public String getSpecMode() {
		return specMode;
	}
	public void setSpecMode(String specMode) {
		this.specMode = specMode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getOutOName() {
		return outOName;
	}
	public void setOutOName(String outOName) {
		this.outOName = outOName;
	}
	public String getInOName() {
		return inOName;
	}
	public void setInOName(String inOName) {
		this.inOName = inOName;
	}
	public String getOutWName() {
		return outWName;
	}
	public void setOutWName(String outWName) {
		this.outWName = outWName;
	}
	public String getInWName() {
		return inWName;
	}
	public void setInWName(String inWName) {
		this.inWName = inWName;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
