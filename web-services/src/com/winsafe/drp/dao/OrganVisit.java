package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class OrganVisit implements Serializable {

    /** */
	private static final long serialVersionUID = -5810385152941353011L;

	/** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer userid;

    /** nullable persistent field */
    private String visitorgan;


    /** default constructor */
    public OrganVisit() {
    }

    /** minimal constructor */
    public OrganVisit(Integer id) {
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

    public String getVisitorgan() {
        return this.visitorgan;
    }

    public void setVisitorgan(String visitorgan) {
        this.visitorgan = visitorgan;
    }


    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof OrganVisit) ) return false;
        OrganVisit castOther = (OrganVisit) other;
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
