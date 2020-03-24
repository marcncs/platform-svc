package com.winsafe.drp.dao;

import java.util.Date;

public class IncomeLogForm {
	/** identifier field */
    private String id;

    /** nullable persistent field */
    private String roid;

    /** nullable persistent field */
    private String drawee;

    /** nullable persistent field */
    private Integer fundattach;
    
    private String fundattachname;

    /** nullable persistent field */
    private Double incomesum;
    
    private Double alreadyspend;
    
    private Date spenddate;

    /** nullable persistent field */
    private String billnum;
    
    private String voucher;

    /** nullable persistent field */
    private String remark;
    
    /** nullable persistent field */
    private String makeorganid;
    
    private String makeorganidname;

    /** nullable persistent field */
    private Integer makedeptid;

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
    private String auditdate;

    /** nullable persistent field */
    private Integer isreceive;
    
    private String isreceivename;

    /** nullable persistent field */
    private Integer receiveid;
    
    private String receiveidname;

    /** nullable persistent field */
    private String receivedate;
    
    private Integer paymentmode;
    
    private String paymentmodename;


	public Integer getPaymentmode() {
		return paymentmode;
	}

	public void setPaymentmode(Integer paymentmode) {
		this.paymentmode = paymentmode;
	}

	public String getPaymentmodename() {
		return paymentmodename;
	}

	public void setPaymentmodename(String paymentmodename) {
		this.paymentmodename = paymentmodename;
	}

	public String getBillnum() {
		return billnum;
	}

	public void setBillnum(String billnum) {
		this.billnum = billnum;
	}

	public String getDrawee() {
		return drawee;
	}

	public void setDrawee(String drawee) {
		this.drawee = drawee;
	}

	public String getFundattachname() {
		return fundattachname;
	}

	public void setFundattachname(String fundattachname) {
		this.fundattachname = fundattachname;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public Double getIncomesum() {
		return incomesum;
	}

	public void setIncomesum(Double incomesum) {
		this.incomesum = incomesum;
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


	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRoid() {
		return roid;
	}

	public void setRoid(String roid) {
		this.roid = roid;
	}

	public String getAuditdate() {
		return auditdate;
	}

	public void setAuditdate(String auditdate) {
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

	public Integer getFundattach() {
		return fundattach;
	}

	public void setFundattach(Integer fundattach) {
		this.fundattach = fundattach;
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

	public Integer getIsreceive() {
		return isreceive;
	}

	public void setIsreceive(Integer isreceive) {
		this.isreceive = isreceive;
	}

	public String getIsreceivename() {
		return isreceivename;
	}

	public void setIsreceivename(String isreceivename) {
		this.isreceivename = isreceivename;
	}

	public String getMakedate() {
		return makedate;
	}

	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}

	public String getReceivedate() {
		return receivedate;
	}

	public void setReceivedate(String receivedate) {
		this.receivedate = receivedate;
	}

	public Integer getReceiveid() {
		return receiveid;
	}

	public void setReceiveid(Integer receiveid) {
		this.receiveid = receiveid;
	}

	public String getReceiveidname() {
		return receiveidname;
	}

	public void setReceiveidname(String receiveidname) {
		this.receiveidname = receiveidname;
	}

	public Double getAlreadyspend() {
		return alreadyspend;
	}

	public void setAlreadyspend(Double alreadyspend) {
		this.alreadyspend = alreadyspend;
	}

	public Date getSpenddate() {
		return spenddate;
	}

	public void setSpenddate(Date spenddate) {
		this.spenddate = spenddate;
	}

	public Integer getMakedeptid() {
		return makedeptid;
	}

	public void setMakedeptid(Integer makedeptid) {
		this.makedeptid = makedeptid;
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

	public String getVoucher() {
		return voucher;
	}

	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}
}
