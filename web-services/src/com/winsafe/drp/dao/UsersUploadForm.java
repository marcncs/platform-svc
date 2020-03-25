package com.winsafe.drp.dao;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class UsersUploadForm extends ActionForm {
	 private FormFile usrsfile;

	public FormFile getUsrsfile() {
		return usrsfile;
	}

	public void setUsrsfile(FormFile usrsfile) {
		this.usrsfile = usrsfile;
	}
	 
}
