package com.winsafe.drp.dao;

public class StockCheckDetailForm {

	private String productname;

	private String unitidname;

	private String batch;

	private String warehousebit;
	
	private Double reckonquantity;

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getUnitidname() {
		return unitidname;
	}

	public void setUnitidname(String unitidname) {
		this.unitidname = unitidname;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getWarehousebit() {
		return warehousebit;
	}

	public void setWarehousebit(String warehousebit) {
		this.warehousebit = warehousebit;
	}

	public Double getReckonquantity() {
		return reckonquantity;
	}

	public void setReckonquantity(Double reckonquantity) {
		this.reckonquantity = reckonquantity;
	}
	
	

}