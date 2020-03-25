package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Loan implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private Integer uid;

    /** nullable persistent field */
    private Date loandate;

    /** nullable persistent field */
    private String purpose;

    /** nullable persistent field */
    private Double loansum;

    /** nullable persistent field */
    private String companyidea;

    /** nullable persistent field */
    private String hubidea;

    /** nullable persistent field */
    private Integer fundsrc;
    
    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Integer isaudit;

    /** nullable persistent field */
    private Integer auditid;

    /** nullable persistent field */
    private Date auditdate;

    /** nullable persistent field */
    private Integer isendcase;

    /** nullable persistent field */
    private Integer endcaseid;

    /** nullable persistent field */
    private Date endcasedate;

    /** full constructor */
    public Loan(String id, Integer uid, Date loandate, String purpose, Double loansum, String companyidea, String hubidea, Integer fundsrc, Integer makeid, Date makedate, Integer isaudit, Integer auditid, Date auditdate, Integer isendcase, Integer endcaseid, Date endcasedate) {
        this.id = id;
        this.uid = uid;
        this.loandate = loandate;
        this.purpose = purpose;
        this.loansum = loansum;
        this.companyidea = companyidea;
        this.hubidea = hubidea;
        this.fundsrc = fundsrc;
        this.makeid = makeid;
        this.makedate = makedate;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
        this.isendcase = isendcase;
        this.endcaseid = endcaseid;
        this.endcasedate = endcasedate;
    }

    /** default constructor */
    public Loan() {
    }

    /** minimal constructor */
    public Loan(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUid() {
        return this.uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Date getLoandate() {
        return this.loandate;
    }

    public void setLoandate(Date loandate) {
        this.loandate = loandate;
    }

    public String getPurpose() {
        return this.purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Double getLoansum() {
        return this.loansum;
    }

    public void setLoansum(Double loansum) {
        this.loansum = loansum;
    }

    public String getCompanyidea() {
        return this.companyidea;
    }

    public void setCompanyidea(String companyidea) {
        this.companyidea = companyidea;
    }

    public String getHubidea() {
        return this.hubidea;
    }

    public void setHubidea(String hubidea) {
        this.hubidea = hubidea;
    }

    public Integer getFundsrc() {
        return this.fundsrc;
    }

    public void setFundsrc(Integer fundsrc) {
        this.fundsrc = fundsrc;
    }

    public Integer getMakeid() {
        return this.makeid;
    }

    public void setMakeid(Integer makeid) {
        this.makeid = makeid;
    }

    public Date getMakedate() {
        return this.makedate;
    }

    public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }

    public Integer getIsaudit() {
        return this.isaudit;
    }

    public void setIsaudit(Integer isaudit) {
        this.isaudit = isaudit;
    }

    public Integer getAuditid() {
        return this.auditid;
    }

    public void setAuditid(Integer auditid) {
        this.auditid = auditid;
    }

    public Date getAuditdate() {
        return this.auditdate;
    }

    public void setAuditdate(Date auditdate) {
        this.auditdate = auditdate;
    }

    public Integer getIsendcase() {
        return this.isendcase;
    }

    public void setIsendcase(Integer isendcase) {
        this.isendcase = isendcase;
    }

    public Integer getEndcaseid() {
        return this.endcaseid;
    }

    public void setEndcaseid(Integer endcaseid) {
        this.endcaseid = endcaseid;
    }

    public Date getEndcasedate() {
        return this.endcasedate;
    }

    public void setEndcasedate(Date endcasedate) {
        this.endcasedate = endcasedate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Loan) ) return false;
        Loan castOther = (Loan) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public Integer getMakedeptid() {
		return makedeptid;
	}

	public void setMakedeptid(Integer makedeptid) {
		this.makedeptid = makedeptid;
	}

	public String getMakeorganid() {
		return makeorganid;
	}

	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
	}

}
