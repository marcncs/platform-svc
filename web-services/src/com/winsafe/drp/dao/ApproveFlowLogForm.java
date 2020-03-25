package com.winsafe.drp.dao;

public class ApproveFlowLogForm {
	/** identifier field */
    private Long id;

    /** nullable persistent field */
    private String afid;

    /** nullable persistent field */
    private String billno;

    /** nullable persistent field */
    private Long approveid;
    
    private String approveidname;

    /** nullable persistent field */
    private Integer approveorder;

    /** nullable persistent field */
    private Integer operate;

    /** nullable persistent field */
    private Integer actid;
    
    private String actidname;

    /** nullable persistent field */
    private String approvecontent;

    /** nullable persistent field */
    private Integer approve;
    
    private String approvename;

    /** nullable persistent field */
    private String approvedate;

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

	public String getApprovedate() {
		return approvedate;
	}

	public void setApprovedate(String approvedate) {
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

	public Integer getApproveorder() {
		return approveorder;
	}

	public void setApproveorder(Integer approveorder) {
		this.approveorder = approveorder;
	}

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOperate() {
		return operate;
	}

	public void setOperate(Integer operate) {
		this.operate = operate;
	}

	public String getApprovename() {
		return approvename;
	}

	public void setApprovename(String approvename) {
		this.approvename = approvename;
	}
    
    
}
