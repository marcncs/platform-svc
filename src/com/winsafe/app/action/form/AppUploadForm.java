package com.winsafe.app.action.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class AppUploadForm extends ActionForm {
	private FormFile appFile;

	private String appVersion;

	private String publishName;
	
	private String updateLog;
	public FormFile getAppFile() {
		return appFile;
	}

	public void setAppFile(FormFile appFile) {
		this.appFile = appFile;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getPublishName() {
		return publishName;
	}

	public void setPublishName(String publishName) {
		this.publishName = publishName;
	}

	public String getUpdateLog() {
		return updateLog;
	}

	public void setUpdateLog(String updateLog) {
		this.updateLog = updateLog;
	}

}
