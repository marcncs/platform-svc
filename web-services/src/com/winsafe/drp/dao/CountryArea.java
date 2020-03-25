package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CountryArea implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String areaname;

    /** nullable persistent field */
    private Integer parentid;

    /** nullable persistent field */
    private Integer rank;

    /** full constructor */
    public CountryArea(Integer id, String areaname, Integer parentid, Integer rank) {
        this.id = id;
        this.areaname = areaname;
        this.parentid = parentid;
        this.rank = rank;
    }

    /** default constructor */
    public CountryArea() {
    }

    /** minimal constructor */
    public CountryArea(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAreaname() {
        return this.areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public Integer getParentid() {
        return this.parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public Integer getRank() {
        return this.rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CountryArea) ) return false;
        CountryArea castOther = (CountryArea) other;
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
