package com.winsafe.drp.dao;

import java.util.Date;

public class CalendarAwakeForm {
	 /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String awakecontent;

    /** nullable persistent field */
    private Date awakedatetime;

    /** nullable persistent field */
    private Integer awakemodel;
    
    private String awakemodelname;

    /** nullable persistent field */
    private Integer isawake;
    
    private String isawakename;

    /** nullable persistent field */
    private Integer isdel;
    
    private String isdelname;

    /** persistent field */
    private Integer userid;
    
    private String useridname;

	public String getAwakecontent() {
		return awakecontent;
	}

	public void setAwakecontent(String awakecontent) {
		this.awakecontent = awakecontent;
	}

	public Date getAwakedatetime() {
		return awakedatetime;
	}

	public void setAwakedatetime(Date awakedatetime) {
		this.awakedatetime = awakedatetime;
	}

	public Integer getAwakemodel() {
		return awakemodel;
	}

	public void setAwakemodel(Integer awakemodel) {
		this.awakemodel = awakemodel;
	}

	public String getAwakemodelname() {
		return awakemodelname;
	}

	public void setAwakemodelname(String awakemodelname) {
		this.awakemodelname = awakemodelname;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}

	public String getIsdelname() {
		return isdelname;
	}

	public void setIsdelname(String isdelname) {
		this.isdelname = isdelname;
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
}
