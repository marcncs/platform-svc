package com.winsafe.drp.dao;

import java.util.List;

public class PurchaseIncomeTotal {
	private String pname;
	private List pidls;
	private Double subsum;
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public List getPidls() {
		return pidls;
	}
	public void setPidls(List pidls) {
		this.pidls = pidls;
	}
	public Double getSubsum() {
		return subsum;
	}
	public void setSubsum(Double subsum) {
		this.subsum = subsum;
	}
}
