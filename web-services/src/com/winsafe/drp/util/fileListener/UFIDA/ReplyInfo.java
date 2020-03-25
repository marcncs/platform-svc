package com.winsafe.drp.util.fileListener.UFIDA;

public class ReplyInfo {
	//用于保存保存对象时的报错信息
	private String errorInfo;
	//用于保存是否保存对象成功的信息
	private Boolean saveFlag;
	public String getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	public Boolean getSaveFlag() {
		return saveFlag;
	}
	public void setSaveFlag(Boolean saveFlag) {
		this.saveFlag = saveFlag;
	}
	
	
	
}
