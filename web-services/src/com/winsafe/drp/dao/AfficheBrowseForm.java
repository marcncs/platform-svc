package com.winsafe.drp.dao;

public class AfficheBrowseForm {
	/** identifier field */
    private Integer id;

    /** persistent field */
    private Integer afficheid;

    /** nullable persistent field */
    private Integer userid;

    private String useridname;
    
    /** nullable persistent field */
    private Integer isbrowse;
    
    private String isbrowsename;

	public Integer getAfficheid() {
		return afficheid;
	}

	public void setAfficheid(Integer afficheid) {
		this.afficheid = afficheid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIsbrowse() {
		return isbrowse;
	}

	public void setIsbrowse(Integer isbrowse) {
		this.isbrowse = isbrowse;
	}

	public String getIsbrowsename() {
		return isbrowsename;
	}

	public void setIsbrowsename(String isbrowsename) {
		this.isbrowsename = isbrowsename;
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
