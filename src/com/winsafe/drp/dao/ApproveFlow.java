package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ApproveFlow implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String flowname;

    /** nullable persistent field */
    private String memo;

    /** full constructor */
    public ApproveFlow(String id, String flowname, String memo) {
        this.id = id;
        this.flowname = flowname;
        this.memo = memo;
    }

    /** default constructor */
    public ApproveFlow() {
    }

    /** minimal constructor */
    public ApproveFlow(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlowname() {
        return this.flowname;
    }

    public void setFlowname(String flowname) {
        this.flowname = flowname;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ApproveFlow) ) return false;
        ApproveFlow castOther = (ApproveFlow) other;
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
