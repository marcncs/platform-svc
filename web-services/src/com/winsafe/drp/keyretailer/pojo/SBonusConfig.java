package com.winsafe.drp.keyretailer.pojo;

import java.util.Date;

/**
 * SBonusSetting entity. @author MyEclipse Persistence Tools
 */
public class SBonusConfig {

	// Fields
	private Integer id;
	private Integer year;
	private Date startDate;
	private Date endDate;
	private Date makeDate;
	private Integer isCounted;
	//1|年度积分周期,2|年度评价周期,3|年度达标系数,4|积分目标设置周期
	private Integer configType;
	private String value;
	private int version;
	
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
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}
	public Integer getIsCounted() {
		return isCounted;
	}
	public void setIsCounted(Integer isCounted) {
		this.isCounted = isCounted;
	}
	public Integer getConfigType() {
		return configType;
	}
	public void setConfigType(Integer configType) {
		this.configType = configType;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}

}
