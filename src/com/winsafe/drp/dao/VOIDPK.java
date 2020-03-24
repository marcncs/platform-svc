package com.winsafe.drp.dao;

import java.io.Serializable;

public class VOIDPK implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9189688051573465520L;
	private String billno;   
    private String oid;
	public String getBillno() {
		return billno;
	}
	public void setBillno(String billno) {
		this.billno = billno;
	}
	
	/**
	 * @return the oid
	 */
	public String getOid() {
		return oid;
	}
	/**
	 * @param oid the oid to set
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}
	
	
	


}
