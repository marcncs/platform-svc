package com.winsafe.drp.dao;

import java.util.Date;

public class CorrelationDocumentForm {
	/** identifier field */
    private Integer id;

    /** persistent field */
    private String cid;
    
    private Integer objsort;
    
    private String cname;
    

    
    private String clienttypename;

    /** nullable persistent field */
    private String documentname;

    /** nullable persistent field */
    private String realpathname;
    
    private String makeorganid;

    /** nullable persistent field */
    private Date makedate;

    /** persistent field */
    private Integer makeid;
    
    private String makeidname;
    
    private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public Integer getObjsort() {
		return objsort;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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


	public String getClienttypename() {
		return clienttypename;
	}

	public void setClienttypename(String clienttypename) {
		this.clienttypename = clienttypename;
	}

	public String getDocumentname() {
		return documentname;
	}

	public void setDocumentname(String documentname) {
		this.documentname = documentname;
	}

	public String getRealpathname() {
		return realpathname;
	}

	public void setRealpathname(String realpathname) {
		this.realpathname = realpathname;
	}

	public String getMakeorganid() {
		return makeorganid;
	}

	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
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

	public String getMakeidname() {
		return makeidname;
	}

	public void setMakeidname(String makeidname) {
		this.makeidname = makeidname;
	}
    
    

}