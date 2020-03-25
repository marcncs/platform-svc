package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class OrganArea extends ActionForm implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private Long organid;

    /** nullable persistent field */
    private Long areaid;

    /** full constructor */
    public OrganArea(Long id, Long organid, Long areaid) {
        this.id = id;
        this.organid = organid;
        this.areaid = areaid;
    }

    /** default constructor */
    public OrganArea() {
    }

    /** minimal constructor */
    public OrganArea(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrganid() {
        return this.organid;
    }

    public void setOrganid(Long organid) {
        this.organid = organid;
    }

    public Long getAreaid() {
        return this.areaid;
    }

    public void setAreaid(Long areaid) {
        this.areaid = areaid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof OrganArea) ) return false;
        OrganArea castOther = (OrganArea) other;
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
