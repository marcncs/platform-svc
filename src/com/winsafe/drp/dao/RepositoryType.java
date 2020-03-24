package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class RepositoryType implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String structcode;

    /** nullable persistent field */
    private String sortname;

    /** nullable persistent field */
    private String remark;

    /** full constructor */
    public RepositoryType(Long id, String structcode, String sortname, String remark) {
        this.id = id;
        this.structcode = structcode;
        this.sortname = sortname;
        this.remark = remark;
    }

    /** default constructor */
    public RepositoryType() {
    }

    /** minimal constructor */
    public RepositoryType(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStructcode() {
        return this.structcode;
    }

    public void setStructcode(String structcode) {
        this.structcode = structcode;
    }

    public String getSortname() {
        return this.sortname;
    }

    public void setSortname(String sortname) {
        this.sortname = sortname;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof RepositoryType) ) return false;
        RepositoryType castOther = (RepositoryType) other;
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
