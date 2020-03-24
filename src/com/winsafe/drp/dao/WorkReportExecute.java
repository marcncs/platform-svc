package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class WorkReportExecute extends ActionForm implements Serializable {

    /** identifier field */
    private Integer id;

    /** persistent field */
    private Integer reportid;

    /** persistent field */
    private Integer approveid;

    /** nullable persistent field */
    private String approvecontent;

    /** nullable persistent field */
    private Integer approve;

    /** nullable persistent field */
    private Date approvedate;

    /** full constructor */
    public WorkReportExecute(Integer id, Integer reportid, Integer approveid, String approvecontent, Integer approve, Date approvedate) {
        this.id = id;
        this.reportid = reportid;
        this.approveid = approveid;
        this.approvecontent = approvecontent;
        this.approve = approve;
        this.approvedate = approvedate;
    }

    /** default constructor */
    public WorkReportExecute() {
    }

    /** minimal constructor */
    public WorkReportExecute(Integer id, Integer reportid, Integer approveid) {
        this.id = id;
        this.reportid = reportid;
        this.approveid = approveid;
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
        if ( !(other instanceof WorkReportExecute) ) return false;
        WorkReportExecute castOther = (WorkReportExecute) other;
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
