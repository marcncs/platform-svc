package com.winsafe.drp.dao;

import java.util.Date;

public class PurchaseOrderApproveForm {
	/** identifier field */
    private Long id;

    /** persistent field */
    private String poid;

    /** persistent field */
    private Long approveid;
    
    private Integer actid;
    
    private String actidname;
    
    private String approveidname;

    /** nullable persistent field */
    private String approvecontent;

    /** nullable persistent field */
    private Integer approve;
    
    private String approvename;

    /** nullable persistent field */
    private Date approvedate;

	public Integer getApprove() {
		return approve;
	}

	public void setApprove(Integer approve) {
		this.approve = approve;
	}

	public String getApprovecontent() {
		return approvecontent;
	}

	public void setApprovecontent(String approvecontent) {
		this.approvecontent = approvecontent;
	}

	public Date getApprovedate() {
		return approvedate;
	}

	public void setApprovedate(Date approvedate) {
		this.approvedate = approvedate;
	}

	public Long getApproveid() {
		return approveid;
	}

	public void setApproveid(Long approveid) {
		this.approveid = approveid;
	}

	public String getApproveidname() {
		return approveidname;
	}

	public void setApproveidname(String approveidname) {
		this.approveidname = approveidname;
	}

	public String getApprovename() {
		return approvename;
	}

	public void setApprovename(String approvename) {
		this.approvename = approvename;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPoid() {
		return poid;
	}

	public void setPoid(String poid) {
		this.poid = poid;
	}

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
}
