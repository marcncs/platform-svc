package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class IncomeLogDetail implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String ilid;

    /** nullable persistent field */
    private String rid;

    /** nullable persistent field */
    private Double receivablesum;

    /** nullable persistent field */
    private Integer paymentmode;

    /** nullable persistent field */
    private String billno;

    /** nullable persistent field */
    private Double thisreceivablesum;

    /** full constructor */
    public IncomeLogDetail(Integer id, String ilid, String rid, Double receivablesum, Integer paymentmode, String billno, Double thisreceivablesum) {
        this.id = id;
        this.ilid = ilid;
        this.rid = rid;
        this.receivablesum = receivablesum;
        this.paymentmode = paymentmode;
        this.billno = billno;
        this.thisreceivablesum = thisreceivablesum;
    }

    /** default constructor */
    public IncomeLogDetail() {
    }

    /** minimal constructor */
    public IncomeLogDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIlid() {
        return this.ilid;
    }

    public void setIlid(String ilid) {
        this.ilid = ilid;
    }

    public String getRid() {
        return this.rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public Double getReceivablesum() {
        return this.receivablesum;
    }

    public void setReceivablesum(Double receivablesum) {
        this.receivablesum = receivablesum;
    }

    public Integer getPaymentmode() {
        return this.paymentmode;
    }

    public void setPaymentmode(Integer paymentmode) {
        this.paymentmode = paymentmode;
    }

    public String getBillno() {
        return this.billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public Double getThisreceivablesum() {
        return this.thisreceivablesum;
    }

    public void setThisreceivablesum(Double thisreceivablesum) {
        this.thisreceivablesum = thisreceivablesum;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof IncomeLogDetail) ) return false;
        IncomeLogDetail castOther = (IncomeLogDetail) other;
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
