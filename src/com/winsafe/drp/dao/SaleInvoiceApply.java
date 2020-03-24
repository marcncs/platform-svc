package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class SaleInvoiceApply extends ActionForm implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private String addr;

    /** nullable persistent field */
    private String linkman;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private String taxcode;

    /** nullable persistent field */
    private String bankname;

    /** nullable persistent field */
    private String bankaccount;

    /** nullable persistent field */
    private Integer invoicesort;

    /** nullable persistent field */
    private Double invoicesum;

    /** nullable persistent field */
    private String invoicecontent;

    /** nullable persistent field */
    private Double settlementsum;

    /** nullable persistent field */
    private Double replacesum;

    /** nullable persistent field */
    private Double shouldreceivetax;

    /** nullable persistent field */
    private Double shouldreceiveinvoicesum;

    /** nullable persistent field */
    private Integer paymentmode;

    /** nullable persistent field */
    private String memo;

    /** nullable persistent field */
    private Long makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Integer isaudit;

    /** nullable persistent field */
    private Long auditid;

    /** nullable persistent field */
    private Date auditdate;

    /** nullable persistent field */
    private Integer isblankout;

    /** nullable persistent field */
    private Long blankoutid;

    /** nullable persistent field */
    private Date blankoutdate;

    /** full constructor */
    public SaleInvoiceApply(String id, String cid, String cname, String addr, String linkman, String tel, String taxcode, String bankname, String bankaccount, Integer invoicesort, Double invoicesum, String invoicecontent, Double settlementsum, Double replacesum, Double shouldreceivetax, Double shouldreceiveinvoicesum, Integer paymentmode, String memo, Long makeid, Date makedate, Integer isaudit, Long auditid, Date auditdate, Integer isblankout, Long blankoutid, Date blankoutdate) {
        this.id = id;
        this.cid = cid;
        this.cname = cname;
        this.addr = addr;
        this.linkman = linkman;
        this.tel = tel;
        this.taxcode = taxcode;
        this.bankname = bankname;
        this.bankaccount = bankaccount;
        this.invoicesort = invoicesort;
        this.invoicesum = invoicesum;
        this.invoicecontent = invoicecontent;
        this.settlementsum = settlementsum;
        this.replacesum = replacesum;
        this.shouldreceivetax = shouldreceivetax;
        this.shouldreceiveinvoicesum = shouldreceiveinvoicesum;
        this.paymentmode = paymentmode;
        this.memo = memo;
        this.makeid = makeid;
        this.makedate = makedate;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
        this.isblankout = isblankout;
        this.blankoutid = blankoutid;
        this.blankoutdate = blankoutdate;
    }

    /** default constructor */
    public SaleInvoiceApply() {
    }

    /** minimal constructor */
    public SaleInvoiceApply(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
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

    public String getAddr() {
        return this.addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getLinkman() {
        return this.linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTaxcode() {
        return this.taxcode;
    }

    public void setTaxcode(String taxcode) {
        this.taxcode = taxcode;
    }

    public String getBankname() {
        return this.bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBankaccount() {
        return this.bankaccount;
    }

    public void setBankaccount(String bankaccount) {
        this.bankaccount = bankaccount;
    }

    public Integer getInvoicesort() {
        return this.invoicesort;
    }

    public void setInvoicesort(Integer invoicesort) {
        this.invoicesort = invoicesort;
    }

    public Double getInvoicesum() {
        return this.invoicesum;
    }

    public void setInvoicesum(Double invoicesum) {
        this.invoicesum = invoicesum;
    }

    public String getInvoicecontent() {
        return this.invoicecontent;
    }

    public void setInvoicecontent(String invoicecontent) {
        this.invoicecontent = invoicecontent;
    }

    public Double getSettlementsum() {
        return this.settlementsum;
    }

    public void setSettlementsum(Double settlementsum) {
        this.settlementsum = settlementsum;
    }

    public Double getReplacesum() {
        return this.replacesum;
    }

    public void setReplacesum(Double replacesum) {
        this.replacesum = replacesum;
    }

    public Double getShouldreceivetax() {
        return this.shouldreceivetax;
    }

    public void setShouldreceivetax(Double shouldreceivetax) {
        this.shouldreceivetax = shouldreceivetax;
    }

    public Double getShouldreceiveinvoicesum() {
        return this.shouldreceiveinvoicesum;
    }

    public void setShouldreceiveinvoicesum(Double shouldreceiveinvoicesum) {
        this.shouldreceiveinvoicesum = shouldreceiveinvoicesum;
    }

    public Integer getPaymentmode() {
        return this.paymentmode;
    }

    public void setPaymentmode(Integer paymentmode) {
        this.paymentmode = paymentmode;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getMakeid() {
        return this.makeid;
    }

    public void setMakeid(Long makeid) {
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

    public Long getAuditid() {
        return this.auditid;
    }

    public void setAuditid(Long auditid) {
        this.auditid = auditid;
    }

    public Date getAuditdate() {
        return this.auditdate;
    }

    public void setAuditdate(Date auditdate) {
        this.auditdate = auditdate;
    }

    public Integer getIsblankout() {
        return this.isblankout;
    }

    public void setIsblankout(Integer isblankout) {
        this.isblankout = isblankout;
    }

    public Long getBlankoutid() {
        return this.blankoutid;
    }

    public void setBlankoutid(Long blankoutid) {
        this.blankoutid = blankoutid;
    }

    public Date getBlankoutdate() {
        return this.blankoutdate;
    }

    public void setBlankoutdate(Date blankoutdate) {
        this.blankoutdate = blankoutdate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof SaleInvoiceApply) ) return false;
        SaleInvoiceApply castOther = (SaleInvoiceApply) other;
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
