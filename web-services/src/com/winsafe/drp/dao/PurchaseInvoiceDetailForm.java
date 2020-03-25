package com.winsafe.drp.dao;

import org.apache.struts.action.ActionForm;

public class PurchaseInvoiceDetailForm extends ActionForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1384992843731683666L;

	/** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer piid;

    /** nullable persistent field */
    private String poid;

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

	public Integer getPiid() {
		return piid;
	}

	public void setPiid(Integer piid) {
		this.piid = piid;
	}

	public String getPoid() {
		return poid;
	}

	public void setPoid(String poid) {
		this.poid = poid;
	}

	public Double getSubsum() {
		return subsum;
	}

	public void setSubsum(Double subsum) {
		this.subsum = subsum;
	}
}
