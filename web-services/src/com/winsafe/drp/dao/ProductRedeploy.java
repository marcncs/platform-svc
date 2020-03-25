package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ProductRedeploy implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer redeployid;

    /** nullable persistent field */
    private Integer redeploydept;

    /** nullable persistent field */
    private String redeploymemo;

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
    public ProductRedeploy(Integer id, Integer redeployid, Integer redeploydept, String redeploymemo, Integer makeid, Date makedate, Integer isaudit, Integer auditid, Date auditdate) {
        this.id = id;
        this.redeployid = redeployid;
        this.redeploydept = redeploydept;
        this.redeploymemo = redeploymemo;
        this.makeid = makeid;
        this.makedate = makedate;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
    }

    /** default constructor */
    public ProductRedeploy() {
    }

    /** minimal constructor */
    public ProductRedeploy(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRedeployid() {
        return this.redeployid;
    }

    public void setRedeployid(Integer redeployid) {
        this.redeployid = redeployid;
    }

    public Integer getRedeploydept() {
        return this.redeploydept;
    }

    public void setRedeploydept(Integer redeploydept) {
        this.redeploydept = redeploydept;
    }

    public String getRedeploymemo() {
        return this.redeploymemo;
    }

    public void setRedeploymemo(String redeploymemo) {
        this.redeploymemo = redeploymemo;
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
        if ( !(other instanceof ProductRedeploy) ) return false;
        ProductRedeploy castOther = (ProductRedeploy) other;
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
