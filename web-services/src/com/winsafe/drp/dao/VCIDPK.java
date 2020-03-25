package com.winsafe.drp.dao;

import java.io.Serializable;

public class VCIDPK implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -926746778601329560L;
	private String billno;   
	private String organid;
    private String cid;
	public String getBillno() {
		return billno;
	}
	public void setBillno(String billno) {
		this.billno = billno;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getOrganid() {
		return organid;
	}
	public void setOrganid(String organid) {
		this.organid = organid;
	}
	
	


}
