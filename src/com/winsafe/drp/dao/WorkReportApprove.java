package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class WorkReportApprove implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer reportid;

    /** nullable persistent field */
    private Integer approveid;

    /** nullable persistent field */
    private Integer actid;

    /** nullable persistent field */
    private String approvecontent;

    /** nullable persistent field */
    private Integer approve;

    /** nullable persistent field */
    private Date approvedate;

    /** full constructor */
    public WorkReportApprove(Integer id, Integer reportid, Integer approveid, Integer actid, String approvecontent, Integer approve, Date approvedate) {
        this.id = id;
        this.reportid = reportid;
        this.approveid = approveid;
        this.actid = actid;
        this.approvecontent = approvecontent;
        this.approve = approve;
        this.approvedate = approvedate;
    }

    /** default constructor */
    public WorkReportApprove() {
    }

    /** minimal constructor */
    public WorkReportApprove(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReportid() {
        return this.reportid;
    }

    public void setReportid(Integer reportid) {
        this.reportid = reportid;
    }

    public Integer getApproveid() {
        return this.approveid;
    }

    public void setApproveid(Integer approveid) {
        this.approveid = approveid;
    }

    public Integer getActid() {
        return this.actid;
    }

    public void setActid(Integer actid) {
        this.actid = actid;
    }

    public String getApprovecontent() {
        return this.approvecontent;
    }

    public void setApprovecontent(String approvecontent) {
        this.approvecontent = approvecontent;
    }

    public Integer getApprove() {
        return this.approve;
    }

    public void setApprove(Integer approve) {
        this.approve = approve;
    }

    public Date getApprovedate() {
        return this.approvedate;
    }

    public void setApprovedate(Date approvedate) {
        this.approvedate = approvedate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof WorkReportApprove) ) return false;
        WorkReportApprove castOther = (WorkReportApprove) other;
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
