package com.winsafe.drp.dao;

import java.util.Date;

public class AlterMoveApplyForm {
	/** identifier field */
    private String id;

    /** nullable persistent field */
    private Date movedate;

    /** nullable persistent field */
    private String outorganid;
    
    private String outorganidname;

    /** nullable persistent field */
    private Double totalsum;

    /** nullable persistent field */
    private Integer paymentmode;
    
    private String paymentmodename;

    /** nullable persistent field */
    private Integer invmsg;
    
    private String invmsgname;

    /** nullable persistent field */
    private Integer transportmode;
    
    private String transportmodename;

    /** nullable persistent field */
    private String transportaddr;

    /** nullable persistent field */
    private String tickettitle;

    /** nullable persistent field */
    private String movecause;

    /** nullable persistent field */
    private String remark;

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
    private Integer isagree;
    
    private String isagreename;

    /** nullable persistent field */
    private Integer agreeid;
    
    private String agreeidname;

    /** nullable persistent field */
    private Date agreedate;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
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
    private Integer isblankout;
    
    private String isblankoutname;

    /** nullable persistent field */
    private Integer blankoutid;
    
    private String blankoutidname;

    /** nullable persistent field */
    private Date blankoutdate;

    /** nullable persistent field */
    private String blankoutreason;

    /** nullable persistent field */
    private Integer printtimes;

    /** nullable persistent field */
    private Integer istrans;
    
    private String istransname;

	public Date getAgreedate() {
		return agreedate;
	}

	public void setAgreedate(Date agreedate) {
		this.agreedate = agreedate;
	}

	public Integer getAgreeid() {
		return agreeid;
	}

	public void setAgreeid(Integer agreeid) {
		this.agreeid = agreeid;
	}

	public String getAgreeidname() {
		return agreeidname;
	}

	public void setAgreeidname(String agreeidname) {
		this.agreeidname = agreeidname;
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

	public String getAuditidname() {
		return auditidname;
	}

	public void setAuditidname(String auditidname) {
		this.auditidname = auditidname;
	}

	public Date getBlankoutdate() {
		return blankoutdate;
	}

	public void setBlankoutdate(Date blankoutdate) {
		this.blankoutdate = blankoutdate;
	}

	public Integer getBlankoutid() {
		return blankoutid;
	}

	public void setBlankoutid(Integer blankoutid) {
		this.blankoutid = blankoutid;
	}

	public String getBlankoutidname() {
		return blankoutidname;
	}

	public void setBlankoutidname(String blankoutidname) {
		this.blankoutidname = blankoutidname;
	}

	public String getBlankoutreason() {
		return blankoutreason;
	}

	public void setBlankoutreason(String blankoutreason) {
		this.blankoutreason = blankoutreason;
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

	public Integer getIsagree() {
		return isagree;
	}

	public void setIsagree(Integer isagree) {
		this.isagree = isagree;
	}

	public String getIsagreename() {
		return isagreename;
	}

	public void setIsagreename(String isagreename) {
		this.isagreename = isagreename;
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

	public Integer getIstrans() {
		return istrans;
	}

	public void setIstrans(Integer istrans) {
		this.istrans = istrans;
	}

	public String getIstransname() {
		return istransname;
	}

	public void setIstransname(String istransname) {
		this.istransname = istransname;
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

	public String getMovecause() {
		return movecause;
	}

	public void setMovecause(String movecause) {
		this.movecause = movecause;
	}


	public Date getMovedate() {
		return movedate;
	}

	public void setMovedate(Date movedate) {
		this.movedate = movedate;
	}

	public String getOutorganid() {
		return outorganid;
	}

	public void setOutorganid(String outorganid) {
		this.outorganid = outorganid;
	}

	public String getOutorganidname() {
		return outorganidname;
	}

	public void setOutorganidname(String outorganidname) {
		this.outorganidname = outorganidname;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTickettitle() {
		return tickettitle;
	}

	public void setTickettitle(String tickettitle) {
		this.tickettitle = tickettitle;
	}

	public Double getTotalsum() {
		return totalsum;
	}

	public void setTotalsum(Double totalsum) {
		this.totalsum = totalsum;
	}

	public String getTransportaddr() {
		return transportaddr;
	}

	public void setTransportaddr(String transportaddr) {
		this.transportaddr = transportaddr;
	}

	public Integer getTransportmode() {
		return transportmode;
	}

	public void setTransportmode(Integer transportmode) {
		this.transportmode = transportmode;
	}

	public String getTransportmodename() {
		return transportmodename;
	}

	public void setTransportmodename(String transportmodename) {
		this.transportmodename = transportmodename;
	}
}
