package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class RoleLeftMenu implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer roleid;

    /** nullable persistent field */
    private Integer lmid;

    /** full constructor */
    public RoleLeftMenu(Integer id, Integer roleid, Integer lmid) {
        this.id = id;
        this.roleid = roleid;
        this.lmid = lmid;
    }

    /** default constructor */
    public RoleLeftMenu() {
    }

    /** minimal constructor */
    public RoleLeftMenu(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleid() {
        return this.roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public Integer getLmid() {
        return this.lmid;
    }

    public void setLmid(Integer lmid) {
        this.lmid = lmid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof RoleLeftMenu) ) return false;
        RoleLeftMenu castOther = (RoleLeftMenu) other;
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
