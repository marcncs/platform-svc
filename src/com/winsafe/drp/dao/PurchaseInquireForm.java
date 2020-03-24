package com.winsafe.drp.dao;

import java.util.Date;

/** @author Hibernate CodeGenerator */
public class PurchaseInquireForm  {

	/** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String ppid;

    /** nullable persistent field */
    private String inquiretitle;

    /** persistent field */
    private String pid;
    
    private String providename;

    /** nullable persistent field */
    private String plinkman;

    private Integer makeid;
    
    private String makeorganid;
    public String getMakeorganid() {
		return makeorganid;
	}



	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
	}



	public Integer getMakedeptid() {
		return makedeptid;
	}



	public void setMakedeptid(Integer makedeptid) {
		this.makedeptid = makedeptid;
	}



	private Integer makedeptid;

    /** nullable persistent field */
    private Date makedate;
    
    /** nullable persistent field */
    private Integer isaudit;
    

    /** nullable persistent field */
    private Integer auditid;
    

    /** nullable persistent field */
    private Date auditdate;

    /** nullable persistent field */
    private Integer validdate;

   
    /** nullable persistent field */
    private String remark;
    
    private Integer iscaseend;
    



	public Integer getIscaseend() {
		return iscaseend;
	}



	public void setIscaseend(Integer iscaseend) {
		this.iscaseend = iscaseend;
	}




	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getInquiretitle() {
		return inquiretitle;
	}



	public void setInquiretitle(String inquiretitle) {
		this.inquiretitle = inquiretitle;
	}



	public Date getMakedate() {
		return makedate;
	}



	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}



	public Integer getMakeid() {
		return makeid;
	}



	public void setMakeid(Integer makeid) {
		this.makeid = makeid;
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



	public String getPpid() {
		return ppid;
	}



	public void setPpid(String ppid) {
		this.ppid = ppid;
	}



	public String getProvidename() {
		return providename;
	}



	public void setProvidename(String providename) {
		this.providename = providename;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



	public Integer getValiddate() {
		return validdate;
	}



	public void setValiddate(Integer validdate) {
		this.validdate = validdate;
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




	public Integer getIsaudit() {
		return isaudit;
	}



	public void setIsaudit(Integer isaudit) {
		this.isaudit = isaudit;
	}

}
