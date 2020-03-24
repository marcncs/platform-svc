package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;
public class Scanner implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String model;
	private String osVersion;
	private Long status;
	private Date installDate;
	private String appVersion;
	private Date appVerUpDate;
	private Long wHCode;
	private String scannerName;
	private Date lastUpDate;
	private String scannerImeiN;
	
	public  Scanner(){
		
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getModel() {
		return model;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getStatus() {
		return status;
	}

	public void setInstallDate(Date installDate) {
		this.installDate = installDate;
	}

	public Date getInstallDate() {
		return installDate;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVerUpDate(Date appVerUpDate) {
		this.appVerUpDate = appVerUpDate;
	}

	public Date getAppVerUpDate() {
		return appVerUpDate;
	}

	public void setwHCode(Long wHCode) {
		this.wHCode = wHCode;
	}

	public Long getwHCode() {
		return wHCode;
	}

	public void setScannerName(String scannerName) {
		this.scannerName = scannerName;
	}

	public String getScannerName() {
		return scannerName;
	}

	public void setLastUpDate(Date lastUpDate) {
		this.lastUpDate = lastUpDate;
	}

	public Date getLastUpDate() {
		return lastUpDate;
	}

	public void setScannerImeiN(String scannerImeiN) {
		this.scannerImeiN = scannerImeiN;
	}

	public String getScannerImeiN() {
		return scannerImeiN;
	}

}
