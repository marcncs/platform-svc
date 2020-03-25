package com.winsafe.drp.dao;

import org.apache.struts.validator.ValidatorForm;

public class ValidateWarehouse extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5574924184076798974L;
	private Integer id;
	private String warehousename;
	private Integer dept;
	private Integer userid;
	private String username;
	private String warehousetel;
	private Integer warehouseproperty;
	private String warehouseaddr;
	private Integer useflag;
	private String remark;
	private Integer province;
	private String shortname;

	private Integer city;

	private Integer areas;
	private Integer isautoreceive;

	public Integer getIsautoreceive() {
		return isautoreceive;
	}

	public void setIsautoreceive(Integer isautoreceive) {
		this.isautoreceive = isautoreceive;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setWarehousename(String warehousename) {
		this.warehousename = warehousename;
	}

	public void setWarehouseaddr(String warehouseaddr) {
		this.warehouseaddr = warehouseaddr;
	}

	public void setUseflag(Integer useflag) {
		this.useflag = useflag;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}

	public Integer getUseflag() {
		return useflag;
	}

	public String getWarehouseaddr() {
		return warehouseaddr;
	}

	public String getWarehousename() {
		return warehousename;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getWarehouseproperty() {
		return warehouseproperty;
	}

	public void setWarehouseproperty(Integer warehouseproperty) {
		this.warehouseproperty = warehouseproperty;
	}

	public String getWarehousetel() {
		return warehousetel;
	}

	public void setWarehousetel(String warehousetel) {
		this.warehousetel = warehousetel;
	}

	public Integer getDept() {
		return dept;
	}

	public void setDept(Integer dept) {
		this.dept = dept;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getAreas() {
		return areas;
	}

	public void setAreas(Integer areas) {
		this.areas = areas;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

}
