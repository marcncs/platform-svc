package com.winsafe.drp.dao;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class UpkeyForm extends ActionForm {

	private static final long serialVersionUID = 5422245060554435056L;
	private FormFile keyfile;
	private String filepath;

	

	public FormFile getKeyfile() {
		return keyfile;
	}

	public void setKeyfile(FormFile keyfile) {
		this.keyfile = keyfile;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

}
