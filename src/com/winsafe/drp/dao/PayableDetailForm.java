package com.winsafe.drp.dao;

public class PayableDetailForm {

	 /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String pid;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;

    /** nullable persistent field */
    private String batch;

    /** nullable persistent field */
    private String billno;
    
    /** nullable persistent field */
    private Integer unitid;
    
    private String unitidname;

    /** nullable persistent field */
    private Double quantity;

    /** nullable persistent field */
    private Double goodsfund;

    /** nullable persistent field */
    private Double scot;

    /** nullable persistent field */
    private Double alreadyquantity;

    /** nullable persistent field */
    private Double alreadysum;

    /** nullable persistent field */
    private Integer isclose;

	public Double getAlreadyquantity() {
		return alreadyquantity;
	}

	public void setAlreadyquantity(Double alreadyquantity) {
		this.alreadyquantity = alreadyquantity;
	}

	public Double getAlreadysum() {
		return alreadysum;
	}

	public void setAlreadysum(Double alreadysum) {
		this.alreadysum = alreadysum;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	public Double getGoodsfund() {
		return goodsfund;
	}

	public void setGoodsfund(Double goodsfund) {
		this.goodsfund = goodsfund;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIsclose() {
		return isclose;
	}

	public void setIsclose(Integer isclose) {
		this.isclose = isclose;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
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

	public Double getScot() {
		return scot;
	}

	public void setScot(Double scot) {
		this.scot = scot;
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

    
    

}
