package com.winsafe.drp.dao;

public class OrganProductAnnunciator {
	private String productid;
	private String productidname;
	private String specmode;
	private Integer unitid;
	private String unitidname;
	private Double curquantity;
	private Double safetyl;
	private Double safetyh;
	public Double getCurquantity() {
		return curquantity;
	}
	public void setCurquantity(Double curquantity) {
		this.curquantity = curquantity;
	}
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public String getProductidname() {
		return productidname;
	}
	public void setProductidname(String productidname) {
		this.productidname = productidname;
	}
	public Double getSafetyh() {
		return safetyh;
	}
	public void setSafetyh(Double safetyh) {
		this.safetyh = safetyh;
	}
	public Double getSafetyl() {
		return safetyl;
	}
	public void setSafetyl(Double safetyl) {
		this.safetyl = safetyl;
	}
	public String getSpecmode() {
		return specmode;
	}
	public void setSpecmode(String specmode) {
		this.specmode = specmode;
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
