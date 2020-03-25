package com.winsafe.drp.dao;

public class WarehouseVisitForm {
	/** identifier field */
    private Integer id;

    /** persistent field */
    private Integer wid;
    
    private String widname;

    /** persistent field */
    private Integer userid;
    
    private String useridname;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUseridname() {
		return useridname;
	}

	public void setUseridname(String useridname) {
		this.useridname = useridname;
	}

	public Integer getWid() {
		return wid;
	}

	public void setWid(Integer wid) {
		this.wid = wid;
	}

	public String getWidname() {
		return widname;
	}

	public void setWidname(String widname) {
		this.widname = widname;
	}
}
