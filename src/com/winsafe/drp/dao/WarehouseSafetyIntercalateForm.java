package com.winsafe.drp.dao;

public class WarehouseSafetyIntercalateForm {
	/** identifier field */
    private Long id;

    /** nullable persistent field */
    private Long warehouseid;
    
    private String warehouseidname;

    /** nullable persistent field */
    private String productid;
    
    private String productidname;

    private String specmode;
    
    private Double safetyh;
    
    private Double safetyl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(Long warehouseid) {
		this.warehouseid = warehouseid;
	}

	public String getWarehouseidname() {
		return warehouseidname;
	}

	public void setWarehouseidname(String warehouseidname) {
		this.warehouseidname = warehouseidname;
	}

	public String getSpecmode() {
		return specmode;
	}

	public void setSpecmode(String specmode) {
		this.specmode = specmode;
	}
}
