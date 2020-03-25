package com.winsafe.drp.dao;

public class SSMDetailReportForm {


	private String oname;

	private String rname;
	
	private String sname;
	
	private String makedate;
	
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
    private Double punitprice;
    
    private Double sunitprice;

    /** nullable persistent field */
    private Double quantity;
    
    private Double takequantity;

    /** nullable persistent field */
    private Double psubsum;
    
    private Double ssubsum;
    

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


	public String getMakedate() {
		return makedate;
	}

	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public Double getPsubsum() {
		return psubsum;
	}

	public void setPsubsum(Double psubsum) {
		this.psubsum = psubsum;
	}

	public Double getSsubsum() {
		return ssubsum;
	}

	public void setSsubsum(Double ssubsum) {
		this.ssubsum = ssubsum;
	}

	public Double getPunitprice() {
		return punitprice;
	}

	public void setPunitprice(Double punitprice) {
		this.punitprice = punitprice;
	}

	public Double getSunitprice() {
		return sunitprice;
	}

	public void setSunitprice(Double sunitprice) {
		this.sunitprice = sunitprice;
	}

	
	
	
	
    
    
}
