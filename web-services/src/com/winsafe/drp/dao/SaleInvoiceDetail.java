package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class SaleInvoiceDetail extends ActionForm implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer siid;

    /** nullable persistent field */
    private String soid;

    /** nullable persistent field */
    private Double subsum;
    
    private Date makedate;

    public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

	/** full constructor */
    public SaleInvoiceDetail(Integer id, Integer siid, String soid, Double subsum) {
        this.id = id;
        this.siid = siid;
        this.soid = soid;
        this.subsum = subsum;
    }

    /** default constructor */
    public SaleInvoiceDetail() {
    }

    /** minimal constructor */
    public SaleInvoiceDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSiid() {
        return this.siid;
    }

    public void setSiid(Integer siid) {
        this.siid = siid;
    }

    public String getSoid() {
        return this.soid;
    }

    public void setSoid(String soid) {
        this.soid = soid;
    }

    public Double getSubsum() {
        return this.subsum;
    }

    public void setSubsum(Double subsum) {
        this.subsum = subsum;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof SaleInvoiceDetail) ) return false;
        SaleInvoiceDetail castOther = (SaleInvoiceDetail) other;
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
