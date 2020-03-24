package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class CashWasteBook extends ActionForm implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer cbid;

    /** nullable persistent field */
    private String billno;

    /** nullable persistent field */
    private String memo;

    /** nullable persistent field */
    private Double cyclefirstsum;

    /** nullable persistent field */
    private Double cycleinsum;

    /** nullable persistent field */
    private Double cycleoutsum;

    /** nullable persistent field */
    private Double cyclebalancesum;

    /** nullable persistent field */
    private Date recorddate;

    /** full constructor */
    public CashWasteBook(Integer id, Integer cbid, String billno, String memo, Double cyclefirstsum, Double cycleinsum, Double cycleoutsum, Double cyclebalancesum, Date recorddate) {
        this.id = id;
        this.cbid = cbid;
        this.billno = billno;
        this.memo = memo;
        this.cyclefirstsum = cyclefirstsum;
        this.cycleinsum = cycleinsum;
        this.cycleoutsum = cycleoutsum;
        this.cyclebalancesum = cyclebalancesum;
        this.recorddate = recorddate;
    }

    /** default constructor */
    public CashWasteBook() {
    }

    /** minimal constructor */
    public CashWasteBook(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCbid() {
        return this.cbid;
    }

    public void setCbid(Integer cbid) {
        this.cbid = cbid;
    }

    public String getBillno() {
        return this.billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Double getCyclefirstsum() {
        return this.cyclefirstsum;
    }

    public void setCyclefirstsum(Double cyclefirstsum) {
        this.cyclefirstsum = cyclefirstsum;
    }

    public Double getCycleinsum() {
        return this.cycleinsum;
    }

    public void setCycleinsum(Double cycleinsum) {
        this.cycleinsum = cycleinsum;
    }

    public Double getCycleoutsum() {
        return this.cycleoutsum;
    }

    public void setCycleoutsum(Double cycleoutsum) {
        this.cycleoutsum = cycleoutsum;
    }

    public Double getCyclebalancesum() {
        return this.cyclebalancesum;
    }

    public void setCyclebalancesum(Double cyclebalancesum) {
        this.cyclebalancesum = cyclebalancesum;
    }

    public Date getRecorddate() {
        return this.recorddate;
    }

    public void setRecorddate(Date recorddate) {
        this.recorddate = recorddate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CashWasteBook) ) return false;
        CashWasteBook castOther = (CashWasteBook) other;
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
