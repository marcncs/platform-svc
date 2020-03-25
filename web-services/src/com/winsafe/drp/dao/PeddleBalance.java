package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class PeddleBalance extends ActionForm implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private Long userid;

    /** nullable persistent field */
    private Date blcstartdate;

    /** nullable persistent field */
    private Date blcenddate;

    /** nullable persistent field */
    private Double blctotalsum;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Long makedeptid;

    /** nullable persistent field */
    private Long makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Integer isaudit;

    /** nullable persistent field */
    private Long auditid;

    /** nullable persistent field */
    private Date auditdate;

    /** full constructor */
    public PeddleBalance(Long id, Long userid, Date blcstartdate, Date blcenddate, Double blctotalsum, String makeorganid, Long makedeptid, Long makeid, Date makedate, Integer isaudit, Long auditid, Date auditdate) {
        this.id = id;
        this.userid = userid;
        this.blcstartdate = blcstartdate;
        this.blcenddate = blcenddate;
        this.blctotalsum = blctotalsum;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
    }

    /** default constructor */
    public PeddleBalance() {
    }

    /** minimal constructor */
    public PeddleBalance(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return this.userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Date getBlcstartdate() {
        return this.blcstartdate;
    }

    public void setBlcstartdate(Date blcstartdate) {
        this.blcstartdate = blcstartdate;
    }

    public Date getBlcenddate() {
        return this.blcenddate;
    }

    public void setBlcenddate(Date blcenddate) {
        this.blcenddate = blcenddate;
    }

    public Double getBlctotalsum() {
        return this.blctotalsum;
    }

    public void setBlctotalsum(Double blctotalsum) {
        this.blctotalsum = blctotalsum;
    }

    public String getMakeorganid() {
        return this.makeorganid;
    }

    public void setMakeorganid(String makeorganid) {
        this.makeorganid = makeorganid;
    }

    public Long getMakedeptid() {
        return this.makedeptid;
    }

    public void setMakedeptid(Long makedeptid) {
        this.makedeptid = makedeptid;
    }

    public Long getMakeid() {
        return this.makeid;
    }

    public void setMakeid(Long makeid) {
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

    public Long getAuditid() {
        return this.auditid;
    }

    public void setAuditid(Long auditid) {
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
        if ( !(other instanceof PeddleBalance) ) return false;
        PeddleBalance castOther = (PeddleBalance) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}
