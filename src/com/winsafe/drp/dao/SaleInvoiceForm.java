package com.winsafe.drp.dao;

import java.util.Date;

public class SaleInvoiceForm {
	/** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String invoicecode;
    
    private String invoicecontent;

    /** nullable persistent field */
    private Integer invoicetype;
    
    private String invoicetypename;

    /** nullable persistent field */
    private String makeinvoicedate;

    /** nullable persistent field */
    private String invoicedate;

    /** nullable persistent field */
    private Double invoicesum;

    /** nullable persistent field */
    private String cid;
    
    private String cname;
    
    private String invoicetitle;
    
    private String sendaddr;

    /** nullable persistent field */
    private String memo;
    
    /** nullable persistent field */
    private Integer isaudit;
    
    private String isauditname;

    /** nullable persistent field */
    private Integer auditid;
    
    private String auditidname;

    /** nullable persistent field */
    private Date auditdate;
    
    private String makeorganid;
    
    private String makeorganidname;
    
    private Integer makedeptid;
    
    private String makedeptidname;

    /** nullable persistent field */
    private Integer makeid;
    
    private String makename;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Integer updateid;
    
    private String updatename;

    /** nullable persistent field */
    private Date lastdate;



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

	public String getInvoicedate() {
		return invoicedate;
	}

	public void setInvoicedate(String invoicedate) {
		this.invoicedate = invoicedate;
	}

	public Double getInvoicesum() {
		return invoicesum;
	}

	public void setInvoicesum(Double invoicesum) {
		this.invoicesum = invoicesum;
	}

	public Integer getInvoicetype() {
		return invoicetype;
	}

	public void setInvoicetype(Integer invoicetype) {
		this.invoicetype = invoicetype;
	}


	public Date getLastdate() {
		return lastdate;
	}

	public void setLastdate(Date lastdate) {
		this.lastdate = lastdate;
	}

	public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

	public Integer getMakeid() {
		return makeid;
	}

	public void setMakeid(Integer makeid) {
		this.makeid = makeid;
	}

	public String getMakeinvoicedate() {
		return makeinvoicedate;
	}

	public void setMakeinvoicedate(String makeinvoicedate) {
		this.makeinvoicedate = makeinvoicedate;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getUpdateid() {
		return updateid;
	}

	public void setUpdateid(Integer updateid) {
		this.updateid = updateid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getInvoicetypename() {
		return invoicetypename;
	}

	public void setInvoicetypename(String invoicetypename) {
		this.invoicetypename = invoicetypename;
	}

	public String getMakename() {
		return makename;
	}

	public void setMakename(String makename) {
		this.makename = makename;
	}

	public String getUpdatename() {
		return updatename;
	}

	public void setUpdatename(String updatename) {
		this.updatename = updatename;
	}

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

	public Integer getIsaudit() {
		return isaudit;
	}

	public void setIsaudit(Integer isaudit) {
		this.isaudit = isaudit;
	}

	public String getAuditidname() {
		return auditidname;
	}

	public void setAuditidname(String auditidname) {
		this.auditidname = auditidname;
	}

	public String getIsauditname() {
		return isauditname;
	}

	public void setIsauditname(String isauditname) {
		this.isauditname = isauditname;
	}

	public String getInvoicecontent() {
		return invoicecontent;
	}

	public void setInvoicecontent(String invoicecontent) {
		this.invoicecontent = invoicecontent;
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

	public String getInvoicetitle() {
		return invoicetitle;
	}

	public void setInvoicetitle(String invoicetitle) {
		this.invoicetitle = invoicetitle;
	}

	public String getSendaddr() {
		return sendaddr;
	}

	public void setSendaddr(String sendaddr) {
		this.sendaddr = sendaddr;
	}
}
