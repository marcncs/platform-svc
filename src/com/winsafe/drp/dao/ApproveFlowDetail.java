package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ApproveFlowDetail implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String afid;

    /** nullable persistent field */
    private Integer approveid;

    /** nullable persistent field */
    private Integer actid;
    
    private Integer approveorder;

    /** full constructor */
    public ApproveFlowDetail(Integer id, String afid, Integer approveid, Integer actid) {
        this.id = id;
        this.afid = afid;
        this.approveid = approveid;
        this.actid = actid;
    }

    /** default constructor */
    public ApproveFlowDetail() {
    }

    /** minimal constructor */
    public ApproveFlowDetail(Integer id) {
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ApproveFlowDetail) ) return false;
        ApproveFlowDetail castOther = (ApproveFlowDetail) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	/**
	 * @return the approveorder
	 */
	public Integer getApproveorder() {
		return approveorder;
	}

	/**
	 * @param approveorder the approveorder to set
	 */
	public void setApproveorder(Integer approveorder) {
		this.approveorder = approveorder;
	}
    
    

}
