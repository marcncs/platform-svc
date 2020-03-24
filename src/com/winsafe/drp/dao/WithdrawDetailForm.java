package com.winsafe.drp.dao;

public class WithdrawDetailForm {
	 /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String wid;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;

    /** nullable persistent field */
    private Integer unitid;
    
    private String unitidname;
   
    /** nullable persistent field */
    private String batch;

    /** nullable persistent field */
    private Double unitprice;
    
    private Double taxunitprice;

    /** nullable persistent field */
    private Double quantity;

    /** nullable persistent field */
    private Double subsum;

    /** nullable persistent field */
    private Double discount;

    /** nullable persistent field */
    private Double taxrate;
    
    private Integer rows;
    
    private String cname;

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

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Double getTaxrate() {
		return taxrate;
	}

	public void setTaxrate(Double taxrate) {
		this.taxrate = taxrate;
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

	public Double getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(Double unitprice) {
		this.unitprice = unitprice;
	}

	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public Double getTaxunitprice() {
		return taxunitprice;
	}

	public void setTaxunitprice(Double taxunitprice) {
		this.taxunitprice = taxunitprice;
	}

	/**
	 * @return the cname
	 */
	public String getCname() {
		return cname;
	}

	/**
	 * @param cname the cname to set
	 */
	public void setCname(String cname) {
		this.cname = cname;
	}
	
	
    
    
}
