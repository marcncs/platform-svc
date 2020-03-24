package com.winsafe.drp.dao;

import java.util.List;

public class SetProductPrice {
	private Integer unitid;
	private String unitidname;

	private List ppls;

	public List getPpls() {
		return ppls;
	}

	public void setPpls(List ppls) {
		this.ppls = ppls;
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
	
}
