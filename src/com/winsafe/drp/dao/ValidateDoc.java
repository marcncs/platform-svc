package com.winsafe.drp.dao;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

public class ValidateDoc extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5733688405312771927L;

	/** identifier field */
	private String sortid;

	/** nullable persistent field */
	private String describe;

	private FormFile doc;

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	 

	public String getSortid() {
		return sortid;
	}

	public void setSortid(String sortid) {
		this.sortid = sortid;
	}

	public FormFile getDoc() {
		return doc;
	}

	public void setDoc(FormFile doc) {
		this.doc = doc;
	}

}
