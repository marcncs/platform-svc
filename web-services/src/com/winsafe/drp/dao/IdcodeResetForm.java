package com.winsafe.drp.dao;

import org.apache.struts.action.ActionForm;

/**
 * @author jelli
 * 2009-6-25
 */
public class IdcodeResetForm extends ActionForm {
	/** identifier field */
    private String id;

    /** nullable persistent field */
    private String memo;

    /** nullable persistent field */
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

    /** nullable persistent field */
    private Integer isaudit;
    
    private String isauditname;

    /** nullable persistent field */
    private Long auditid;
    
    private String auditidname;

    /** nullable persistent field */
    private String auditdate;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * @return the makeorganid
	 */
	public String getMakeorganid() {
		return makeorganid;
	}

	/**
	 * @param makeorganid the makeorganid to set
	 */
	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
	}

	/**
	 * @return the makeorganidname
	 */
	public String getMakeorganidname() {
		return makeorganidname;
	}

	/**
	 * @param makeorganidname the makeorganidname to set
	 */
	public void setMakeorganidname(String makeorganidname) {
		this.makeorganidname = makeorganidname;
	}

	/**
	 * @return the makedeptid
	 */
	public Long getMakedeptid() {
		return makedeptid;
	}

	/**
	 * @param makedeptid the makedeptid to set
	 */
	public void setMakedeptid(Long makedeptid) {
		this.makedeptid = makedeptid;
	}

	/**
	 * @return the makedeptidname
	 */
	public String getMakedeptidname() {
		return makedeptidname;
	}

	/**
	 * @param makedeptidname the makedeptidname to set
	 */
	public void setMakedeptidname(String makedeptidname) {
		this.makedeptidname = makedeptidname;
	}

	/**
	 * @return the makeid
	 */
	public Long getMakeid() {
		return makeid;
	}

	/**
	 * @param makeid the makeid to set
	 */
	public void setMakeid(Long makeid) {
		this.makeid = makeid;
	}

	/**
	 * @return the makeidname
	 */
	public String getMakeidname() {
		return makeidname;
	}

	/**
	 * @param makeidname the makeidname to set
	 */
	public void setMakeidname(String makeidname) {
		this.makeidname = makeidname;
	}

	/**
	 * @return the makedate
	 */
	public String getMakedate() {
		return makedate;
	}

	/**
	 * @param makedate the makedate to set
	 */
	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}

	/**
	 * @return the isaudit
	 */
	public Integer getIsaudit() {
		return isaudit;
	}

	/**
	 * @param isaudit the isaudit to set
	 */
	public void setIsaudit(Integer isaudit) {
		this.isaudit = isaudit;
	}

	/**
	 * @return the isauditname
	 */
	public String getIsauditname() {
		return isauditname;
	}

	/**
	 * @param isauditname the isauditname to set
	 */
	public void setIsauditname(String isauditname) {
		this.isauditname = isauditname;
	}

	/**
	 * @return the auditid
	 */
	public Long getAuditid() {
		return auditid;
	}

	/**
	 * @param auditid the auditid to set
	 */
	public void setAuditid(Long auditid) {
		this.auditid = auditid;
	}

	/**
	 * @return the auditidname
	 */
	public String getAuditidname() {
		return auditidname;
	}

	/**
	 * @param auditidname the auditidname to set
	 */
	public void setAuditidname(String auditidname) {
		this.auditidname = auditidname;
	}

	/**
	 * @return the auditdate
	 */
	public String getAuditdate() {
		return auditdate;
	}

	/**
	 * @param auditdate the auditdate to set
	 */
	public void setAuditdate(String auditdate) {
		this.auditdate = auditdate;
	}
    
    
}
