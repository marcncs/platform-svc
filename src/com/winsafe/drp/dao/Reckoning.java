package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Reckoning implements Serializable {

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
    private Double backsum;

    /** nullable persistent field */
    private String memo;
    
    private Integer iscash;

    /** nullable persistent field */
    private Integer fundattach;
    
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

    /** full constructor */
    public Reckoning(String id, Integer uid, Date loandate, String purpose, Double loansum, Double backsum, String memo, Integer fundattach, Integer makeid, Date makedate, Integer isaudit, Integer auditid, Date auditdate) {
        this.id = id;
        this.uid = uid;
        this.loandate = loandate;
        this.purpose = purpose;
        this.loansum = loansum;
        this.backsum = backsum;
        this.memo = memo;
        this.fundattach = fundattach;
        this.makeid = makeid;
        this.makedate = makedate;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
    }

    /** default constructor */
    public Reckoning() {
    }

    /** minimal constructor */
    public Reckoning(String id) {
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

    public Double getBacksum() {
        return this.backsum;
    }

    public void setBacksum(Double backsum) {
        this.backsum = backsum;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getFundattach() {
        return this.fundattach;
    }

    public void setFundattach(Integer fundattach) {
        this.fundattach = fundattach;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Reckoning) ) return false;
        Reckoning castOther = (Reckoning) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
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

	public String getMakeorganid() {
		return makeorganid;
	}

	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
	}

}
