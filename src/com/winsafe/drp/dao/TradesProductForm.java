package com.winsafe.drp.dao;

public class TradesProductForm {
	private String productid;
	private String productname;
	private String specmode;
	private Integer unitid;
	private String unitidname;
	private String warehouseid;
	private String warehouseidname;
	private Double quantity;
	private Double stockpile;
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
	public String getSpecmode() {
		return specmode;
	}
	public void setSpecmode(String specmode) {
		this.specmode = specmode;
	}
	public Integer getUnitid() {
		return unitid;
	}
	public void setUnitid(Integer unitid) {
		this.unitid = unitid;
	}
	public String getUnitidname() {
		return unitidname;
	}
	public void setUnitidname(String unitidname) {
		this.unitidname = unitidname;
	}
	public String getWarehouseid() {
		return warehouseid;
	}
	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}
	public String getWarehouseidname() {
		return warehouseidname;
	}
	public void setWarehouseidname(String warehouseidname) {
		this.warehouseidname = warehouseidname;
	}
	public Double getStockpile() {
		return stockpile;
	}
	public void setStockpile(Double stockpile) {
		this.stockpile = stockpile;
	}
	
	
	
}
