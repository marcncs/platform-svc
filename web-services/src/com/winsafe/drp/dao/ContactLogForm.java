package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.struts.action.ActionForm;

public class ContactLogForm extends ActionForm implements Serializable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = -1252441091139904355L;

	/** identifier field */
    private Integer id;

    private Integer objsort;
    
    public Integer getObjsort() {
		return objsort;
	}

	public void setObjsort(Integer objsort) {
		this.objsort = objsort;
	}

	/** nullable persistent field */
    private String cid;
    
    private String cidname;

    /** nullable persistent field */
    private String contactdate;

    /** nullable persistent field */
    private Integer contactmode;
    
    private String contactmodename;

    /** nullable persistent field */
    private Integer contactproperty;
    
    private String contactpropertyname;

    /** nullable persistent field */
    private String contactcontent;

    /** nullable persistent field */
    private String feedback;

    /** nullable persistent field */
    private String linkman;

    /** nullable persistent field */
    private String nextcontact;

    private String nextgoal;
    
    private String makeorganid;
    
    public String getMakeorganid() {
		return makeorganid;
	}

	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
	}

	/** nullable persistent field */
    private Integer userid;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getContactcontent() {
		return contactcontent;
	}

	public void setContactcontent(String contactcontent) {
		this.contactcontent = contactcontent;
	}

	public String getContactdate() {
		return contactdate;
	}

	public void setContactdate(String contactdate) {
		this.contactdate = contactdate;
	}

	public Integer getContactmode() {
		return contactmode;
	}

	public void setContactmode(Integer contactmode) {
		this.contactmode = contactmode;
	}

	public String getContactmodename() {
		return contactmodename;
	}

	public void setContactmodename(String contactmodename) {
		this.contactmodename = contactmodename;
	}

	public Integer getContactproperty() {
		return contactproperty;
	}

	public void setContactproperty(Integer contactproperty) {
		this.contactproperty = contactproperty;
	}

	public String getContactpropertyname() {
		return contactpropertyname;
	}

	public void setContactpropertyname(String contactpropertyname) {
		this.contactpropertyname = contactpropertyname;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getNextcontact() {
		return nextcontact;
	}

	public void setNextcontact(String nextcontact) {
		this.nextcontact = nextcontact;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}



	public String getCidname() {
		return cidname;
	}

	public void setCidname(String cidname) {
		this.cidname = cidname;
	}

	public String getNextgoal() {
		return nextgoal;
	}

	public void setNextgoal(String nextgoal) {
		this.nextgoal = nextgoal;
	}

	


}
