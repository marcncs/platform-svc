package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class FeeWasteBook implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private Long feedept;

    /** nullable persistent field */
    private Long feeid;

    /** nullable persistent field */
    private String porject;

    /** nullable persistent field */
    private String billno;

    /** nullable persistent field */
    private String memo;

    /** nullable persistent field */
    private Double cycleinsum;

    /** nullable persistent field */
    private Double cycleoutsum;

    /** nullable persistent field */
    private Date recorddate;

    /** full constructor */
    public FeeWasteBook(Long id, String cid, String cname, Long feedept, Long feeid, String porject, String billno, String memo, Double cycleinsum, Double cycleoutsum, Date recorddate) {
        this.id = id;
        this.cid = cid;
        this.cname = cname;
        this.feedept = feedept;
        this.feeid = feeid;
        this.porject = porject;
        this.billno = billno;
        this.memo = memo;
        this.cycleinsum = cycleinsum;
        this.cycleoutsum = cycleoutsum;
        this.recorddate = recorddate;
    }

    /** default constructor */
    public FeeWasteBook() {
    }

    /** minimal constructor */
    public FeeWasteBook(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCid() {
        return this.cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return this.cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Long getFeedept() {
        return this.feedept;
    }

    public void setFeedept(Long feedept) {
        this.feedept = feedept;
    }

    public Long getFeeid() {
        return this.feeid;
    }

    public void setFeeid(Long feeid) {
        this.feeid = feeid;
    }

    public String getPorject() {
        return this.porject;
    }

    public void setPorject(String porject) {
        this.porject = porject;
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
        if ( !(other instanceof FeeWasteBook) ) return false;
        FeeWasteBook castOther = (FeeWasteBook) other;
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
