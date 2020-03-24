package com.winsafe.drp.dao;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/** 
 * @author jerry
 * @version 2009-8-18 下午03:59:28 
 * www.winsafe.cn 
 */
public class ImgUploadForm extends ActionForm {
	private FormFile idcodefile;
	public FormFile getIdcodefile() {
		return idcodefile;
	}
	public void setIdcodefile(FormFile idcodefile) {
		this.idcodefile = idcodefile;
	}
    
    
}
