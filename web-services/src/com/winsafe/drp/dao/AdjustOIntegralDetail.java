package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class AdjustOIntegralDetail implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String aoiid;

    /** nullable persistent field */
    private String oid;

    /** nullable persistent field */
    private String oname;

    /** nullable persistent field */
    private Double adjustintegral;

    /** full constructor */
    public AdjustOIntegralDetail(Long id, String aoiid, String oid, String oname, Double adjustintegral) {
        this.id = id;
        this.aoiid = aoiid;
        this.oid = oid;
        this.oname = oname;
        this.adjustintegral = adjustintegral;
    }

    /** default constructor */
    public AdjustOIntegralDetail() {
    }

    /** minimal constructor */
    public AdjustOIntegralDetail(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAoiid() {
        return this.aoiid;
    }

    public void setAoiid(String aoiid) {
        this.aoiid = aoiid;
    }

    public String getOid() {
        return this.oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOname() {
        return this.oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
    }

    public Double getAdjustintegral() {
        return this.adjustintegral;
    }

    public void setAdjustintegral(Double adjustintegral) {
        this.adjustintegral = adjustintegral;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof AdjustOIntegralDetail) ) return false;
        AdjustOIntegralDetail castOther = (AdjustOIntegralDetail) other;
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
