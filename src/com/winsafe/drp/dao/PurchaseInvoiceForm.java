package com.winsafe.drp.dao;

import java.util.Date;

public class PurchaseInvoiceForm {
	/** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String invoicecode;

    /** nullable persistent field */
    private String invoicecontent;

    /** nullable persistent field */
    private Integer invoicetype;
    
    private String invoicetypename;

    /** nullable persistent field */
    private Date makeinvoicedate;

    /** nullable persistent field */
    private Date invoicedate;
    
    private Double invoicesum;

    /** nullable persistent field */
    private String provideid;
    
    private String provideidname;

    /** nullable persistent field */
    private String memo;
    
    private String InvoiceTitle;
    
    public String getInvoiceTitle() {
		return InvoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		InvoiceTitle = invoiceTitle;
	}

	public String getSendAddr() {
		return SendAddr;
	}

	public void setSendAddr(String sendAddr) {
		SendAddr = sendAddr;
	}

	private String SendAddr;

    /** nullable persistent field */
    private String makeorganid;
    
    private String makeorganidname;

    /** nullable persistent field */
    private Integer makedeptid;
    
    private String makedeptidname;

    /** nullable persistent field */
    private Integer makeid;
    
    private String makeidname;

    /** nullable persistent field */
    private String makedate;

    /** nullable persistent field */
    private Integer isaudit;
    
    private String isauditname;

    /** nullable persistent field */
    private Integer auditid;
    
    private String auditidname;

    /** nullable persistent field */
    private Date auditdate;

    /** nullable persistent field */
    private Integer updateid;
    
    private String updateidname;

    /** nullable persistent field */
    private Date lastdate;

	public Date getAuditdate() {
		return auditdate;
	}

	public void setAuditdate(Date auditdate) {
		this.auditdate = auditdate;
	}

	public Integer getAuditid() {
		return auditid;
	}

	public void setAuditid(Integer auditid) {
		this.auditid = auditid;
	}

	public String getAuditidname() {
		return auditidname;
	}

	public void setAuditidname(String auditidname) {
		this.auditidname = auditidname;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInvoicecode() {
		return invoicecode;
	}

	public void setInvoicecode(String invoicecode) {
		this.invoicecode = invoicecode;
	}

	public String getInvoicecontent() {
		return invoicecontent;
	}

	public void setInvoicecontent(String invoicecontent) {
		this.invoicecontent = invoicecontent;
	}

	public Date getInvoicedate() {
		return invoicedate;
	}

	public void setInvoicedate(Date invoicedate) {
		this.invoicedate = invoicedate;
	}

	public Integer getInvoicetype() {
		return invoicetype;
	}

	public void setInvoicetype(Integer invoicetype) {
		this.invoicetype = invoicetype;
	}

	public String getInvoicetypename() {
		return invoicetypename;
	}

	public void setInvoicetypename(String invoicetypename) {
		this.invoicetypename = invoicetypename;
	}

	public Integer getIsaudit() {
		return isaudit;
	}

	public void setIsaudit(Integer isaudit) {
		this.isaudit = isaudit;
	}

	public String getIsauditname() {
		return isauditname;
	}

	public void setIsauditname(String isauditname) {
		this.isauditname = isauditname;
	}

	public Date getLastdate() {
		return lastdate;
	}

	public void setLastdate(Date lastdate) {
		this.lastdate = lastdate;
	}

	public String getMakedate() {
		return makedate;
	}

	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}

	public Integer getMakedeptid() {
		return makedeptid;
	}

	public void setMakedeptid(Integer makedeptid) {
		this.makedeptid = makedeptid;
	}

	public String getMakedeptidname() {
		return makedeptidname;
	}

	public void setMakedeptidname(String makedeptidname) {
		this.makedeptidname = makedeptidname;
	}

	public Integer getMakeid() {
		return makeid;
	}

	public void setMakeid(Integer makeid) {
		this.makeid = makeid;
	}

	public String getMakeidname() {
		return makeidname;
	}

	public void setMakeidname(String makeidname) {
		this.makeidname = makeidname;
	}

	public Date getMakeinvoicedate() {
		return makeinvoicedate;
	}

	public void setMakeinvoicedate(Date makeinvoicedate) {
		this.makeinvoicedate = makeinvoicedate;
	}

	public String getMakeorganid() {
		return makeorganid;
	}

	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
	}

	public String getMakeorganidname() {
		return makeorganidname;
	}

	public void setMakeorganidname(String makeorganidname) {
		this.makeorganidname = makeorganidname;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getProvideid() {
		return provideid;
	}

	public void setProvideid(String provideid) {
		this.provideid = provideid;
	}

	public String getProvideidname() {
		return provideidname;
	}

	public void setProvideidname(String provideidname) {
		this.provideidname = provideidname;
	}

	public Integer getUpdateid() {
		return updateid;
	}

	public void setUpdateid(Integer updateid) {
		this.updateid = updateid;
	}

	public String getUpdateidname() {
		return updateidname;
	}

	public void setUpdateidname(String updateidname) {
		this.updateidname = updateidname;
	}

	public Double getInvoicesum() {
		return invoicesum;
	}

	public void setInvoicesum(Double invoicesum) {
		this.invoicesum = invoicesum;
	}
}
