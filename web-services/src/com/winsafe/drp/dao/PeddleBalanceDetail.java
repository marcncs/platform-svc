package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class PeddleBalanceDetail  extends ActionForm implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String pbid;

    /** nullable persistent field */
    private Integer paymentmode;

    /** nullable persistent field */
    private Double subsum;

    /** nullable persistent field */
    private Integer fundattach;

    /** full constructor */
    public PeddleBalanceDetail(Long id, String pbid, Integer paymentmode, Double subsum, Integer fundattach) {
        this.id = id;
        this.pbid = pbid;
        this.paymentmode = paymentmode;
        this.subsum = subsum;
        this.fundattach = fundattach;
    }

    /** default constructor */
    public PeddleBalanceDetail() {
    }

    /** minimal constructor */
    public PeddleBalanceDetail(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPbid() {
        return this.pbid;
    }

    public void setPbid(String pbid) {
        this.pbid = pbid;
    }

    public Integer getPaymentmode() {
        return this.paymentmode;
    }

    public void setPaymentmode(Integer paymentmode) {
        this.paymentmode = paymentmode;
    }

    public Double getSubsum() {
        return this.subsum;
    }

    public void setSubsum(Double subsum) {
        this.subsum = subsum;
    }

    public Integer getFundattach() {
        return this.fundattach;
    }

    public void setFundattach(Integer fundattach) {
        this.fundattach = fundattach;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof PeddleBalanceDetail) ) return false;
        PeddleBalanceDetail castOther = (PeddleBalanceDetail) other;
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
