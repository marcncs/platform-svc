package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class IntegralWasteBook extends ActionForm implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String ioid;

    /** nullable persistent field */
    private String billno;

    /** nullable persistent field */
    private Integer integralsort;

    /** nullable persistent field */
    private String memo;

    /** nullable persistent field */
    private Double integralinsum;

    /** nullable persistent field */
    private Double integraloutsum;

    /** nullable persistent field */
    private Date makedate;

    /** full constructor */
    public IntegralWasteBook(Long id, String ioid, String billno, Integer integralsort, String memo, Double integralinsum, Double integraloutsum, Date makedate) {
        this.id = id;
        this.ioid = ioid;
        this.billno = billno;
        this.integralsort = integralsort;
        this.memo = memo;
        this.integralinsum = integralinsum;
        this.integraloutsum = integraloutsum;
        this.makedate = makedate;
    }

    /** default constructor */
    public IntegralWasteBook() {
    }

    /** minimal constructor */
    public IntegralWasteBook(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIoid() {
        return this.ioid;
    }

    public void setIoid(String ioid) {
        this.ioid = ioid;
    }

    public String getBillno() {
        return this.billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public Integer getIntegralsort() {
        return this.integralsort;
    }

    public void setIntegralsort(Integer integralsort) {
        this.integralsort = integralsort;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Double getIntegralinsum() {
        return this.integralinsum;
    }

    public void setIntegralinsum(Double integralinsum) {
        this.integralinsum = integralinsum;
    }

    public Double getIntegraloutsum() {
        return this.integraloutsum;
    }

    public void setIntegraloutsum(Double integraloutsum) {
        this.integraloutsum = integraloutsum;
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
        if ( !(other instanceof IntegralWasteBook) ) return false;
        IntegralWasteBook castOther = (IntegralWasteBook) other;
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
