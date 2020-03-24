package com.winsafe.control.pojo;

import java.io.Serializable;

import com.winsafe.drp.metadata.ServiceExpType; 


public class ResponseData implements Serializable {

	private static final long serialVersionUID = 7683053520250528998L;

	public ResponseData() {
	}

	public ResponseData(ServiceExpType enumType) {
		this.setCode(enumType.getCode());
		this.setMsg(enumType.getDesc());
	}

	public ResponseData(String code, String msg) {
		this.setCode(code);
		this.setMsg(msg);
	}
	
	public ResponseData(Object data) {
		this.data = data;
	}

	private String code;
	
	private String msg;

	private java.lang.Object data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public java.lang.Object getData() {
		return data;
	}

	public ResponseData setData(java.lang.Object data) {
		this.data = data;
		return this;
	}
}