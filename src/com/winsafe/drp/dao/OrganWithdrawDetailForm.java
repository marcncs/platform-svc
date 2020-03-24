package com.winsafe.drp.dao;
/**
 * @author : jerry
 * @version : 2009-9-15 下午05:04:38
 * www.winsafe.cn
 */
public class OrganWithdrawDetailForm {

	/** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String owid;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;

    /** nullable persistent field */
    private String unitidname;

    /** nullable persistent field */
    private String batch;

    /** nullable persistent field */
    private Double unitprice;

    /** nullable persistent field */
    private Double quantity;
    
    private Double ratifyquantity;

    /** nullable persistent field */
    private Double takequantity;

    /** nullable persistent field */
    private Double subsum;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOwid() {
		return owid;
	}

	public void setOwid(String owid) {
		this.owid = owid;
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

	public String getSpecmode() {
		return specmode;
	}

	public void setSpecmode(String specmode) {
		this.specmode = specmode;
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

	public Double getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(Double unitprice) {
		this.unitprice = unitprice;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getRatifyquantity() {
		return ratifyquantity;
	}

	public void setRatifyquantity(Double ratifyquantity) {
		this.ratifyquantity = ratifyquantity;
	}

	public Double getTakequantity() {
		return takequantity;
	}

	public void setTakequantity(Double takequantity) {
		this.takequantity = takequantity;
	}

	public Double getSubsum() {
		return subsum;
	}

	public void setSubsum(Double subsum) {
		this.subsum = subsum;
	}
    
    
}
