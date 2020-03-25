package com.winsafe.drp.dao;

import com.winsafe.drp.metadata.ErrorType;

public class ErrorBean { 

	private String idcode;
	private String info;
	private String errCode;
	private String type;
	private ErrorType errType;
	private String proName;
	private String specMode;
	private Integer lineNo;

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getIdcode() {
		return idcode;
	}

	public void setIdcode(String idcode) {
		this.idcode = idcode;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ErrorType getErrType() {
		return errType;
	}

	public void setErrType(ErrorType errType) {
		this.errType = errType;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getSpecMode() {
		return specMode;
	}

	public void setSpecMode(String specMode) {
		this.specMode = specMode;
	}

	public Integer getLineNo() {
		return lineNo;
	}

	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}

}
