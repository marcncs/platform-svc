package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ObjIntegral implements Serializable {

    /** identifier field */
    private Integer oiid;

    /** nullable persistent field */
    private String oid;

    /** nullable persistent field */
    private Integer osort;

    /** nullable persistent field */
    private String oname;

    /** nullable persistent field */
    private String omobile;

    /** nullable persistent field */
    private String organid;

    /** nullable persistent field */
    private String keyscontent;

    /** full constructor */
    public ObjIntegral(Integer oiid, String oid, Integer osort, String oname, String omobile, String organid, String keyscontent) {
        this.oiid = oiid;
        this.oid = oid;
        this.osort = osort;
        this.oname = oname;
        this.omobile = omobile;
        this.organid = organid;
        this.keyscontent = keyscontent;
    }

    /** default constructor */
    public ObjIntegral() {
    }

    /** minimal constructor */
    public ObjIntegral(Integer oiid) {
        this.oiid = oiid;
    }

    public Integer getOiid() {
        return this.oiid;
    }

    public void setOiid(Integer oiid) {
        this.oiid = oiid;
    }

    public String getOid() {
        return this.oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Integer getOsort() {
        return this.osort;
    }

    public void setOsort(Integer osort) {
        this.osort = osort;
    }

    public String getOname() {
        return this.oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
    }

    public String getOmobile() {
        return this.omobile;
    }

    public void setOmobile(String omobile) {
        this.omobile = omobile;
    }

    public String getOrganid() {
        return this.organid;
    }

    public void setOrganid(String organid) {
        this.organid = organid;
    }

    public String getKeyscontent() {
        return this.keyscontent;
    }

    public void setKeyscontent(String keyscontent) {
        this.keyscontent = keyscontent;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("oiid", getOiid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ObjIntegral) ) return false;
        ObjIntegral castOther = (ObjIntegral) other;
        return new EqualsBuilder()
            .append(this.getOiid(), castOther.getOiid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getOiid())
            .toHashCode();
    }

}
