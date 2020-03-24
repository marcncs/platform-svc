package com.winsafe.drp.dao;

import java.util.Date;

public class ProductIncomeDetailForm {
	/** identifier field */
    private Integer id;

    /** persistent field */
    private String piid;

    /** persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;

    /** persistent field */
    private Integer unitid;
    
    private String unitidname;

    /** nullable persistent field */
    private Double quantity;
    
    private Double factquantity;
    
    private Double costprice;
    
    private Double costsum;
    
    private Date makedate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPiid() {
		return piid;
	}

	public void setPiid(String piid) {
		this.piid = piid;
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

	public Double getCostprice() {
		return costprice;
	}

	public void setCostprice(Double costprice) {
		this.costprice = costprice;
	}

	public Double getCostsum() {
		return costsum;
	}

	public void setCostsum(Double costsum) {
		this.costsum = costsum;
	}

	public Double getFactquantity() {
		return factquantity;
	}

	public void setFactquantity(Double factquantity) {
		this.factquantity = factquantity;
	}

	public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}
}
