package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class OrganAwake implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String organid;

    /** nullable persistent field */
    private Integer userid;

    /** full constructor */
    public OrganAwake(Integer id, String organid, Integer userid) {
        this.id = id;
        this.organid = organid;
        this.userid = userid;
    }

    /** default constructor */
    public OrganAwake() {
    }

    /** minimal constructor */
    public OrganAwake(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrganid() {
        return this.organid;
    }

    public void setOrganid(String organid) {
        this.organid = organid;
    }

    public Integer getUserid() {
        return this.userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof OrganAwake) ) return false;
        OrganAwake castOther = (OrganAwake) other;
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
