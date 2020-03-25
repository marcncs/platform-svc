package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class UserRole implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer userid;

    /** nullable persistent field */
    private Integer roleid;

    /** nullable persistent field */
    private Integer ispopedom;

    /** full constructor */
    public UserRole(Integer id, Integer userid, Integer roleid, Integer ispopedom) {
        this.id = id;
        this.userid = userid;
        this.roleid = roleid;
        this.ispopedom = ispopedom;
    }

    /** default constructor */
    public UserRole() {
    }

    /** minimal constructor */
    public UserRole(Integer id) {
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

    public Integer getRoleid() {
        return this.roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public Integer getIspopedom() {
        return this.ispopedom;
    }

    public void setIspopedom(Integer ispopedom) {
        this.ispopedom = ispopedom;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof UserRole) ) return false;
        UserRole castOther = (UserRole) other;
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
