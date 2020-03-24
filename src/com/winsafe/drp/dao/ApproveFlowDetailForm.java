package com.winsafe.drp.dao;

public class ApproveFlowDetailForm {
	/** nullable persistent field */
    private Integer id;

    /** nullable persistent field */
    private String afid;

    /** nullable persistent field */
    private Integer approveid;
    
    private String approveidname;

    /** nullable persistent field */
    private Integer actid;
    
    private String actidname;
    
    private Integer approveorder;

	public Integer getActid() {
		return actid;
	}

	public void setActid(Integer actid) {
		this.actid = actid;
	}

	public String getActidname() {
		return actidname;
	}

	public void setActidname(String actidname) {
		this.actidname = actidname;
	}


	public String getAfid() {
		return afid;
	}

	public void setAfid(String afid) {
		this.afid = afid;
	}

	public Integer getApproveid() {
		return approveid;
	}

	public void setApproveid(Integer approveid) {
		this.approveid = approveid;
	}

	public String getApproveidname() {
		return approveidname;
	}

	public void setApproveidname(String approveidname) {
		this.approveidname = approveidname;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the approveorder
	 */
	public Integer getApproveorder() {
		return approveorder;
	}

	/**
	 * @param approveorder the approveorder to set
	 */
	public void setApproveorder(Integer approveorder) {
		this.approveorder = approveorder;
	}
	
	
}
