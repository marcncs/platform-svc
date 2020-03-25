package com.winsafe.drp.dao;

import java.util.Date;


public class HapForm {
	/** identifier field */
    private Integer id;

    private Integer objsort;

	/** nullable persistent field */
    private String cid;
    
    private String cname;
    
    private Integer haptitle;

    /** nullable persistent field */
    private Integer hapcontent;
    

    /** nullable persistent field */
    private Integer hapkind;

    /** nullable persistent field */
    private Integer hapstatus;

    /** nullable persistent field */
    private Double intend;
    
    private Date IntendDate;

    /** nullable persistent field */
    private Date hapbegin;

    /** nullable persistent field */
    private Date hapend;

    /** nullable persistent field */
    private String memo;
    
    /** nullable persistent field */
    private Integer makeid;
    
    private String makeorganid;
    
    private Integer makedeptid;

    /** nullable persistent field */
    private Date makedate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getObjsort() {
		return objsort;
	}

	public void setObjsort(Integer objsort) {
		this.objsort = objsort;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public Integer getHaptitle() {
		return haptitle;
	}

	public void setHaptitle(Integer haptitle) {
		this.haptitle = haptitle;
	}

	public Integer getHapcontent() {
		return hapcontent;
	}

	public void setHapcontent(Integer hapcontent) {
		this.hapcontent = hapcontent;
	}

	public Integer getHapkind() {
		return hapkind;
	}

	public void setHapkind(Integer hapkind) {
		this.hapkind = hapkind;
	}

	public Integer getHapstatus() {
		return hapstatus;
	}

	public void setHapstatus(Integer hapstatus) {
		this.hapstatus = hapstatus;
	}

	public Double getIntend() {
		return intend;
	}

	public void setIntend(Double intend) {
		this.intend = intend;
	}

	public Date getIntendDate() {
		return IntendDate;
	}

	public void setIntendDate(Date intendDate) {
		IntendDate = intendDate;
	}

	public Date getHapbegin() {
		return hapbegin;
	}

	public void setHapbegin(Date hapbegin) {
		this.hapbegin = hapbegin;
	}

	public Date getHapend() {
		return hapend;
	}

	public void setHapend(Date hapend) {
		this.hapend = hapend;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getMakeid() {
		return makeid;
	}

	public void setMakeid(Integer makeid) {
		this.makeid = makeid;
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

	public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}
    
    

	
}
