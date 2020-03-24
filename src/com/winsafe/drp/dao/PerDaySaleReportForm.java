package com.winsafe.drp.dao;

public class PerDaySaleReportForm {
	private String productid;
	private String productname;
	private Integer unitid;
	private String unitname;
	private Double quantity;
	private Double subsum;
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}


	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public Double getSubsum() {
		return subsum;
	}
	public void setSubsum(Double subsum) {
		this.subsum = subsum;
	}
	public Integer getUnitid() {
		return unitid;
	}
	public void setUnitid(Integer unitid) {
		this.unitid = unitid;
	}
	public String getUnitname() {
		return unitname;
	}
	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}
}
