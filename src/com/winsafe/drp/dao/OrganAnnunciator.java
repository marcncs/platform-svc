package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class OrganAnnunciator extends ActionForm implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String organid;

    /** nullable persistent field */
    private Integer userid;

    /** nullable persistent field */
    private Integer isawake;

    /** full constructor */
    public OrganAnnunciator(Integer id, String organid, Integer userid, Integer isawake) {
        this.id = id;
        this.organid = organid;
        this.userid = userid;
        this.isawake = isawake;
    }

    /** default constructor */
    public OrganAnnunciator() {
    }

    /** minimal constructor */
    public OrganAnnunciator(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    
    public Integer getUserid() {
        return this.userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getIsawake() {
        return this.isawake;
    }

    public void setIsawake(Integer isawake) {
        this.isawake = isawake;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof OrganAnnunciator) ) return false;
        OrganAnnunciator castOther = (OrganAnnunciator) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public String getOrganid() {
		return organid;
	}

	public void setOrganid(String organid) {
		this.organid = organid;
	}

}
