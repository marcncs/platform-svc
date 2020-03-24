package com.winsafe.drp.keyretailer.util;

public class CodeAndTime {
	private String code;
	private long time;
	
	public CodeAndTime() { 
	}
	public CodeAndTime(String code, long time) {
		this.code = code;
		this.time = time;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
}
