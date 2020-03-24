package com.winsafe.drp.dao;

import java.util.Date;

public class InventoryUploadLog {
	private Long id;
	private Integer isDeal;
	private String fileName;
	private String filePath;
	private Integer makeId;
	private String makeOrganId;
	private Integer makeDeptId;
	private Date makeDate;
	private String logFilePath;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getIsDeal() {
		return isDeal;
	}
	public void setIsDeal(Integer isDeal) {
		this.isDeal = isDeal;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Integer getMakeId() {
		return makeId;
	}
	public void setMakeId(Integer makeId) {
		this.makeId = makeId;
	}
	public String getMakeOrganId() {
		return makeOrganId;
	}
	public void setMakeOrganId(String makeOrganId) {
		this.makeOrganId = makeOrganId;
	}
	public Integer getMakeDeptId() {
		return makeDeptId;
	}
	public void setMakeDeptId(Integer makeDeptId) {
		this.makeDeptId = makeDeptId;
	}
	public Date getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}
	public String getLogFilePath() {
		return logFilePath;
	}
	public void setLogFilePath(String logFilePath) {
		this.logFilePath = logFilePath;
	}
}
