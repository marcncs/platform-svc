package com.winsafe.drp.keyretailer.pojo;

import java.util.Date;

/**
 * SUserArea entity. @author MyEclipse Persistence Tools
 */

public class SalesAreaCountry implements java.io.Serializable {

	// Fields
	private Integer id;
	private Integer salesAreaId;
	private Integer countryAreaId;
	private Date makeDate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSalesAreaId() {
		return salesAreaId;
	}
	public void setSalesAreaId(Integer salesAreaId) {
		this.salesAreaId = salesAreaId;
	}
	public Integer getCountryAreaId() {
		return countryAreaId;
	}
	public void setCountryAreaId(Integer countryAreaId) {
		this.countryAreaId = countryAreaId;
	}
	public Date getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}
}