package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class RoleMenu implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer roleid;

    /** nullable persistent field */
    private Integer operateid;

    /** full constructor */
    public RoleMenu(Integer id, Integer roleid, Integer operateid) {
        this.id = id;
        this.roleid = roleid;
        this.operateid = operateid;
    }

    /** default constructor */
    public RoleMenu() {
    }

    /** minimal constructor */
    public RoleMenu(Integer id) {
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

    public Integer getOperateid() {
        return this.operateid;
    }

    public void setOperateid(Integer operateid) {
        this.operateid = operateid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof RoleMenu) ) return false;
        RoleMenu castOther = (RoleMenu) other;
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
