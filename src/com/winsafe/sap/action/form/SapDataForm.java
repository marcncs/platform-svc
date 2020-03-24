package com.winsafe.sap.action.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;


/** 
 * @author ryan.xi
 * @version 2014-10-16 下午03:59:28 
 * www.winsafe.cn 
 */
public class SapDataForm extends ActionForm {

	private String username;
    
    private String password;
    //order—订单数据，delivery—为发货数据， invoice—为发票数据
    private String type;
    
    private FormFile filestream;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public FormFile getFilestream() {
		return filestream;
	}

	public void setFilestream(FormFile filestream) {
		this.filestream = filestream;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
