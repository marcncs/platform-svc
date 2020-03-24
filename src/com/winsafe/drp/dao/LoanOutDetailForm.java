package com.winsafe.drp.dao;

public class LoanOutDetailForm {
	/** identifier field */
    private Long id;

    /** nullable persistent field */
    private String loid;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;

    /** nullable persistent field */
    private Long warehouseid;
    
    private String warehouseidname;

    /** nullable persistent field */
    private Long unitid;
    
    private String unitidname;

    /** nullable persistent field */
    private String batch;

    /** nullable persistent field */
    private Double unitprice;

    /** nullable persistent field */
    private Double quantity;

    /** nullable persistent field */
    private Double takequantity;

    /** nullable persistent field */
    private Double backquantity;

    /** nullable persistent field */
    private Double cost;

    /** nullable persistent field */
    private Double subsum;

	public Double getBackquantity() {
		return backquantity;
	}

	public void setBackquantity(Double backquantity) {
		this.backquantity = backquantity;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoid() {
		return loid;
	}

	public void setLoid(String loid) {
		this.loid = loid;
	}

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

	public Double getSubsum() {
		return subsum;
	}

	public void setSubsum(Double subsum) {
		this.subsum = subsum;
	}

	public Double getTakequantity() {
		return takequantity;
	}

	public void setTakequantity(Double takequantity) {
		this.takequantity = takequantity;
	}

	public Long getUnitid() {
		return unitid;
	}

	public void setUnitid(Long unitid) {
		this.unitid = unitid;
	}

	public String getUnitidname() {
		return unitidname;
	}

	public void setUnitidname(String unitidname) {
		this.unitidname = unitidname;
	}

	public Double getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(Double unitprice) {
		this.unitprice = unitprice;
	}

	public Long getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(Long warehouseid) {
		this.warehouseid = warehouseid;
	}

	public String getWarehouseidname() {
		return warehouseidname;
	}

	public void setWarehouseidname(String warehouseidname) {
		this.warehouseidname = warehouseidname;
	}
}
