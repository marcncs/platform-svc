package com.winsafe.drp.dao;

import org.apache.struts.action.ActionForm;

/**
 * @author jelli
 * 2009-5-19
 */
public class ViewSaleBillForm extends ActionForm {
	  /** nullable persistent field */
    private String id;

    /** nullable persistent field */
    private String makeorganid;
    
    private String makeorganidname;

    /** nullable persistent field */
    private Integer makeid;
    
    private String makeidname;

    /** nullable persistent field */
    private String makedate;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private String cmobile;

    /** nullable persistent field */
    private Double totalsum;

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
	 * @return the cid
	 */
	public String getCid() {
		return cid;
	}

	/**
	 * @param cid the cid to set
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}

	/**
	 * @return the cname
	 */
	public String getCname() {
		return cname;
	}

	/**
	 * @param cname the cname to set
	 */
	public void setCname(String cname) {
		this.cname = cname;
	}

	/**
	 * @return the cmobile
	 */
	public String getCmobile() {
		return cmobile;
	}

	/**
	 * @param cmobile the cmobile to set
	 */
	public void setCmobile(String cmobile) {
		this.cmobile = cmobile;
	}

	/**
	 * @return the totalsum
	 */
	public Double getTotalsum() {
		return totalsum;
	}

	/**
	 * @param totalsum the totalsum to set
	 */
	public void setTotalsum(Double totalsum) {
		this.totalsum = totalsum;
	}

	public void setMakeid(Integer makeid) {
		this.makeid = makeid;
	}

	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
	}
    
    
}
