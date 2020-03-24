package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class PurchaseInvoice implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String invoicecode;

    /** nullable persistent field */
    private String invoicecontent;

    /** nullable persistent field */
    private Integer invoicetype;

    /** nullable persistent field */
    private Date makeinvoicedate;

    /** nullable persistent field */
    private Date invoicedate;

    /** nullable persistent field */
    private Double invoicesum;

    /** nullable persistent field */
    private String provideid;

    /** nullable persistent field */
    private String memo;

    /** nullable persistent field */
    private String invoicetitle;

    /** nullable persistent field */
    private String sendaddr;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Integer isaudit;

    /** nullable persistent field */
    private Integer auditid;

    /** nullable persistent field */
    private Date auditdate;

    /** nullable persistent field */
    private Integer updateid;

    /** nullable persistent field */
    private Date lastdate;

    /** full constructor */
    public PurchaseInvoice(Integer id, String invoicecode, String invoicecontent, Integer invoicetype, Date makeinvoicedate, Date invoicedate, Double invoicesum, String provideid, String memo, String invoicetitle, String sendaddr, String makeorganid, Integer makedeptid, Integer makeid, Date makedate, Integer isaudit, Integer auditid, Date auditdate, Integer updateid, Date lastdate) {
        this.id = id;
        this.invoicecode = invoicecode;
        this.invoicecontent = invoicecontent;
        this.invoicetype = invoicetype;
        this.makeinvoicedate = makeinvoicedate;
        this.invoicedate = invoicedate;
        this.invoicesum = invoicesum;
        this.provideid = provideid;
        this.memo = memo;
        this.invoicetitle = invoicetitle;
        this.sendaddr = sendaddr;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
        this.updateid = updateid;
        this.lastdate = lastdate;
    }

    /** default constructor */
    public PurchaseInvoice() {
    }

    /** minimal constructor */
    public PurchaseInvoice(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInvoicecode() {
        return this.invoicecode;
    }

    public void setInvoicecode(String invoicecode) {
        this.invoicecode = invoicecode;
    }

    public String getInvoicecontent() {
        return this.invoicecontent;
    }

    public void setInvoicecontent(String invoicecontent) {
        this.invoicecontent = invoicecontent;
    }

    public Integer getInvoicetype() {
        return this.invoicetype;
    }

    public void setInvoicetype(Integer invoicetype) {
        this.invoicetype = invoicetype;
    }

    public Date getMakeinvoicedate() {
        return this.makeinvoicedate;
    }

    public void setMakeinvoicedate(Date makeinvoicedate) {
        this.makeinvoicedate = makeinvoicedate;
    }

    public Date getInvoicedate() {
        return this.invoicedate;
    }

    public void setInvoicedate(Date invoicedate) {
        this.invoicedate = invoicedate;
    }

    public Double getInvoicesum() {
        return this.invoicesum;
    }

    public void setInvoicesum(Double invoicesum) {
        this.invoicesum = invoicesum;
    }

    public String getProvideid() {
        return this.provideid;
    }

    public void setProvideid(String provideid) {
        this.provideid = provideid;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getInvoicetitle() {
        return this.invoicetitle;
    }

    public void setInvoicetitle(String invoicetitle) {
        this.invoicetitle = invoicetitle;
    }

    public String getSendaddr() {
        return this.sendaddr;
    }

    public void setSendaddr(String sendaddr) {
        this.sendaddr = sendaddr;
    }

    public String getMakeorganid() {
        return this.makeorganid;
    }

    public void setMakeorganid(String makeorganid) {
        this.makeorganid = makeorganid;
    }

    public Integer getMakedeptid() {
        return this.makedeptid;
    }

    public void setMakedeptid(Integer makedeptid) {
        this.makedeptid = makedeptid;
    }

    public Integer getMakeid() {
        return this.makeid;
    }

    public void setMakeid(Integer makeid) {
        this.makeid = makeid;
    }

    public Date getMakedate() {
        return this.makedate;
    }

    public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }

    public Integer getIsaudit() {
        return this.isaudit;
    }

    public void setIsaudit(Integer isaudit) {
        this.isaudit = isaudit;
    }

    public Integer getAuditid() {
        return this.auditid;
    }

    public void setAuditid(Integer auditid) {
        this.auditid = auditid;
    }

    public Date getAuditdate() {
        return this.auditdate;
    }

    public void setAuditdate(Date auditdate) {
        this.auditdate = auditdate;
    }

    public Integer getUpdateid() {
        return this.updateid;
    }

    public void setUpdateid(Integer updateid) {
        this.updateid = updateid;
    }

    public Date getLastdate() {
        return this.lastdate;
    }

    public void setLastdate(Date lastdate) {
        this.lastdate = lastdate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof PurchaseInvoice) ) return false;
        PurchaseInvoice castOther = (PurchaseInvoice) other;
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
