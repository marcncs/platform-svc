package com.winsafe.drp.dao;

import java.util.Date;

public class TakeBillForm {
	 /** identifier field */
    private String id;

    /** nullable persistent field */
    private String oid;

    /** nullable persistent field */
    private String oname;

    /** nullable persistent field */
    private String rlinkman;

    /** nullable persistent field */
    private String tel;
    
    private String makeorganid;
    
    private String makeorganidname;

    /** nullable persistent field */
    private Long makedeptid;

    private String makedeptidname;
    
    /** nullable persistent field */
    private Long makeid;
    
    private String makeidname;

    /** nullable persistent field */
    private String makedate;
    
    private String equiporganid;
    
    private String equiporganidname;

    /** nullable persistent field */
    private Integer isaudit;
    
    private String isauditname;

    /** nullable persistent field */
    private Long auditid;
    
    private String auditidname;

    /** nullable persistent field */
    private String auditdate;
    
    /** nullable persistent field */
    private Integer isblankout;
    
    private String isbankoutname;

    /** nullable persistent field */
    private Long blankoutid;
    
    private String blankoutidname;

    /** nullable persistent field */
    private Date blankoutdate;

    /** nullable persistent field */
    private String blankoutreason;

	public String getAuditdate() {
		return auditdate;
	}

	public void setAuditdate(String auditdate) {
		this.auditdate = auditdate;
	}

	public Long getAuditid() {
		return auditid;
	}

	public void setAuditid(Long auditid) {
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

	public String getMakedate() {
		return makedate;
	}

	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}


	public Long getMakedeptid() {
		return makedeptid;
	}

	public void setMakedeptid(Long makedeptid) {
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

	public Long getMakeid() {
		return makeid;
	}

	public void setMakeid(Long makeid) {
		this.makeid = makeid;
	}

	public String getMakeidname() {
		return makeidname;
	}

	public void setMakeidname(String makeidname) {
		this.makeidname = makeidname;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getOname() {
		return oname;
	}

	public void setOname(String oname) {
		this.oname = oname;
	}

	public String getRlinkman() {
		return rlinkman;
	}

	public void setRlinkman(String rlinkman) {
		this.rlinkman = rlinkman;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEquiporganid() {
		return equiporganid;
	}

	public void setEquiporganid(String equiporganid) {
		this.equiporganid = equiporganid;
	}

	public String getEquiporganidname() {
		return equiporganidname;
	}

	public void setEquiporganidname(String equiporganidname) {
		this.equiporganidname = equiporganidname;
	}

	public Date getBlankoutdate() {
		return blankoutdate;
	}

	public void setBlankoutdate(Date blankoutdate) {
		this.blankoutdate = blankoutdate;
	}

	public Long getBlankoutid() {
		return blankoutid;
	}

	public void setBlankoutid(Long blankoutid) {
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

	public String getIsbankoutname() {
		return isbankoutname;
	}

	public void setIsbankoutname(String isbankoutname) {
		this.isbankoutname = isbankoutname;
	}

	public Integer getIsblankout() {
		return isblankout;
	}

	public void setIsblankout(Integer isblankout) {
		this.isblankout = isblankout;
	}
}
