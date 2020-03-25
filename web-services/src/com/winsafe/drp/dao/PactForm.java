package com.winsafe.drp.dao;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class PactForm extends ActionForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    /** nullable persistent field */
    private String pactcode;

    /** nullable persistent field */
    private Integer pacttype;

    /** nullable persistent field */
    private Integer userid;
    
    private Integer objsort;

    /** persistent field */
    private String cid;
    
    private String cname;

    /** nullable persistent field */
    private String cdeputy;

    /** nullable persistent field */
    private Date signdate;

    /** nullable persistent field */
    private String provide;

    /** nullable persistent field */
    private String pdeputy;

    /** nullable persistent field */
    private String signaddr;

    /** nullable persistent field */
    private String pactscopy;
    
    private String affix;
    
    private String makeorganid;
    public Integer getObjsort() {
		return objsort;
	}

	public void setObjsort(Integer objsort) {
		this.objsort = objsort;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

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

	public Integer getMakeid() {
		return makeid;
	}

	public void setMakeid(Integer makeid) {
		this.makeid = makeid;
	}

	public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

	private Integer makedeptid;
    private Integer makeid;
    private Date makedate;


	public String getAffix() {
		return affix;
	}

	public void setAffix(String affix) {
		this.affix = affix;
	}

	public String getCdeputy() {
		return cdeputy;
	}

	public void setCdeputy(String cdeputy) {
		this.cdeputy = cdeputy;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPactcode() {
		return pactcode;
	}

	public void setPactcode(String pactcode) {
		this.pactcode = pactcode;
	}

	public String getPactscopy() {
		return pactscopy;
	}

	public void setPactscopy(String pactscopy) {
		this.pactscopy = pactscopy;
	}

	public Integer getPacttype() {
		return pacttype;
	}

	public void setPacttype(Integer pacttype) {
		this.pacttype = pacttype;
	}

	public String getPdeputy() {
		return pdeputy;
	}

	public void setPdeputy(String pdeputy) {
		this.pdeputy = pdeputy;
	}

	public String getProvide() {
		return provide;
	}

	public void setProvide(String provide) {
		this.provide = provide;
	}

	public String getSignaddr() {
		return signaddr;
	}

	public void setSignaddr(String signaddr) {
		this.signaddr = signaddr;
	}

	public Date getSigndate() {
		return signdate;
	}

	public void setSigndate(Date signdate) {
		this.signdate = signdate;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	
}
