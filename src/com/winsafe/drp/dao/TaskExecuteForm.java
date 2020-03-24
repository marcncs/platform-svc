package com.winsafe.drp.dao;

public class TaskExecuteForm {
	/** identifier field */
    private Integer id;

    /** persistent field */
    private Integer tpid;

    /** persistent field */
    private Integer userid;
    
    private String useridname;

    /** nullable persistent field */
    private Integer isaffirm;
    
    private String isaffirmname;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIsaffirm() {
		return isaffirm;
	}

	public void setIsaffirm(Integer isaffirm) {
		this.isaffirm = isaffirm;
	}

	public String getIsaffirmname() {
		return isaffirmname;
	}

	public void setIsaffirmname(String isaffirmname) {
		this.isaffirmname = isaffirmname;
	}

	public Integer getTpid() {
		return tpid;
	}

	public void setTpid(Integer tpid) {
		this.tpid = tpid;
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
