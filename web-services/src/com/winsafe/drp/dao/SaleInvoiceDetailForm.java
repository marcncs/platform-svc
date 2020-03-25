package com.winsafe.drp.dao;


public class SaleInvoiceDetailForm {
	/** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer siid;

    /** nullable persistent field */
    private String soid;

    /** nullable persistent field */
    private Double subsum;
    
    private String makedate;

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

	public Integer getSiid() {
		return siid;
	}

	public void setSiid(Integer siid) {
		this.siid = siid;
	}

	public String getSoid() {
		return soid;
	}

	public void setSoid(String soid) {
		this.soid = soid;
	}

	public Double getSubsum() {
		return subsum;
	}

	public void setSubsum(Double subsum) {
		this.subsum = subsum;
	}
    
}
