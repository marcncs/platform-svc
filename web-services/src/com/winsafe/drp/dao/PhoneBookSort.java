package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class PhoneBookSort implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String sortname;

    /** nullable persistent field */
    private Integer userid;

    /** full constructor */
    public PhoneBookSort(Integer id, String sortname, Integer userid) {
        this.id = id;
        this.sortname = sortname;
        this.userid = userid;
    }

    /** default constructor */
    public PhoneBookSort() {
    }

    /** minimal constructor */
    public PhoneBookSort(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSortname() {
        return this.sortname;
    }

    public void setSortname(String sortname) {
        this.sortname = sortname;
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
        if ( !(other instanceof PhoneBookSort) ) return false;
        PhoneBookSort castOther = (PhoneBookSort) other;
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
