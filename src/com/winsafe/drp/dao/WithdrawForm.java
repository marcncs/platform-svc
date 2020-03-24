package com.winsafe.drp.dao;


public class WithdrawForm {
	 /** identifier field */
    private String id;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String cname;
    
    private String cmobile;

    /** nullable persistent field */
    private String clinkman;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private String warehouseid;
    
    private String warehouseidname;

    /** nullable persistent field */
    private String withdrawcause;

    /** nullable persistent field */
    private Double totalsum;

    /** nullable persistent field */
    private Integer isaudit;
    
    private String isauditname;

    /** nullable persistent field */
    private Integer auditid;
    
    private String auditidname;

    /** nullable persistent field */
    private String auditdate;
    
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
    private Integer isblankout;
    
    private String isblankoutname;

    /** nullable persistent field */
    private Integer blankoutid;
    
    private String blankoutidname;

    /** nullable persistent field */
    private String blankoutdate;
    
    /** nullable persistent field */
    private String billno;

    /** nullable persistent field */
    private Integer paymentmode;
    
    private String paymentmodename;

    /** nullable persistent field */
    private Integer withdrawsort;
    
    private String withdrawsortname;

    /** nullable persistent field */
    private Integer printtimes;

	public String getAuditdate() {
		return auditdate;
	}

	public void setAuditdate(String auditdate) {
		this.auditdate = auditdate;
	}

	public String getAuditidname() {
		return auditidname;
	}

	public void setAuditidname(String auditidname) {
		this.auditidname = auditidname;
	}

	public String getBlankoutdate() {
		return blankoutdate;
	}

	public void setBlankoutdate(String blankoutdate) {
		this.blankoutdate = blankoutdate;
	}

	public String getBlankoutidname() {
		return blankoutidname;
	}

	public void setBlankoutidname(String blankoutidname) {
		this.blankoutidname = blankoutidname;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getClinkman() {
		return clinkman;
	}

	public void setClinkman(String clinkman) {
		this.clinkman = clinkman;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Integer getIsblankout() {
		return isblankout;
	}

	public void setIsblankout(Integer isblankout) {
		this.isblankout = isblankout;
	}

	public String getIsblankoutname() {
		return isblankoutname;
	}

	public void setIsblankoutname(String isblankoutname) {
		this.isblankoutname = isblankoutname;
	}

	public String getMakedate() {
		return makedate;
	}

	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}

	public String getMakeidname() {
		return makeidname;
	}

	public void setMakeidname(String makeidname) {
		this.makeidname = makeidname;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Double getTotalsum() {
		return totalsum;
	}

	public void setTotalsum(Double totalsum) {
		this.totalsum = totalsum;
	}

	public String getWithdrawcause() {
		return withdrawcause;
	}

	public void setWithdrawcause(String withdrawcause) {
		this.withdrawcause = withdrawcause;
	}

	public String getWarehouseidname() {
		return warehouseidname;
	}

	public void setWarehouseidname(String warehouseidname) {
		this.warehouseidname = warehouseidname;
	}

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

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

	public Integer getPrinttimes() {
		return printtimes;
	}

	public void setPrinttimes(Integer printtimes) {
		this.printtimes = printtimes;
	}

	public Integer getWithdrawsort() {
		return withdrawsort;
	}

	public void setWithdrawsort(Integer withdrawsort) {
		this.withdrawsort = withdrawsort;
	}

	public String getWithdrawsortname() {
		return withdrawsortname;
	}

	public void setWithdrawsortname(String withdrawsortname) {
		this.withdrawsortname = withdrawsortname;
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

	public String getCmobile() {
		return cmobile;
	}

	public void setCmobile(String cmobile) {
		this.cmobile = cmobile;
	}

	public void setAuditid(Integer auditid) {
		this.auditid = auditid;
	}

	public void setBlankoutid(Integer blankoutid) {
		this.blankoutid = blankoutid;
	}

	public void setMakedeptid(Integer makedeptid) {
		this.makedeptid = makedeptid;
	}

	public void setMakeid(Integer makeid) {
		this.makeid = makeid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

	public Integer getAuditid() {
		return auditid;
	}

	public Integer getBlankoutid() {
		return blankoutid;
	}

	public Integer getMakedeptid() {
		return makedeptid;
	}

	public Integer getMakeid() {
		return makeid;
	}

	public String getWarehouseid() {
		return warehouseid;
	}
    
    
}
