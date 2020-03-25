package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class PaymentLogDetail implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String plid;

    /** nullable persistent field */
    private String pid;

    /** nullable persistent field */
    private Double payablesum;

    /** nullable persistent field */
    private Integer paymode;

    /** nullable persistent field */
    private String billno;

    /** nullable persistent field */
    private Double thispayablesum;

    /** full constructor */
    public PaymentLogDetail(Integer id, String plid, String pid, Double payablesum, Integer paymode, String billno, Double thispayablesum) {
        this.id = id;
        this.plid = plid;
        this.pid = pid;
        this.payablesum = payablesum;
        this.paymode = paymode;
        this.billno = billno;
        this.thispayablesum = thispayablesum;
    }

    /** default constructor */
    public PaymentLogDetail() {
    }

    /** minimal constructor */
    public PaymentLogDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlid() {
        return this.plid;
    }

    public void setPlid(String plid) {
        this.plid = plid;
    }

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Double getPayablesum() {
        return this.payablesum;
    }

    public void setPayablesum(Double payablesum) {
        this.payablesum = payablesum;
    }

    public Integer getPaymode() {
        return this.paymode;
    }

    public void setPaymode(Integer paymode) {
        this.paymode = paymode;
    }

    public String getBillno() {
        return this.billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public Double getThispayablesum() {
        return this.thispayablesum;
    }

    public void setThispayablesum(Double thispayablesum) {
        this.thispayablesum = thispayablesum;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof PaymentLogDetail) ) return false;
        PaymentLogDetail castOther = (PaymentLogDetail) other;
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
