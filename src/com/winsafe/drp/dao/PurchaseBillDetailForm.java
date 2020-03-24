package com.winsafe.drp.dao;

import java.util.Date;

public class PurchaseBillDetailForm {
	/** identifier field */
    private Integer id;

    /** persistent field */
    private String pbid;
    
    private String makedate;

    /** persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;

    /** persistent field */
    private Integer unitid;
    
    private String unitname;
    
    private Date requiredate;

    /** nullable persistent field */
    private Double unitprice;

    /** nullable persistent field */
    private Double quantity;
    
    private Double incomequantity;

    /** nullable persistent field */
    private Double subsum;
    
    private String pname;
    
    private String pid;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMakedate() {
		return makedate;
	}

	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}

	public String getPbid() {
		return pbid;
	}

	public void setPbid(String pbid) {
		this.pbid = pbid;
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

	public void setIncomequantity(Double incomequantity) {
		this.incomequantity = incomequantity;
	}

	public Double getIncomequantity() {
		return incomequantity;
	}

	/**
	 * @return the pname
	 */
	public String getPname() {
		return pname;
	}

	/**
	 * @param pname the pname to set
	 */
	public void setPname(String pname) {
		this.pname = pname;
	}

	public Date getRequiredate() {
		return requiredate;
	}

	public void setRequiredate(Date requiredate) {
		this.requiredate = requiredate;
	}
	
	
}
