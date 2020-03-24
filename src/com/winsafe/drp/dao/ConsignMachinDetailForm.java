package com.winsafe.drp.dao;

public class ConsignMachinDetailForm {
	/** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String cmid;

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
    private Double quantity;
    
    private Double advicequantity;

    /** nullable persistent field */
    private Double alreadyquantity;
    
    private Double stockpile;

    /** nullable persistent field */
    private Double unitprice;

    /** nullable persistent field */
    private Double subsum;

	public Double getAlreadyquantity() {
		return alreadyquantity;
	}

	public void setAlreadyquantity(Double alreadyquantity) {
		this.alreadyquantity = alreadyquantity;
	}

	public String getCmid() {
		return cmid;
	}

	public void setCmid(String cmid) {
		this.cmid = cmid;
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

	public Double getStockpile() {
		return stockpile;
	}

	public void setStockpile(Double stockpile) {
		this.stockpile = stockpile;
	}

	public Double getAdvicequantity() {
		return advicequantity;
	}

	public void setAdvicequantity(Double advicequantity) {
		this.advicequantity = advicequantity;
	}

}
