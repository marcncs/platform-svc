package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ApproveFlowLog implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String afid;

    /** nullable persistent field */
    private String billno;

    /** nullable persistent field */
    private Integer approveid;

    /** nullable persistent field */
    private Integer approveorder;

    /** nullable persistent field */
    private Integer operate;

    /** nullable persistent field */
    private Integer actid;

    /** nullable persistent field */
    private String approvecontent;

    /** nullable persistent field */
    private Integer approve;

    /** nullable persistent field */
    private Date approvedate;

    /** full constructor */
    public ApproveFlowLog(Integer id, String afid, String billno, Integer approveid, Integer approveorder, Integer operate, Integer actid, String approvecontent, Integer approve, Date approvedate) {
        this.id = id;
        this.afid = afid;
        this.billno = billno;
        this.approveid = approveid;
        this.approveorder = approveorder;
        this.operate = operate;
        this.actid = actid;
        this.approvecontent = approvecontent;
        this.approve = approve;
        this.approvedate = approvedate;
    }

    /** default constructor */
    public ApproveFlowLog() {
    }

    /** minimal constructor */
    public ApproveFlowLog(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAfid() {
        return this.afid;
    }

    public void setAfid(String afid) {
        this.afid = afid;
    }

    public String getBillno() {
        return this.billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public Integer getApproveid() {
        return this.approveid;
    }

    public void setApproveid(Integer approveid) {
        this.approveid = approveid;
    }

    public Integer getApproveorder() {
        return this.approveorder;
    }

    public void setApproveorder(Integer approveorder) {
        this.approveorder = approveorder;
    }

    public Integer getOperate() {
        return this.operate;
    }

    public void setOperate(Integer operate) {
        this.operate = operate;
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
        if ( !(other instanceof ApproveFlowLog) ) return false;
        ApproveFlowLog castOther = (ApproveFlowLog) other;
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
