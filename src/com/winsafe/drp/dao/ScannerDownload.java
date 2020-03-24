package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;
public class ScannerDownload implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	//采集器编号
	private String imei;
	//更新信息的md5值
	private String downloadMd5;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getDownloadMd5() {
		return downloadMd5;
	}
	public void setDownloadMd5(String downloadMd5) {
		this.downloadMd5 = downloadMd5;
	}
}
