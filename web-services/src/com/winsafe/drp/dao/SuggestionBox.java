package com.winsafe.drp.dao;

import java.util.Date;

public class SuggestionBox {

	private String id;
	private String suggestionMsg;
	private Date makeDate;
	private String ip;
	private String imeiNumber;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSuggestionMsg() {
		return suggestionMsg;
	}
	public void setSuggestionMsg(String suggestionMsg) {
		this.suggestionMsg = suggestionMsg;
	}
	public Date getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getImeiNumber() {
		return imeiNumber;
	}
	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}
	
	
}
