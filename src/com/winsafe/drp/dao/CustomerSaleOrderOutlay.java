package com.winsafe.drp.dao;

public class CustomerSaleOrderOutlay {
	private String cid;
	
	private String makedate;
	
	private Double subsum;
	
	private Double totaloutlay;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getMakedate() {
		return makedate;
	}

	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}

	public Double getSubsum() {
		return subsum;
	}

	public void setSubsum(Double subsum) {
		this.subsum = subsum;
	}

	public Double getTotaloutlay() {
		return totaloutlay;
	}

	public void setTotaloutlay(Double totaloutlay) {
		this.totaloutlay = totaloutlay;
	}
}
