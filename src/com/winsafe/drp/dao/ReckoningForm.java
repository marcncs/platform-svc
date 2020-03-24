package com.winsafe.drp.dao;


public class ReckoningForm {
	/** identifier field */
    private String id;

    /** nullable persistent field */
    private Integer uid;
    
    private String uidname;

    /** nullable persistent field */
    private String loandate;

    /** nullable persistent field */
    private String purpose;

    /** nullable persistent field */
    private Double loansum;

    /** nullable persistent field */
    private Double backsum;

    /** nullable persistent field */
    private String memo;
    
    private Integer iscash;
    
    /** nullable persistent field */
    private String makeorganid;
    
    private String makeorganidname;

    /** nullable persistent field */
    private Integer makedeptid;
    
    private String makedeptidname;

    /** nullable persistent field */
    private Integer makeid;
    
    private String makeidname;

    /** nullable persistent field */
    private String makedate;

    /** nullable persistent field */
    private Integer isaudit;
    
    private String isauditname;

    /** nullable persistent field */
    private Integer auditid;
    
    private String auditidname;

    /** nullable persistent field */
    private String auditdate;
    
    private Integer fundattach;
    
    private String fundattachname;

	public String getAuditdate() {
		return auditdate;
	}

	public void setAuditdate(String auditdate) {
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

	public Double getBacksum() {
		return backsum;
	}

	public void setBacksum(Double backsum) {
		this.backsum = backsum;
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

	public String getLoandate() {
		return loandate;
	}

	public void setLoandate(String loandate) {
		this.loandate = loandate;
	}

	public Double getLoansum() {
		return loansum;
	}

	public void setLoansum(Double loansum) {
		this.loansum = loansum;
	}

	public String getMakedate() {
		return makedate;
	}

	public void setMakedate(String makedate) {
		this.makedate = makedate;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getUidname() {
		return uidname;
	}

	public void setUidname(String uidname) {
		this.uidname = uidname;
	}

	public Integer getFundattach() {
		return fundattach;
	}

	public void setFundattach(Integer fundattach) {
		this.fundattach = fundattach;
	}

	public String getFundattachname() {
		return fundattachname;
	}

	public void setFundattachname(String fundattachname) {
		this.fundattachname = fundattachname;
	}

	public Integer getIscash() {
		return iscash;
	}

	public void setIscash(Integer iscash) {
		this.iscash = iscash;
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
}
