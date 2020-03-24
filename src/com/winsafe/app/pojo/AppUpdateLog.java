package com.winsafe.app.pojo;

import java.util.Date;

public class AppUpdateLog {
	private long id;
	private String appId;
	private String scannerImeiN;
	private String appVersion;
	private String appName;
	private String appType;
	private Date appVerUpDate;
	private String scannerName;
	
	public AppUpdateLog() {}
	
	public AppUpdateLog(String scannerImeiN, String appVersion, String appName,
			Date appVerUpDate) {
		super();
		this.scannerImeiN = scannerImeiN;
		this.appVersion = appVersion;
		this.appName = appName;
		this.appVerUpDate = appVerUpDate;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getScannerName() {
		return scannerName;
	}
	public void setScannerName(String scannerName) {
		this.scannerName = scannerName;
	}
	public String getScannerImeiN() {
		return scannerImeiN;
	}
	public void setScannerImeiN(String scannerImeiN) {
		this.scannerImeiN = scannerImeiN;
	}
	public Date getAppVerUpDate() {
		return appVerUpDate;
	}
	public void setAppVerUpDate(Date appVerUpDate) {
		this.appVerUpDate = appVerUpDate;
	}
	
}
