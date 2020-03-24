package com.winsafe.drp.dao;

public class OrganSafetyIntercalateForm {
	/** identifier field */
    private Long id;

    /** nullable persistent field */
    private String organid;
    
    private String organidname;

    /** nullable persistent field */
    private String productid;
    
    private String productidname;

    private String specmode;
    
    private Integer unitid;
    
    private String unitidname;
    
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

	
	public String getSpecmode() {
		return specmode;
	}

	public void setSpecmode(String specmode) {
		this.specmode = specmode;
	}

	public String getOrganid() {
		return organid;
	}

	public void setOrganid(String organid) {
		this.organid = organid;
	}

	public String getOrganidname() {
		return organidname;
	}

	public void setOrganidname(String organidname) {
		this.organidname = organidname;
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
