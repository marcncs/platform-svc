package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class PurchaseOrderApprove implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String poid;

    /** nullable persistent field */
    private Long approveid;

    /** nullable persistent field */
    private Integer actid;

    /** nullable persistent field */
    private String approvecontent;

    /** nullable persistent field */
    private Integer approve;

    /** nullable persistent field */
    private Date approvedate;

    /** nullable persistent field */
    private Integer approveorder;

    /** nullable persistent field */
    private Integer operate;

    /** full constructor */
    public PurchaseOrderApprove(Long id, String poid, Long approveid, Integer actid, String approvecontent, Integer approve, Date approvedate, Integer approveorder, Integer operate) {
        this.id = id;
        this.poid = poid;
        this.approveid = approveid;
        this.actid = actid;
        this.approvecontent = approvecontent;
        this.approve = approve;
        this.approvedate = approvedate;
        this.approveorder = approveorder;
        this.operate = operate;
    }

    /** default constructor */
    public PurchaseOrderApprove() {
    }

    /** minimal constructor */
    public PurchaseOrderApprove(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPoid() {
        return this.poid;
    }

    public void setPoid(String poid) {
        this.poid = poid;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof PurchaseOrderApprove) ) return false;
        PurchaseOrderApprove castOther = (PurchaseOrderApprove) other;
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
