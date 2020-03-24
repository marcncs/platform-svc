package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Role implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String rolename;

    /** nullable persistent field */
    private String describes;

    /** full constructor */
    public Role(Integer id, String rolename, String describes) {
        this.id = id;
        this.rolename = rolename;
        this.describes = describes;
    }

    /** default constructor */
    public Role() {
    }

    /** minimal constructor */
    public Role(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRolename() {
        return this.rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getDescribes() {
        return this.describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Role) ) return false;
        Role castOther = (Role) other;
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
