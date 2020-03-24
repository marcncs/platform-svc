package com.winsafe.drp.dao;

import java.util.Date;

public class UserLogForm {
	/** identifier field */
    private Long id;

    /** persistent field */
    private Long userid;
    
    private String useridname;

    /** persistent field */
    private Date logtime;

    /** nullable persistent field */
    private String detail;

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getLogtime() {
		return logtime;
	}

	public void setLogtime(Date logtime) {
		this.logtime = logtime;
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
}
