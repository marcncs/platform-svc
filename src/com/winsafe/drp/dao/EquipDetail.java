package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class EquipDetail implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String eid;

    /** nullable persistent field */
    private String sbid;

    /** nullable persistent field */
    private Double erasum;

    /** nullable persistent field */
    private Integer paymentmode;

    /** nullable persistent field */
    private Integer invmsg;

    /** nullable persistent field */
    private Double billsum;

    /** full constructor */
    public EquipDetail(Integer id, String eid, String sbid, Double erasum, Integer paymentmode, Integer invmsg, Double billsum) {
        this.id = id;
        this.eid = eid;
        this.sbid = sbid;
        this.erasum = erasum;
        this.paymentmode = paymentmode;
        this.invmsg = invmsg;
        this.billsum = billsum;
    }

    /** default constructor */
    public EquipDetail() {
    }

    /** minimal constructor */
    public EquipDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEid() {
        return this.eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getSbid() {
        return this.sbid;
    }

    public void setSbid(String sbid) {
        this.sbid = sbid;
    }

    public Double getErasum() {
        return this.erasum;
    }

    public void setErasum(Double erasum) {
        this.erasum = erasum;
    }

    public Integer getPaymentmode() {
        return this.paymentmode;
    }

    public void setPaymentmode(Integer paymentmode) {
        this.paymentmode = paymentmode;
    }

    public Integer getInvmsg() {
        return this.invmsg;
    }

    public void setInvmsg(Integer invmsg) {
        this.invmsg = invmsg;
    }

    public Double getBillsum() {
        return this.billsum;
    }

    public void setBillsum(Double billsum) {
        this.billsum = billsum;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof EquipDetail) ) return false;
        EquipDetail castOther = (EquipDetail) other;
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
