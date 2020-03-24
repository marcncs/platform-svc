package com.winsafe.drp.dao;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/** 
 * @author jerry
 * @version 2009-8-18 下午03:59:28 
 * www.winsafe.cn 
 */
public class IdcodeUploadForm extends ActionForm {
	/** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String filename;

    /** nullable persistent field */
    private Integer billsort;

    /** nullable persistent field */
    private Integer isdeal;

    /** nullable persistent field */
    private Integer valinum;

    /** nullable persistent field */
    private Integer failnum;

    /** nullable persistent field */
    private String failfilepath;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private String makedate;
    
    private FormFile idcodefile;
    
    private String type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Integer getBillsort() {
		return billsort;
	}

	public void setBillsort(Integer billsort) {
		this.billsort = billsort;
	}

	public Integer getIsdeal() {
		return isdeal;
	}

	public void setIsdeal(Integer isdeal) {
		this.isdeal = isdeal;
	}

	public Integer getValinum() {
		return valinum;
	}

	public void setValinum(Integer valinum) {
		this.valinum = valinum;
	}

	public Integer getFailnum() {
		return failnum;
	}

	public void setFailnum(Integer failnum) {
		this.failnum = failnum;
	}

	public String getFailfilepath() {
		return failfilepath;
	}

	public void setFailfilepath(String failfilepath) {
		this.failfilepath = failfilepath;
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

	public String getMakedate() {
		return makedate;
	}

	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}

	public FormFile getIdcodefile() {
		return idcodefile;
	}

	public void setIdcodefile(FormFile idcodefile) {
		this.idcodefile = idcodefile;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
    
    
}
