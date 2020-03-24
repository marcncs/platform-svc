package com.winsafe.drp.dao;

public class VocationOrderDetailForm {
	/** identifier field */
    private Long id;

    /** nullable persistent field */
    private String soid;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;
    
    private Integer salesort;
    
    private String salesortname;

    /** nullable persistent field */
    private Long warehouseid;

    private String warehouseidname;
    
    /** nullable persistent field */
    private Long unitid;
    
    private String unitidname;

    /** nullable persistent field */
    private String batch;
    
    private Double orgunitprice;

    /** nullable persistent field */
    private Double unitprice;
    
    private Double taxunitprice;

    /** nullable persistent field */
    private Double quantity;

    /** nullable persistent field */
    private Double takequantity;

    /** nullable persistent field */
    private Double discount;

    /** nullable persistent field */
    private Double taxrate;

    /** nullable persistent field */
    private Double cost;

    /** nullable persistent field */
    private Double subsum;
    
    private Integer rows;

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
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

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getOrgunitprice() {
		return orgunitprice;
	}

	public void setOrgunitprice(Double orgunitprice) {
		this.orgunitprice = orgunitprice;
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

	public Integer getSalesort() {
		return salesort;
	}

	public void setSalesort(Integer salesort) {
		this.salesort = salesort;
	}

	public String getSalesortname() {
		return salesortname;
	}

	public void setSalesortname(String salesortname) {
		this.salesortname = salesortname;
	}

	public String getSoid() {
		return soid;
	}

	public void setSoid(String soid) {
		this.soid = soid;
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

	public Double getTaxrate() {
		return taxrate;
	}

	public void setTaxrate(Double taxrate) {
		this.taxrate = taxrate;
	}

	public Double getTaxunitprice() {
		return taxunitprice;
	}

	public void setTaxunitprice(Double taxunitprice) {
		this.taxunitprice = taxunitprice;
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
