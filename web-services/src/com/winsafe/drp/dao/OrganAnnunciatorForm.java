package com.winsafe.drp.dao;

public class OrganAnnunciatorForm {
	/** identifier field */
    private Long id;

    /** nullable persistent field */
    private String organid;
    
    private String organidname;

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

	

}
