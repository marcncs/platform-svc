package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

public class RecAndDisSumReportForm implements Serializable{
	/** */
	private static final long serialVersionUID = 1L;
	
	// 机构代码
	private String outOrganId;
	// 机构名称
	private String outOrganName;
	// 机构代码
	private String inOrganId;
	// 机构名称
	private String inOrganName;
	// 产品名称
	private String productName;
	// 规格
	private String spec;
	
	// 大区
	private String bigRegion;
	private String bigRegionId;
	// 地区
	private String outMiddleRegion;
	private String outMiddleRegionId;
	// 地区
	private String inMiddleRegion;
	private String inMiddleRegionId;
	// 小区
	private String smallRegion;
	private String smallRegionId;
	//县
	private String areas;
	private String areasId;
	
	//目标积分
	private String bonusPoint;
	
	//YTD积分
	private String amount;
	
	private String reportType;
	private String type;
	private String beginDate;
	private String endDate;
	
	// 单位
	private Integer unitId;
	private String unitName;
	
	private String bsort;
	private Date makeDate;
	
	private String areaId;
	private String regionId;
	
	public String getOutOrganId() {
		return outOrganId;
	}
	public void setOutOrganId(String outOrganId) {
		this.outOrganId = outOrganId;
	}
	public String getOutOrganName() {
		return outOrganName;
	}
	public void setOutOrganName(String outOrganName) {
		this.outOrganName = outOrganName;
	}
	public String getInOrganId() {
		return inOrganId;
	}
	public void setInOrganId(String inOrganId) {
		this.inOrganId = inOrganId;
	}
	public String getInOrganName() {
		return inOrganName;
	}
	public void setInOrganName(String inOrganName) {
		this.inOrganName = inOrganName;
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
	public String getBigRegion() {
		return bigRegion;
	}
	public void setBigRegion(String bigRegion) {
		this.bigRegion = bigRegion;
	}
	public String getBigRegionId() {
		return bigRegionId;
	}
	public void setBigRegionId(String bigRegionId) {
		this.bigRegionId = bigRegionId;
	}
	public String getOutMiddleRegion() {
		return outMiddleRegion;
	}
	public void setOutMiddleRegion(String outMiddleRegion) {
		this.outMiddleRegion = outMiddleRegion;
	}
	public String getOutMiddleRegionId() {
		return outMiddleRegionId;
	}
	public void setOutMiddleRegionId(String outMiddleRegionId) {
		this.outMiddleRegionId = outMiddleRegionId;
	}
	public String getInMiddleRegion() {
		return inMiddleRegion;
	}
	public void setInMiddleRegion(String inMiddleRegion) {
		this.inMiddleRegion = inMiddleRegion;
	}
	public String getInMiddleRegionId() {
		return inMiddleRegionId;
	}
	public void setInMiddleRegionId(String inMiddleRegionId) {
		this.inMiddleRegionId = inMiddleRegionId;
	}
	public String getSmallRegion() {
		return smallRegion;
	}
	public void setSmallRegion(String smallRegion) {
		this.smallRegion = smallRegion;
	}
	public String getSmallRegionId() {
		return smallRegionId;
	}
	public void setSmallRegionId(String smallRegionId) {
		this.smallRegionId = smallRegionId;
	}
	public String getAreas() {
		return areas;
	}
	public void setAreas(String areas) {
		this.areas = areas;
	}
	public String getAreasId() {
		return areasId;
	}
	public void setAreasId(String areasId) {
		this.areasId = areasId;
	}
	public String getBonusPoint() {
		return bonusPoint;
	}
	public void setBonusPoint(String bonusPoint) {
		this.bonusPoint = bonusPoint;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
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
	public Integer getUnitId() {
		return unitId;
	}
	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBsort() {
		return bsort;
	}
	public void setBsort(String bsort) {
		this.bsort = bsort;
	}
	public Date getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
}
