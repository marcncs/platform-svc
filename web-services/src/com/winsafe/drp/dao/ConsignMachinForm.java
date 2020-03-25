package com.winsafe.drp.dao;

import java.util.Date;

public class ConsignMachinForm {
	/** identifier field */
    private String id;

    /** nullable persistent field */
    private String pid;
    
    private String pidname;

    /** nullable persistent field */
    private String plinkman;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private Integer paymode;
    
    private String paymodename;

    /** nullable persistent field */
    private Double ctotalsum;

    /** nullable persistent field */
    private String cproductid;
    
    private String cproductcode;

    /** nullable persistent field */
    private String cproductname;

    /** nullable persistent field */
    private String cspecmode;

    /** nullable persistent field */
    private Integer cunitid;
    
    private String cunitidname;

    /** nullable persistent field */
    private Double cquantity;
    
    private String crid;
    
    private String cridname;
    
    private Double exrate;
    
    private Double cunitprice;

    /** nullable persistent field */
    private Double completequantity;

    /** nullable persistent field */
    private String completeintenddate;

    /** nullable persistent field */
    private String remark;
    
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
    private Integer isendcase;
    
    private String isendcasename;

    /** nullable persistent field */
    private Integer printtimes;

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

	public String getCompleteintenddate() {
		return completeintenddate;
	}

	public void setCompleteintenddate(String completeintenddate) {
		this.completeintenddate = completeintenddate;
	}

	public Double getCompletequantity() {
		return completequantity;
	}

	public void setCompletequantity(Double completequantity) {
		this.completequantity = completequantity;
	}

	public String getCproductid() {
		return cproductid;
	}

	public void setCproductid(String cproductid) {
		this.cproductid = cproductid;
	}

	public String getCproductname() {
		return cproductname;
	}

	public void setCproductname(String cproductname) {
		this.cproductname = cproductname;
	}

	public Double getCquantity() {
		return cquantity;
	}

	public void setCquantity(Double cquantity) {
		this.cquantity = cquantity;
	}

	public String getCspecmode() {
		return cspecmode;
	}

	public void setCspecmode(String cspecmode) {
		this.cspecmode = cspecmode;
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

	public Integer getIsendcase() {
		return isendcase;
	}

	public void setIsendcase(Integer isendcase) {
		this.isendcase = isendcase;
	}

	public String getIsendcasename() {
		return isendcasename;
	}

	public void setIsendcasename(String isendcasename) {
		this.isendcasename = isendcasename;
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

	public String getPidname() {
		return pidname;
	}

	public void setPidname(String pidname) {
		this.pidname = pidname;
	}

	public String getPlinkman() {
		return plinkman;
	}

	public void setPlinkman(String plinkman) {
		this.plinkman = plinkman;
	}

	public Integer getPrinttimes() {
		return printtimes;
	}

	public void setPrinttimes(Integer printtimes) {
		this.printtimes = printtimes;
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


	public Double getCtotalsum() {
		return ctotalsum;
	}

	public void setCtotalsum(Double ctotalsum) {
		this.ctotalsum = ctotalsum;
	}

	public Double getCunitprice() {
		return cunitprice;
	}

	public void setCunitprice(Double cunitprice) {
		this.cunitprice = cunitprice;
	}

	public Integer getCunitid() {
		return cunitid;
	}

	public void setCunitid(Integer cunitid) {
		this.cunitid = cunitid;
	}

	public String getCunitidname() {
		return cunitidname;
	}

	public void setCunitidname(String cunitidname) {
		this.cunitidname = cunitidname;
	}

	public String getCproductcode() {
		return cproductcode;
	}

	public void setCproductcode(String cproductcode) {
		this.cproductcode = cproductcode;
	}

	public String getCrid() {
		return crid;
	}

	public void setCrid(String crid) {
		this.crid = crid;
	}

	public String getCridname() {
		return cridname;
	}

	public void setCridname(String cridname) {
		this.cridname = cridname;
	}

	public Double getExrate() {
		return exrate;
	}

	public void setExrate(Double exrate) {
		this.exrate = exrate;
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


}
