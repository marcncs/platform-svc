package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class MobileArea extends ActionForm implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String mobilenum;

    /** nullable persistent field */
    private String areas;

    /** nullable persistent field */
    private String cardtype;

    /** full constructor */
    public MobileArea(Long id, String mobilenum, String areas, String cardtype) {
        this.id = id;
        this.mobilenum = mobilenum;
        this.areas = areas;
        this.cardtype = cardtype;
    }

    /** default constructor */
    public MobileArea() {
    }

    /** minimal constructor */
    public MobileArea(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobilenum() {
        return this.mobilenum;
    }

    public void setMobilenum(String mobilenum) {
        this.mobilenum = mobilenum;
    }

    public String getAreas() {
        return this.areas;
    }

    public void setAreas(String areas) {
        this.areas = areas;
    }

    public String getCardtype() {
        return this.cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof MobileArea) ) return false;
        MobileArea castOther = (MobileArea) other;
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
