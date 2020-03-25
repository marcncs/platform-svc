package com.winsafe.drp.dao;

import org.apache.struts.validator.ValidatorForm;

/**
 * @author jelli
 * 2009-4-1
 */
public class RepositoryForm extends ValidatorForm {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** identifier field */
    private String id;

    /** nullable persistent field */
    private Long rtid;
    
    private String rtidname;

    /** nullable persistent field */
    private String title;

    /** nullable persistent field */
    private String content;

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
    private String keyscontent;
    
    private String[] filetitle;


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
	 * @return the rtid
	 */
	public Long getRtid() {
		return rtid;
	}

	/**
	 * @param rtid the rtid to set
	 */
	public void setRtid(Long rtid) {
		this.rtid = rtid;
	}

	/**
	 * @return the rtidname
	 */
	public String getRtidname() {
		return rtidname;
	}

	/**
	 * @param rtidname the rtidname to set
	 */
	public void setRtidname(String rtidname) {
		this.rtidname = rtidname;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
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
	 * @return the keyscontent
	 */
	public String getKeyscontent() {
		return keyscontent;
	}

	/**
	 * @param keyscontent the keyscontent to set
	 */
	public void setKeyscontent(String keyscontent) {
		this.keyscontent = keyscontent;
	}

	/**
	 * @return the filetitle
	 */
	public String[] getFiletitle() {
		return filetitle;
	}

	/**
	 * @param filetitle the filetitle to set
	 */
	public void setFiletitle(String[] filetitle) {
		this.filetitle = filetitle;
	}
	
	
    
    
}
