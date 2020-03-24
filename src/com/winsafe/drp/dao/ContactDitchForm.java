package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.struts.action.ActionForm;

public class ContactDitchForm extends ActionForm implements Serializable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 5205717179952951674L;

	/** identifier field */
    private Long id;

    /** nullable persistent field */
    private String did;
    
    private String didname;

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
    
    /** nullable persistent field */
    private Long userid;
    
    private String useridname;

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getUseridname() {
		return useridname;
	}

	public void setUseridname(String useridname) {
		this.useridname = useridname;
	}

	public String getDidname() {
		return didname;
	}

	public void setDidname(String didname) {
		this.didname = didname;
	}

	public String getNextgoal() {
		return nextgoal;
	}

	public void setNextgoal(String nextgoal) {
		this.nextgoal = nextgoal;
	}


}
