package com.winsafe.drp.dao;

import java.util.Date;

public class PurchasePlanForm {
	/** identifier field */
    private String id;
    
    private String billno;

    /** nullable persistent field */
    private Integer purchasesort;
    

    /** nullable persistent field */
    private Date plandate;

    /** nullable persistent field */
    private Integer plandept;
    
    private String plandeptname;

    /** nullable persistent field */
    private Integer planid;
    
    private String planidname;

    /** nullable persistent field */
    private Integer isrefer;
    
    private String isrefername;
    
    private Integer actid;
    
    private String actidname;

    /** nullable persistent field */
    private Integer approvestatus;
    
    private String approvestatusname;

    /** nullable persistent field */
    private Date approvedate;

    /** nullable persistent field */
    private Integer isaudit;
    
    private String isauditname;

    /** nullable persistent field */
    private Integer auditid;
    
    private String auditidname;

    /** nullable persistent field */
    private Date auditdate;

    /** nullable persistent field */
    private Integer isratify;
    
    private String isratifyname;

    /** nullable persistent field */
    private Integer ratifyid;
    
    private String ratifyidname;

    /** nullable persistent field */
    private Date ratifydate;

    /** nullable persistent field */
    private Integer iscomplete;
    
    private String iscompletename;

    /** nullable persistent field */
    private String remark;
    
    private String makeorganid;
    
    private String makeorganidname;
    
    

    /** nullable persistent field */
    private Integer makedeptid;
    
    private String makedeptidname;

    /** nullable persistent field */
    private Integer makeid;
    private String makeidname;

    /** nullable persistent field */
    private Date makedate;
    private String  makedatename;

	public Date getApprovedate() {
		return approvedate;
	}

	public void setApprovedate(Date approvedate) {
		this.approvedate = approvedate;
	}

	public Integer getApprovestatus() {
		return approvestatus;
	}

	public void setApprovestatus(Integer approvestatus) {
		this.approvestatus = approvestatus;
	}

	public String getApprovestatusname() {
		return approvestatusname;
	}

	public void setApprovestatusname(String approvestatusname) {
		this.approvestatusname = approvestatusname;
	}

	public Date getAuditdate() {
		return auditdate;
	}

	public void setAuditdate(Date auditdate) {
		this.auditdate = auditdate;
	}

	public Integer getAuditid() {
		return auditid;
	}

	public void setAuditid(Integer auditid) {
		this.auditid = auditid;
	}

	public String getAuditidname() {
		return auditidname;
	}

	public void setAuditidname(String auditidname) {
		this.auditidname = auditidname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getIsaudit() {
		return isaudit;
	}

	public void setIsaudit(Integer isaudit) {
		this.isaudit = isaudit;
	}

	public String getIsauditname() {
		return isauditname;
	}

	public void setIsauditname(String isauditname) {
		this.isauditname = isauditname;
	}

	public Integer getIscomplete() {
		return iscomplete;
	}

	public void setIscomplete(Integer iscomplete) {
		this.iscomplete = iscomplete;
	}

	public String getIscompletename() {
		return iscompletename;
	}

	public void setIscompletename(String iscompletename) {
		this.iscompletename = iscompletename;
	}

	public Integer getIsratify() {
		return isratify;
	}

	public void setIsratify(Integer isratify) {
		this.isratify = isratify;
	}

	public String getIsratifyname() {
		return isratifyname;
	}

	public void setIsratifyname(String isratifyname) {
		this.isratifyname = isratifyname;
	}

	public Integer getIsrefer() {
		return isrefer;
	}

	public void setIsrefer(Integer isrefer) {
		this.isrefer = isrefer;
	}

	public String getIsrefername() {
		return isrefername;
	}

	public void setIsrefername(String isrefername) {
		this.isrefername = isrefername;
	}

	public Date getPlandate() {
		return plandate;
	}

	public void setPlandate(Date plandate) {
		this.plandate = plandate;
	}

	public Integer getPlandept() {
		return plandept;
	}

	public void setPlandept(Integer plandept) {
		this.plandept = plandept;
	}

	public String getPlandeptname() {
		return plandeptname;
	}

	public void setPlandeptname(String plandeptname) {
		this.plandeptname = plandeptname;
	}

	public Integer getPlanid() {
		return planid;
	}

	public void setPlanid(Integer planid) {
		this.planid = planid;
	}

	public String getPlanidname() {
		return planidname;
	}

	public void setPlanidname(String planidname) {
		this.planidname = planidname;
	}

	public Integer getPurchasesort() {
		return purchasesort;
	}

	public void setPurchasesort(Integer purchasesort) {
		this.purchasesort = purchasesort;
	}

	public Date getRatifydate() {
		return ratifydate;
	}

	public void setRatifydate(Date ratifydate) {
		this.ratifydate = ratifydate;
	}

	public Integer getRatifyid() {
		return ratifyid;
	}

	public void setRatifyid(Integer ratifyid) {
		this.ratifyid = ratifyid;
	}

	public String getRatifyidname() {
		return ratifyidname;
	}

	public void setRatifyidname(String ratifyidname) {
		this.ratifyidname = ratifyidname;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

	public String getMakedatename() {
		return makedatename;
	}

	public void setMakedatename(String makedatename) {
		this.makedatename = makedatename;
	}

	public Integer getMakedeptid() {
		return makedeptid;
	}

	public void setMakedeptid(Integer makedeptid) {
		this.makedeptid = makedeptid;
	}

	public String getMakedeptidname() {
		return makedeptidname;
	}

	public void setMakedeptidname(String makedeptidname) {
		this.makedeptidname = makedeptidname;
	}

	public Integer getMakeid() {
		return makeid;
	}

	public void setMakeid(Integer makeid) {
		this.makeid = makeid;
	}

	public String getMakeidname() {
		return makeidname;
	}

	public void setMakeidname(String makeidname) {
		this.makeidname = makeidname;
	}

	public String getMakeorganid() {
		return makeorganid;
	}

	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
	}

	public String getMakeorganidname() {
		return makeorganidname;
	}

	public void setMakeorganidname(String makeorganidname) {
		this.makeorganidname = makeorganidname;
	}

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}
}
