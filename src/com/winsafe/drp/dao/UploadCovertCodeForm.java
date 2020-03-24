package com.winsafe.drp.dao;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/** 
 * @author ryan
 * @version 2015-2-27 下午03:59:28 
 * www.winsafe.cn 
 */
public class UploadCovertCodeForm extends ActionForm {
	
    private FormFile filestream;

	public FormFile getFilestream() {
		return filestream;
	}

	public void setFilestream(FormFile filestream) {
		this.filestream = filestream;
	}
    
}
