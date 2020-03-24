package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ScanConf implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String bname;

    /** full constructor */
    public ScanConf(String id, String bname) {
        this.id = id;
        this.bname = bname;
    }

    /** default constructor */
    public ScanConf() {
    }

    /** minimal constructor */
    public ScanConf(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBname() {
        return this.bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ScanConf) ) return false;
        ScanConf castOther = (ScanConf) other;
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
