package com.winsafe.drp.dao;

import java.util.Date;

public class PurchaseBillForm {

	/** identifier field */
    private String id;

    /** nullable persistent field */
    private String ppid;

    /** nullable persistent field */
    private Integer purchasesort;
    
    private String purchasesortname;

    /** nullable persistent field */
    private String pid;
    
    private String pname;

    /** nullable persistent field */
    private String plinkman;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private Integer purchasedept;
    
    private String purchasedeptname;

    /** nullable persistent field */
    private Integer purchaseid;
    
    private String purchaseidname;

    /** nullable persistent field */
    private Integer paymode;
    
    private String paymodename;

    /** nullable persistent field */
    private Integer prompt;

    /** nullable persistent field */
    private Integer invmsg;
    
    private String invmsgname;

    /** nullable persistent field */
    private Integer ismaketicket;

    /** nullable persistent field */
    private Double totalsum;

    /** nullable persistent field */
    private Date receivedate;

    /** nullable persistent field */
    private String receiveaddr;

    /** nullable persistent field */
    private Integer isaudit;
    
    private String isauditname;

    /** nullable persistent field */
    private Integer auditid;
    
    private String auditidname;

    /** nullable persistent field */
    private Date auditdate;

    /** nullable persistent field */
    private Integer isratify;
    
    private String isratifyname;

    /** nullable persistent field */
    private Integer ratifyid;
    
    private String ratifyidname;

    /** nullable persistent field */
    private Date ratifydate;

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
    private Date makedate;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Integer istransferadsum;
    
    private String istransferadsumname;

    /** nullable persistent field */
    private String keyscontent;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getInvmsg() {
		return invmsg;
	}

	public void setInvmsg(Integer invmsg) {
		this.invmsg = invmsg;
	}

	public String getInvmsgname() {
		return invmsgname;
	}

	public void setInvmsgname(String invmsgname) {
		this.invmsgname = invmsgname;
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

	public Integer getIsmaketicket() {
		return ismaketicket;
	}

	public void setIsmaketicket(Integer ismaketicket) {
		this.ismaketicket = ismaketicket;
	}

	public Integer getIsratify() {
		return isratify;
	}

	public void setIsratify(Integer isratify) {
		this.isratify = isratify;
	}

	public String getIsratifyname() {
		return isratifyname;
	}

	public void setIsratifyname(String isratifyname) {
		this.isratifyname = isratifyname;
	}

	public Integer getIstransferadsum() {
		return istransferadsum;
	}

	public void setIstransferadsum(Integer istransferadsum) {
		this.istransferadsum = istransferadsum;
	}

	public String getIstransferadsumname() {
		return istransferadsumname;
	}

	public void setIstransferadsumname(String istransferadsumname) {
		this.istransferadsumname = istransferadsumname;
	}

	public String getKeyscontent() {
		return keyscontent;
	}

	public void setKeyscontent(String keyscontent) {
		this.keyscontent = keyscontent;
	}

	public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
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

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPlinkman() {
		return plinkman;
	}

	public void setPlinkman(String plinkman) {
		this.plinkman = plinkman;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPpid() {
		return ppid;
	}

	public void setPpid(String ppid) {
		this.ppid = ppid;
	}

	public Integer getPrompt() {
		return prompt;
	}

	public void setPrompt(Integer prompt) {
		this.prompt = prompt;
	}

	public Integer getPurchasedept() {
		return purchasedept;
	}

	public void setPurchasedept(Integer purchasedept) {
		this.purchasedept = purchasedept;
	}

	public String getPurchasedeptname() {
		return purchasedeptname;
	}

	public void setPurchasedeptname(String purchasedeptname) {
		this.purchasedeptname = purchasedeptname;
	}

	public Integer getPurchaseid() {
		return purchaseid;
	}

	public void setPurchaseid(Integer purchaseid) {
		this.purchaseid = purchaseid;
	}

	public String getPurchaseidname() {
		return purchaseidname;
	}

	public void setPurchaseidname(String purchaseidname) {
		this.purchaseidname = purchaseidname;
	}

	public Integer getPurchasesort() {
		return purchasesort;
	}

	public void setPurchasesort(Integer purchasesort) {
		this.purchasesort = purchasesort;
	}

	public String getPurchasesortname() {
		return purchasesortname;
	}

	public void setPurchasesortname(String purchasesortname) {
		this.purchasesortname = purchasesortname;
	}

	public Date getRatifydate() {
		return ratifydate;
	}

	public void setRatifydate(Date ratifydate) {
		this.ratifydate = ratifydate;
	}

	public Integer getRatifyid() {
		return ratifyid;
	}

	public void setRatifyid(Integer ratifyid) {
		this.ratifyid = ratifyid;
	}

	public String getRatifyidname() {
		return ratifyidname;
	}

	public void setRatifyidname(String ratifyidname) {
		this.ratifyidname = ratifyidname;
	}

	public String getReceiveaddr() {
		return receiveaddr;
	}

	public void setReceiveaddr(String receiveaddr) {
		this.receiveaddr = receiveaddr;
	}

	public Date getReceivedate() {
		return receivedate;
	}

	public void setReceivedate(Date receivedate) {
		this.receivedate = receivedate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
}
