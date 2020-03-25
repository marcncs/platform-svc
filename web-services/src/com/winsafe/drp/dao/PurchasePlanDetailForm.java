package com.winsafe.drp.dao;



public class PurchasePlanDetailForm {
	/** identifier field */
    private Integer rows;

    /** nullable persistent field */
    private String ppid;

    /** persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;
    
    private String specmode;

    /** persistent field */
    private Integer unitid;
    
    private String unitname;

    /** nullable persistent field */
    private Double unitprice;

    /** nullable persistent field */
    private Double quantity;

    /** nullable persistent field */
    private Double changequantity;

    /** nullable persistent field */
    private String requiredate;

    /** nullable persistent field */
    private String advicedate;
    
    private Double subsum;


    public Double getSubsum() {
		return subsum;
	}

	public void setSubsum(Double subsum) {
		this.subsum = subsum;
	}

	/** nullable persistent field */
    private String requireexplain;

	public String getAdvicedate() {
		return advicedate;
	}

	public void setAdvicedate(String advicedate) {
		this.advicedate = advicedate;
	}

	public Double getChangequantity() {
		return changequantity;
	}

	public void setChangequantity(Double changequantity) {
		this.changequantity = changequantity;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getPpid() {
		return ppid;
	}

	public void setPpid(String ppid) {
		this.ppid = ppid;
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

	public String getRequiredate() {
		return requiredate;
	}

	public void setRequiredate(String requiredate) {
		this.requiredate = requiredate;
	}

	public String getRequireexplain() {
		return requireexplain;
	}

	public void setRequireexplain(String requireexplain) {
		this.requireexplain = requireexplain;
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

	public String getUnitname() {
		return unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	public Double getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(Double unitprice) {
		this.unitprice = unitprice;
	}

}
