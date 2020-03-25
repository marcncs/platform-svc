package com.winsafe.erp.action.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.dao.UsersBean;
import com.winsafe.sap.metadata.SapFileType;

/** 
 * @author ryan.xi
 * @version 2014-10-16 下午03:59:28 
 * www.winsafe.cn 
 */
public class SapCodeForm extends ActionForm {
    
    private FormFile sapFile;
    
    private String fileType;
    
    private String plantName;
    
    private String plantCode;
    
    private String plantType;

	public FormFile getSapFile() {
		return sapFile;
	}

	public void setSapFile(FormFile sapFile) {
		this.sapFile = sapFile;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public String getPlantCode() {
		return plantCode;
	}

	public void setPlantCode(String plantCode) {
		this.plantCode = plantCode;
	}

	public String getPlantType() {
		return plantType;
	}

	public void setPlantType(String plantType) {
		this.plantType = plantType;
	}

	
}
