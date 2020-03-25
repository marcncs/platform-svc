package com.winsafe.drp.dao;

public class WarehouseAnnunciatorForm {
	/** identifier field */
    private Long id;

    /** nullable persistent field */
    private Long warehouseid;
    
    private String warehouseidname;

    /** nullable persistent field */
    private Long userid;
    
    private String useridname;

    /** nullable persistent field */
    private Integer isawake;
    
    private String isawakename;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getIsawake() {
		return isawake;
	}

	public void setIsawake(Integer isawake) {
		this.isawake = isawake;
	}

	public String getIsawakename() {
		return isawakename;
	}

	public void setIsawakename(String isawakename) {
		this.isawakename = isawakename;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getUseridname() {
		return useridname;
	}

	public void setUseridname(String useridname) {
		this.useridname = useridname;
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

}
