package com.winsafe.drp.dao;


public class LoanForm {
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
    private String companyidea;

    /** nullable persistent field */
    private String hubidea;
    
    private Integer fundsrc;
    
    private String fundsrcname;
    
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
    private Integer isendcase;
    
    private String isendcasename;

    /** nullable persistent field */
    private Integer endcaseid;
    
    private String endcaseidname;

    /** nullable persistent field */
    private String endcasedate;

    /** nullable persistent field */
    private String auditdate;

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

	public String getCompanyidea() {
		return companyidea;
	}

	public void setCompanyidea(String companyidea) {
		this.companyidea = companyidea;
	}

	public String getHubidea() {
		return hubidea;
	}

	public void setHubidea(String hubidea) {
		this.hubidea = hubidea;
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

	public Integer getFundsrc() {
		return fundsrc;
	}

	public void setFundsrc(Integer fundsrc) {
		this.fundsrc = fundsrc;
	}

	public String getFundsrcname() {
		return fundsrcname;
	}

	public void setFundsrcname(String fundsrcname) {
		this.fundsrcname = fundsrcname;
	}

	public String getEndcasedate() {
		return endcasedate;
	}

	public void setEndcasedate(String endcasedate) {
		this.endcasedate = endcasedate;
	}

	public Integer getEndcaseid() {
		return endcaseid;
	}

	public void setEndcaseid(Integer endcaseid) {
		this.endcaseid = endcaseid;
	}

	public String getEndcaseidname() {
		return endcaseidname;
	}

	public void setEndcaseidname(String endcaseidname) {
		this.endcaseidname = endcaseidname;
	}

	public Integer getIsendcase() {
		return isendcase;
	}

	public void setIsendcase(Integer isendcase) {
		this.isendcase = isendcase;
	}

	public String getIsendcasename() {
		return isendcasename;
	}

	public void setIsendcasename(String isendcasename) {
		this.isendcasename = isendcasename;
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
