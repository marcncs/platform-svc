package com.winsafe.drp.dao;

public class DetailReportForm {


	private String oname;
	private String pid;
	
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	private String soname;
	
	private String makedate;
	
    public String getMakedate() {
		return makedate;
	}

	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}

	/** nullable persistent field */
    private String billid;
    

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

    /** nullable persistent field */
    private Double quantity;
    
    private Double takequantity;

    /** nullable persistent field */
    private Double subsum;
    
    private Double frate;
    
    /**
     * 箱数
     */
    private Integer boxnum;
    /**
     * 散数
     */
    private Double scatternum;
    

	public Double getFrate() {
		return frate;
	}

	public void setFrate(Double frate) {
		this.frate = frate;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
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

	public Integer getBoxnum() {
		return boxnum;
	}

	public void setBoxnum(Integer boxnum) {
		this.boxnum = boxnum;
	}

	public Double getScatternum() {
		return scatternum;
	}

	public void setScatternum(Double scatternum) {
		this.scatternum = scatternum;
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

	public Double getTakequantity() {
		return takequantity;
	}

	public void setTakequantity(Double takequantity) {
		this.takequantity = takequantity;
	}

	/**
	 * @return the oname
	 */
	public String getOname() {
		return oname;
	}

	/**
	 * @param oname the oname to set
	 */
	public void setOname(String oname) {
		this.oname = oname;
	}

	/**
	 * @return the billid
	 */
	public String getBillid() {
		return billid;
	}

	/**
	 * @param billid the billid to set
	 */
	public void setBillid(String billid) {
		this.billid = billid;
	}

	/**
	 * @return the soname
	 */
	public String getSoname() {
		return soname;
	}

	/**
	 * @param soname the soname to set
	 */
	public void setSoname(String soname) {
		this.soname = soname;
	}

	
	
	
	
    
    
}
