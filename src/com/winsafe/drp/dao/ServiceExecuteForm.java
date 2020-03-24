package com.winsafe.drp.dao;

public class ServiceExecuteForm {
	/** identifier field */
    private Long id;

    /** nullable persistent field */
    private Long said;

    /** nullable persistent field */
    private Long userid;
    
    private String useridname;

    /** nullable persistent field */
    private Integer isaffirm;
    
    private String isaffirmname;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Long getSaid() {
		return said;
	}

	public void setSaid(Long said) {
		this.said = said;
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
