package com.winsafe.drp.dao;

import java.util.Date;

public class StockCheckForm {
	 /** identifier field */
    private String id;

    /** nullable persistent field */
    private Long warehouseid;
    
    private String warehouseidname;

    /** nullable persistent field */
    private String checkdate;

    /** nullable persistent field */
    private String reckondate;

    /** nullable persistent field */
    private Long checkdept;
    
    private String checkdeptname;

    /** nullable persistent field */
    private Long makeid;
    
    private String makeidname;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Long updateid;
    
    private String updateidname;

    /** nullable persistent field */
    private Date lastdate;

    /** nullable persistent field */
    private String memo;

    /** nullable persistent field */
    private Integer isaudit;
    
    private String isauditname;

    /** nullable persistent field */
    private Long auditid;
    
    private String auditidname;

    /** nullable persistent field */
    private Date auditdate;

    /** nullable persistent field */
    private Integer isratify;
    
    private String isratifyname;

    /** nullable persistent field */
    private Long ratifyid;
    
    private String ratifyidname;

    /** nullable persistent field */
    private Date ratifydate;

    /** nullable persistent field */
    private Integer istally;
    
    private String istallyname;

    /** nullable persistent field */
    private Long tallyid;
    
    private String tallyidname;

    /** nullable persistent field */
    private Date tallydate;
    
    private Integer iscreate;
    
    private String iscreatename;

	public Integer getIscreate() {
		return iscreate;
	}

	public void setIscreate(Integer iscreate) {
		this.iscreate = iscreate;
	}

	public String getIscreatename() {
		return iscreatename;
	}

	public void setIscreatename(String iscreatename) {
		this.iscreatename = iscreatename;
	}

	public Date getAuditdate() {
		return auditdate;
	}

	public void setAuditdate(Date auditdate) {
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

	public String getCheckdate() {
		return checkdate;
	}

	public void setCheckdate(String checkdate) {
		this.checkdate = checkdate;
	}

	public Long getCheckdept() {
		return checkdept;
	}

	public void setCheckdept(Long checkdept) {
		this.checkdept = checkdept;
	}

	public String getCheckdeptname() {
		return checkdeptname;
	}

	public void setCheckdeptname(String checkdeptname) {
		this.checkdeptname = checkdeptname;
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

	public Integer getIstally() {
		return istally;
	}

	public void setIstally(Integer istally) {
		this.istally = istally;
	}

	public String getIstallyname() {
		return istallyname;
	}

	public void setIstallyname(String istallyname) {
		this.istallyname = istallyname;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getRatifydate() {
		return ratifydate;
	}

	public void setRatifydate(Date ratifydate) {
		this.ratifydate = ratifydate;
	}

	public Long getRatifyid() {
		return ratifyid;
	}

	public void setRatifyid(Long ratifyid) {
		this.ratifyid = ratifyid;
	}

	public String getRatifyidname() {
		return ratifyidname;
	}

	public void setRatifyidname(String ratifyidname) {
		this.ratifyidname = ratifyidname;
	}

	public String getReckondate() {
		return reckondate;
	}

	public void setReckondate(String reckondate) {
		this.reckondate = reckondate;
	}

	public Date getTallydate() {
		return tallydate;
	}

	public void setTallydate(Date tallydate) {
		this.tallydate = tallydate;
	}

	public Long getTallyid() {
		return tallyid;
	}

	public void setTallyid(Long tallyid) {
		this.tallyid = tallyid;
	}

	public String getTallyidname() {
		return tallyidname;
	}

	public void setTallyidname(String tallyidname) {
		this.tallyidname = tallyidname;
	}

	public Long getUpdateid() {
		return updateid;
	}

	public void setUpdateid(Long updateid) {
		this.updateid = updateid;
	}

	public String getUpdateidname() {
		return updateidname;
	}

	public void setUpdateidname(String updateidname) {
		this.updateidname = updateidname;
	}

	public Long getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(Long warehouseid) {
		this.warehouseid = warehouseid;
	}

	public String getWarehouseidname() {
		return warehouseidname;
	}

	public void setWarehouseidname(String warehouseidname) {
		this.warehouseidname = warehouseidname;
	}
}
