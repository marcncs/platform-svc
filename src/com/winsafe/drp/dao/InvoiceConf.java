package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class InvoiceConf extends ActionForm implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String ivname;

    /** nullable persistent field */
    private Double ivrate;

    /** full constructor */
    public InvoiceConf(Long id, String ivname, Double ivrate) {
        this.id = id;
        this.ivname = ivname;
        this.ivrate = ivrate;
    }

    /** default constructor */
    public InvoiceConf() {
    }

    /** minimal constructor */
    public InvoiceConf(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIvname() {
        return this.ivname;
    }

    public void setIvname(String ivname) {
        this.ivname = ivname;
    }

    public Double getIvrate() {
        return this.ivrate;
    }

    public void setIvrate(Double ivrate) {
        this.ivrate = ivrate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof InvoiceConf) ) return false;
        InvoiceConf castOther = (InvoiceConf) other;
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
