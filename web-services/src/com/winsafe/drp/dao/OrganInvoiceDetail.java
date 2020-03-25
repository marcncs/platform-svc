package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class OrganInvoiceDetail implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer piid;

    /** nullable persistent field */
    private String poid;

    /** nullable persistent field */
    private Double subsum;
    

	/** nullable persistent field */
    private Date makedate;

    /** full constructor */
    public OrganInvoiceDetail(Integer id, Integer piid, String poid, Double subsum, Date makedate) {
        this.id = id;
        this.piid = piid;
        this.poid = poid;
        this.subsum = subsum;
        this.makedate = makedate;
    }

    /** default constructor */
    public OrganInvoiceDetail() {
    }

    /** minimal constructor */
    public OrganInvoiceDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPiid() {
        return this.piid;
    }

    public void setPiid(Integer piid) {
        this.piid = piid;
    }

    public String getPoid() {
        return this.poid;
    }

    public void setPoid(String poid) {
        this.poid = poid;
    }

    public Double getSubsum() {
        return this.subsum;
    }

    public void setSubsum(Double subsum) {
        this.subsum = subsum;
    }

    public Date getMakedate() {
        return this.makedate;
    }

    public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof OrganInvoiceDetail) ) return false;
        OrganInvoiceDetail castOther = (OrganInvoiceDetail) other;
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
