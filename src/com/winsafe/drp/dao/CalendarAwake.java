package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CalendarAwake implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String awakecontent;

    /** nullable persistent field */
    private Date awakedatetime;

    /** nullable persistent field */
    private Integer awakemodel;

    /** nullable persistent field */
    private Integer isawake;

    /** nullable persistent field */
    private Integer isdel;

    /** nullable persistent field */
    private Integer userid;

    /** full constructor */
    public CalendarAwake(Integer id, String awakecontent, Date awakedatetime, Integer awakemodel, Integer isawake, Integer isdel, Integer userid) {
        this.id = id;
        this.awakecontent = awakecontent;
        this.awakedatetime = awakedatetime;
        this.awakemodel = awakemodel;
        this.isawake = isawake;
        this.isdel = isdel;
        this.userid = userid;
    }

    /** default constructor */
    public CalendarAwake() {
    }

    /** minimal constructor */
    public CalendarAwake(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAwakecontent() {
        return this.awakecontent;
    }

    public void setAwakecontent(String awakecontent) {
        this.awakecontent = awakecontent;
    }

    public Date getAwakedatetime() {
        return this.awakedatetime;
    }

    public void setAwakedatetime(Date awakedatetime) {
        this.awakedatetime = awakedatetime;
    }

    public Integer getAwakemodel() {
        return this.awakemodel;
    }

    public void setAwakemodel(Integer awakemodel) {
        this.awakemodel = awakemodel;
    }

    public Integer getIsawake() {
        return this.isawake;
    }

    public void setIsawake(Integer isawake) {
        this.isawake = isawake;
    }

    public Integer getIsdel() {
        return this.isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
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
        if ( !(other instanceof CalendarAwake) ) return false;
        CalendarAwake castOther = (CalendarAwake) other;
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
