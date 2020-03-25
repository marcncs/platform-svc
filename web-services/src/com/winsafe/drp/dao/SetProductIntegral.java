package com.winsafe.drp.dao;

import java.util.List;

public class SetProductIntegral {
	private Integer unitid;
	private String unitidname;
	private Integer salesortid;
	private String salesortidname;	

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

	public Integer getSalesortid() {
		return salesortid;
	}

	public void setSalesortid(Integer salesortid) {
		this.salesortid = salesortid;
	}

	public String getSalesortidname() {
		return salesortidname;
	}

	public void setSalesortidname(String salesortidname) {
		this.salesortidname = salesortidname;
	}
	
}
