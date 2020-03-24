package com.winsafe.drp.dao;

public class SaleAreasObject {

	private Long productid;
	
	private String productname;
	
	private Long unitid;
	
	private Double quantity;

	public Long getProductid() {
		return productid;
	}

	public void setProductid(Long productid) {
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

	public Long getUnitid() {
		return unitid;
	}

	public void setUnitid(Long unitid) {
		this.unitid = unitid;
	}
}
