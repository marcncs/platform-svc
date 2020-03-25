package com.winsafe.drp.dao;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * @author jelli
 * 2009-4-1
 */
public class MsgForm extends ActionForm {
	/** identifier field */
    private Long id;

    /** nullable persistent field */
    private Integer msgsort;
    
    private String msgsortname;

    /** nullable persistent field */
    private String mobileno;

    /** nullable persistent field */
    private String msgcontent;

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

    /** nullable persistent field */
    private Integer isdeal;
    
    private String isdealname;
    
    private FormFile phonefile;
    
    private Integer quicksend;
    private Integer seltel;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the msgsort
	 */
	public Integer getMsgsort() {
		return msgsort;
	}

	/**
	 * @param msgsort the msgsort to set
	 */
	public void setMsgsort(Integer msgsort) {
		this.msgsort = msgsort;
	}

	/**
	 * @return the msgsortname
	 */
	public String getMsgsortname() {
		return msgsortname;
	}

	/**
	 * @param msgsortname the msgsortname to set
	 */
	public void setMsgsortname(String msgsortname) {
		this.msgsortname = msgsortname;
	}

	/**
	 * @return the mobileno
	 */
	public String getMobileno() {
		return mobileno;
	}

	/**
	 * @param mobileno the mobileno to set
	 */
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	/**
	 * @return the msgcontent
	 */
	public String getMsgcontent() {
		return msgcontent;
	}

	/**
	 * @param msgcontent the msgcontent to set
	 */
	public void setMsgcontent(String msgcontent) {
		this.msgcontent = msgcontent;
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
	 * @return the isdeal
	 */
	public Integer getIsdeal() {
		return isdeal;
	}

	/**
	 * @param isdeal the isdeal to set
	 */
	public void setIsdeal(Integer isdeal) {
		this.isdeal = isdeal;
	}

	/**
	 * @return the isdealname
	 */
	public String getIsdealname() {
		return isdealname;
	}

	/**
	 * @param isdealname the isdealname to set
	 */
	public void setIsdealname(String isdealname) {
		this.isdealname = isdealname;
	}

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

	/**
	 * @return the phonefile
	 */
	public FormFile getPhonefile() {
		return phonefile;
	}

	/**
	 * @param phonefile the phonefile to set
	 */
	public void setPhonefile(FormFile phonefile) {
		this.phonefile = phonefile;
	}

	/**
	 * @return the quicksend
	 */
	public Integer getQuicksend() {
		return quicksend;
	}

	/**
	 * @param quicksend the quicksend to set
	 */
	public void setQuicksend(Integer quicksend) {
		this.quicksend = quicksend;
	}

	/**
	 * @return the seltel
	 */
	public Integer getSeltel() {
		return seltel;
	}

	/**
	 * @param seltel the seltel to set
	 */
	public void setSeltel(Integer seltel) {
		this.seltel = seltel;
	}
    
    
    
    
}
