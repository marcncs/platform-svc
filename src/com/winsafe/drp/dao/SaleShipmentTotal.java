package com.winsafe.drp.dao;

import java.util.List;

public class SaleShipmentTotal {
	private String cname;
	private List sbdls;
	private Double subsum;
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public List getSbdls() {
		return sbdls;
	}
	public void setSbdls(List sbdls) {
		this.sbdls = sbdls;
	}
	public Double getSubsum() {
		return subsum;
	}
	public void setSubsum(Double subsum) {
		this.subsum = subsum;
	}
}
