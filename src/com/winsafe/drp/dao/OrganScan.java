package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class OrganScan implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String scb;

    /** nullable persistent field */
    private String organid;

    /** nullable persistent field */
    private Integer isscan;

    /** full constructor */
    public OrganScan(Integer id, String scb, String organid, Integer isscan) {
        this.id = id;
        this.scb = scb;
        this.organid = organid;
        this.isscan = isscan;
    }

    /** default constructor */
    public OrganScan() {
    }

    /** minimal constructor */
    public OrganScan(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getScb() {
        return this.scb;
    }

    public void setScb(String scb) {
        this.scb = scb;
    }

    public String getOrganid() {
        return this.organid;
    }

    public void setOrganid(String organid) {
        this.organid = organid;
    }

    public Integer getIsscan() {
        return this.isscan;
    }

    public void setIsscan(Integer isscan) {
        this.isscan = isscan;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof OrganScan) ) return false;
        OrganScan castOther = (OrganScan) other;
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
