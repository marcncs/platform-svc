package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class PaymentMode extends ActionForm implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer irid;

    /** nullable persistent field */
    private String paymentname;

    /** nullable persistent field */
    private Double integralrate;

    /** full constructor */
    public PaymentMode(Integer id, Integer irid, String paymentname, Double integralrate) {
        this.id = id;
        this.irid = irid;
        this.paymentname = paymentname;
        this.integralrate = integralrate;
    }

    /** default constructor */
    public PaymentMode() {
    }

    /** minimal constructor */
    public PaymentMode(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIrid() {
        return this.irid;
    }

    public void setIrid(Integer irid) {
        this.irid = irid;
    }

    public String getPaymentname() {
        return this.paymentname;
    }

    public void setPaymentname(String paymentname) {
        this.paymentname = paymentname;
    }

    public Double getIntegralrate() {
        return this.integralrate;
    }

    public void setIntegralrate(Double integralrate) {
        this.integralrate = integralrate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof PaymentMode) ) return false;
        PaymentMode castOther = (PaymentMode) other;
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
