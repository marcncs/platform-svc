package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

public class VCMPK implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6345127704122523338L;
	private String cid;   
	private Date makedate;
	
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	/**
	 * @return the makedate
	 */
	public Date getMakedate() {
		return makedate;
	}
	/**
	 * @param makedate the makedate to set
	 */
	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}
	
	
	


}
