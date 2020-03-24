package com.winsafe.drp.dao;

import java.util.Date;


public class AssembleForm {
	/** identifier field */
    private String id;

    /** nullable persistent field */
    private String aproductid;

    /** nullable persistent field */
    private String aproductname;

    /** nullable persistent field */
    private String aspecmode;

    /** nullable persistent field */
    private Integer aunitid;
    
    private String aunitidname;

    /** nullable persistent field */
    private Double aquantity;

    /** nullable persistent field */
    private Double cquantity;

    /** nullable persistent field */
    private Integer adept;
    
    private String adeptname;

    /** nullable persistent field */
    private Date completeintenddate;

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
    private Date makedate;

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

    /** nullable persistent field */
    private String keyscontent;

	public Integer getAdept() {
		return adept;
	}

	public void setAdept(Integer adept) {
		this.adept = adept;
	}

	public String getAdeptname() {
		return adeptname;
	}

	public void setAdeptname(String adeptname) {
		this.adeptname = adeptname;
	}

	public String getAproductid() {
		return aproductid;
	}

	public void setAproductid(String aproductid) {
		this.aproductid = aproductid;
	}

	public String getAproductname() {
		return aproductname;
	}

	public void setAproductname(String aproductname) {
		this.aproductname = aproductname;
	}

	public Double getAquantity() {
		return aquantity;
	}

	public void setAquantity(Double aquantity) {
		this.aquantity = aquantity;
	}

	public String getAspecmode() {
		return aspecmode;
	}

	public void setAspecmode(String aspecmode) {
		this.aspecmode = aspecmode;
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

	public Integer getAunitid() {
		return aunitid;
	}

	public void setAunitid(Integer aunitid) {
		this.aunitid = aunitid;
	}

	public String getAunitidname() {
		return aunitidname;
	}

	public void setAunitidname(String aunitidname) {
		this.aunitidname = aunitidname;
	}

	public Date getCompleteintenddate() {
		return completeintenddate;
	}

	public void setCompleteintenddate(Date completeintenddate) {
		this.completeintenddate = completeintenddate;
	}

	public Double getCquantity() {
		return cquantity;
	}

	public void setCquantity(Double cquantity) {
		this.cquantity = cquantity;
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
}
