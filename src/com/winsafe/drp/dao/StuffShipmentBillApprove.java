package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class StuffShipmentBillApprove extends ActionForm implements Serializable {

    /** identifier field */
    private Long id;

    /** persistent field */
    private String ssid;

    /** persistent field */
    private Long approveid;

    /** nullable persistent field */
    private Integer actid;

    /** nullable persistent field */
    private String approvecontent;

    /** nullable persistent field */
    private Integer approve;

    /** nullable persistent field */
    private Date approvedate;

    /** full constructor */
    public StuffShipmentBillApprove(Long id, String ssid, Long approveid, Integer actid, String approvecontent, Integer approve, Date approvedate) {
        this.id = id;
        this.ssid = ssid;
        this.approveid = approveid;
        this.actid = actid;
        this.approvecontent = approvecontent;
        this.approve = approve;
        this.approvedate = approvedate;
    }

    /** default constructor */
    public StuffShipmentBillApprove() {
    }

    /** minimal constructor */
    public StuffShipmentBillApprove(Long id, String ssid, Long approveid) {
        this.id = id;
        this.ssid = ssid;
        this.approveid = approveid;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSsid() {
        return this.ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public Long getApproveid() {
        return this.approveid;
    }

    public void setApproveid(Long approveid) {
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
        if ( !(other instanceof StuffShipmentBillApprove) ) return false;
        StuffShipmentBillApprove castOther = (StuffShipmentBillApprove) other;
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
