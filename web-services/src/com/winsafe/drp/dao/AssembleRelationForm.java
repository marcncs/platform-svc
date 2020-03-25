package com.winsafe.drp.dao;

import java.util.Date;

public class AssembleRelationForm {
	/** identifier field */
    private String id;

    /** nullable persistent field */
    private String arproductid;
    
    /** nullable persistent field */
    private String arproductname;

    /** nullable persistent field */
    private String arspecmode;

    /** nullable persistent field */
    private Integer arunitid;
    
    private String arunitidname;

    /** nullable persistent field */
    private Double arquantity;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    
    private String makeorganid;
    
    private Integer makedeptid;
    
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
    private String auditdate;

	public String getArproductid() {
		return arproductid;
	}

	public void setArproductid(String arproductid) {
		this.arproductid = arproductid;
	}

	public String getArproductname() {
		return arproductname;
	}

	public void setArproductname(String arproductname) {
		this.arproductname = arproductname;
	}

	public Double getArquantity() {
		return arquantity;
	}

	public void setArquantity(Double arquantity) {
		this.arquantity = arquantity;
	}

	public String getArspecmode() {
		return arspecmode;
	}

	public void setArspecmode(String arspecmode) {
		this.arspecmode = arspecmode;
	}

	public Integer getArunitid() {
		return arunitid;
	}

	public void setArunitid(Integer arunitid) {
		this.arunitid = arunitid;
	}

	public String getArunitidname() {
		return arunitidname;
	}

	public void setArunitidname(String arunitidname) {
		this.arunitidname = arunitidname;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}



}
