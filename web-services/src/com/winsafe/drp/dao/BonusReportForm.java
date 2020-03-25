package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

public class BonusReportForm implements Serializable{
	/** */
	private static final long serialVersionUID = 1L;
	
	//年度
	private String year;
	// 机构代码
	private String organId;
	// 机构名称
	private String organName;
	// 产品名称
	private String productName;
	// 规格
	private String spec;
	
	// 大区
	private String bigRegion;
	private String bigRegionId;
	// 地区
	private String middleRegion;
	private String middleRegionId;
	// 小区
	private String smallRegion;
	private String smallRegionId;
	//县
	private String areas;
	private String areaId;
	
	//目标积分
	private String targetPoint;
	
	//YTD积分
	private String curPoint;
	// 积分完成进度（YTD积分/目标积分）
	private String completeRate;
	// 是否达标
	private String isReached;
	
	
	
	// 支持度评价
	private String isSupported;
	// 最终积分
	private String finalPoint;
	// 返利确认时间
	private Date confirmDate;
	private String type;
	
	private String regionId;
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
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
	public String getMiddleRegion() {
		return middleRegion;
	}
	public void setMiddleRegion(String middleRegion) {
		this.middleRegion = middleRegion;
	}
	public String getMiddleRegionId() {
		return middleRegionId;
	}
	public void setMiddleRegionId(String middleRegionId) {
		this.middleRegionId = middleRegionId;
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
	public String getTargetPoint() {
		return targetPoint;
	}
	public void setTargetPoint(String targetPoint) {
		this.targetPoint = targetPoint;
	}
	public String getCurPoint() {
		return curPoint;
	}
	public void setCurPoint(String curPoint) {
		this.curPoint = curPoint;
	}
	public String getCompleteRate() {
		return completeRate;
	}
	public void setCompleteRate(String completeRate) {
		this.completeRate = completeRate;
	}
	public String getIsReached() {
		return isReached;
	}
	public void setIsReached(String isReached) {
		this.isReached = isReached;
	}
	public String getIsSupported() {
		return isSupported;
	}
	public void setIsSupported(String isSupported) {
		this.isSupported = isSupported;
	}
	public String getFinalPoint() {
		return finalPoint;
	}
	public void setFinalPoint(String finalPoint) {
		this.finalPoint = finalPoint;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
