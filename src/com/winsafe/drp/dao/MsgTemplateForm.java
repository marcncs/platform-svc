package com.winsafe.drp.dao;

import org.apache.struts.action.ActionForm;

/**
 * @author jelli
 * 2009-5-13
 */
public class MsgTemplateForm extends ActionForm {

	/** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String templatename;

    /** nullable persistent field */
    private String templatecontent;

    /** nullable persistent field */
    private Integer templatetype;
    
    private String templatetypename;

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
    private String makedate;

    /** nullable persistent field */
    private Integer updid;
    
    private String updidname;

    /** nullable persistent field */
    private String upddate;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the templatename
	 */
	public String getTemplatename() {
		return templatename;
	}

	/**
	 * @param templatename the templatename to set
	 */
	public void setTemplatename(String templatename) {
		this.templatename = templatename;
	}

	/**
	 * @return the templatecontent
	 */
	public String getTemplatecontent() {
		return templatecontent;
	}

	/**
	 * @param templatecontent the templatecontent to set
	 */
	public void setTemplatecontent(String templatecontent) {
		this.templatecontent = templatecontent;
	}

	/**
	 * @return the templatetype
	 */
	public Integer getTemplatetype() {
		return templatetype;
	}

	/**
	 * @param templatetype the templatetype to set
	 */
	public void setTemplatetype(Integer templatetype) {
		this.templatetype = templatetype;
	}

	/**
	 * @return the templatetypename
	 */
	public String getTemplatetypename() {
		return templatetypename;
	}

	/**
	 * @param templatetypename the templatetypename to set
	 */
	public void setTemplatetypename(String templatetypename) {
		this.templatetypename = templatetypename;
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
	public Integer getMakedeptid() {
		return makedeptid;
	}

	/**
	 * @param makedeptid the makedeptid to set
	 */
	public void setMakedeptid(Integer makedeptid) {
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
	public Integer getMakeid() {
		return makeid;
	}

	/**
	 * @param makeid the makeid to set
	 */
	public void setMakeid(Integer makeid) {
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
	 * @return the updid
	 */
	public Integer getUpdid() {
		return updid;
	}

	/**
	 * @param updid the updid to set
	 */
	public void setUpdid(Integer updid) {
		this.updid = updid;
	}

	/**
	 * @return the updidname
	 */
	public String getUpdidname() {
		return updidname;
	}

	/**
	 * @param updidname the updidname to set
	 */
	public void setUpdidname(String updidname) {
		this.updidname = updidname;
	}

	/**
	 * @return the upddate
	 */
	public String getUpddate() {
		return upddate;
	}

	/**
	 * @param upddate the upddate to set
	 */
	public void setUpddate(String upddate) {
		this.upddate = upddate;
	}
    
    
    
}
