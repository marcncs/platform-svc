package com.winsafe.drp.dao;
/**
 * @author : jerry
 * @version : 2009-9-15 下午05:52:35
 * www.winsafe.cn
 */
public class OrganTradesDetailForm {

	 /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String otid;

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

    /** nullable persistent field */
    private Double canquantity;
    
    private Double takequantity;
    
    private Double ptakequantity;

    /** nullable persistent field */
    private Double subsum;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOtid() {
		return otid;
	}

	public void setOtid(String otid) {
		this.otid = otid;
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

	public Double getCanquantity() {
		return canquantity;
	}

	public void setCanquantity(Double canquantity) {
		this.canquantity = canquantity;
	}

	public Double getTakequantity() {
		return takequantity;
	}

	public void setTakequantity(Double takequantity) {
		this.takequantity = takequantity;
	}

	public Double getPtakequantity() {
		return ptakequantity;
	}

	public void setPtakequantity(Double ptakequantity) {
		this.ptakequantity = ptakequantity;
	}

	public Double getSubsum() {
		return subsum;
	}

	public void setSubsum(Double subsum) {
		this.subsum = subsum;
	}
    
    
}
