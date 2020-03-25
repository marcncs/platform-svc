package com.winsafe.drp.dao;

import java.util.Date;

public class PaymentLogForm {
	 /** identifier field */
    private String id;

    /** persistent field */
    private String poid;

    /** nullable persistent field */
    private String payee;

    /** nullable persistent field */
    private String paypurpose;

    /** nullable persistent field */
    private Integer paymode;
    
    private String paymodename;

    private Double waitpay;
    /** nullable persistent field */
    private Double paysum;
    
    private Double alreadyspend;
    private Date spenddate;

    /** nullable persistent field */
    private Date billdate;

    /** nullable persistent field */
    private Double owebalance;

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
    
    private String makedeptidname;

    /** nullable persistent field */
    private Integer makeid;
    
    private String makeidname;
    
    /** nullable persistent field */
    private Integer isaudit;
    
    private String isauditname;

    /** nullable persistent field */
    private Integer auditid;
    
    private String auditidname;

    /** nullable persistent field */
    private String auditdate;

    /** nullable persistent field */
    private Integer ispay;
    
    private String ispayname;

    /** nullable persistent field */
    private Integer payid;
    
    private String payidname;

    /** nullable persistent field */
    private String paydate;
    
    private Integer fundsrc;
    
    private String fundsrcname;

    /** nullable persistent field */
    private String makedate;

	

	public Date getBilldate() {
		return billdate;
	}

	public void setBilldate(Date billdate) {
		this.billdate = billdate;
	}

	public String getBillnum() {
		return billnum;
	}

	public void setBillnum(String billnum) {
		this.billnum = billnum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMakedate() {
		return makedate;
	}

	public void setMakedate(String makedate) {
		this.makedate = makedate;
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

	public Double getOwebalance() {
		return owebalance;
	}

	public void setOwebalance(Double owebalance) {
		this.owebalance = owebalance;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public Integer getPaymode() {
		return paymode;
	}

	public void setPaymode(Integer paymode) {
		this.paymode = paymode;
	}

	public String getPaymodename() {
		return paymodename;
	}

	public void setPaymodename(String paymodename) {
		this.paymodename = paymodename;
	}

	public String getPaypurpose() {
		return paypurpose;
	}

	public void setPaypurpose(String paypurpose) {
		this.paypurpose = paypurpose;
	}

	public Double getPaysum() {
		return paysum;
	}

	public void setPaysum(Double paysum) {
		this.paysum = paysum;
	}

	public String getPoid() {
		return poid;
	}

	public void setPoid(String poid) {
		this.poid = poid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getWaitpay() {
		return waitpay;
	}

	public void setWaitpay(Double waitpay) {
		this.waitpay = waitpay;
	}

	public Integer getFundsrc() {
		return fundsrc;
	}

	public void setFundsrc(Integer fundsrc) {
		this.fundsrc = fundsrc;
	}

	public String getFundsrcname() {
		return fundsrcname;
	}

	public void setFundsrcname(String fundsrcname) {
		this.fundsrcname = fundsrcname;
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

	public Integer getIsaudit() {
		return isaudit;
	}

	public void setIsaudit(Integer isaudit) {
		this.isaudit = isaudit;
	}

	public Integer getIspay() {
		return ispay;
	}

	public void setIspay(Integer ispay) {
		this.ispay = ispay;
	}

	public String getPaydate() {
		return paydate;
	}

	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}

	public Integer getPayid() {
		return payid;
	}

	public void setPayid(Integer payid) {
		this.payid = payid;
	}

	public String getIsauditname() {
		return isauditname;
	}

	public void setIsauditname(String isauditname) {
		this.isauditname = isauditname;
	}

	public String getIspayname() {
		return ispayname;
	}

	public void setIspayname(String ispayname) {
		this.ispayname = ispayname;
	}

	public String getAuditidname() {
		return auditidname;
	}

	public void setAuditidname(String auditidname) {
		this.auditidname = auditidname;
	}

	public String getPayidname() {
		return payidname;
	}

	public void setPayidname(String payidname) {
		this.payidname = payidname;
	}

	public Double getAlreadyspend() {
		return alreadyspend;
	}

	public void setAlreadyspend(Double alreadyspend) {
		this.alreadyspend = alreadyspend;
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

	public Date getSpenddate() {
		return spenddate;
	}

	public void setSpenddate(Date spenddate) {
		this.spenddate = spenddate;
	}

	public String getVoucher() {
		return voucher;
	}

	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}

}
