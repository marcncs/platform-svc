package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

public class ProductVlidateReportForm implements Serializable{

	/** */
	private static final long serialVersionUID = 1L;
	
	// 大区
	private String region;
	private String regionName;
	// 产品
	private String productId;
	private String productName;
	private String packSizeName;
	
	public String getPackSizeName() {
		return packSizeName;
	}
	public void setPackSizeName(String packSizeName) {
		this.packSizeName = packSizeName;
	}
	private String beginDate;
	private String endDate;
	// 查询码
	private String idcode;
	// 查询时间
	private Date queryDate;
	// 查询电话/IP
	private String queryAddr;
	// 查询次数
	private Integer queryCount;
	// 是否真伪
	private Integer chkTrue;
	private String chkResult;
	// 查询类型
	private String findTypeId;
	private String findTypeStr;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
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
	public String getIdcode() {
		return idcode;
	}
	public void setIdcode(String idcode) {
		this.idcode = idcode;
	}
	public Date getQueryDate() {
		return queryDate;
	}
	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}
	public String getQueryAddr() {
		return queryAddr;
	}
	public void setQueryAddr(String queryAddr) {
		this.queryAddr = queryAddr;
	}
	public Integer getQueryCount() {
		return queryCount;
	}
	public void setQueryCount(Integer queryCount) {
		this.queryCount = queryCount;
	}
	public Integer getChkTrue() {
		return chkTrue;
	}
	public void setChkTrue(Integer chkTrue) {
		this.chkTrue = chkTrue;
	}
	public String getFindTypeId() {
		return findTypeId;
	}
	public void setFindTypeId(String findTypeId) {
		this.findTypeId = findTypeId;
	}
	public String getFindTypeStr() {
		return findTypeStr;
	}
	public void setFindTypeStr(String findTypeStr) {
		this.findTypeStr = findTypeStr;
	}
	public String getChkResult() {
		return chkResult;
	}
	public void setChkResult(String chkResult) {
		this.chkResult = chkResult;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	
	
}
