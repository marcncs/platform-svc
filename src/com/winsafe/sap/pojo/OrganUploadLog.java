package com.winsafe.sap.pojo;

import java.util.Date; 

import com.winsafe.sap.metadata.SapUploadLogStatus;

public class OrganUploadLog {
	private Integer id;
	private Integer isDeal;
	private String fileName;
	private String filePath;
	private String logFilePath;
	private Integer errorCount;
	private Integer totalCount;
	private Integer makeId;
	private String makeOrganId;
	private Date makeDate;
	private String fileHaseCode;
	private Integer type;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public String getLogFilePath() {
		return logFilePath;
	}
	public void setLogFilePath(String logFilePath) {
		this.logFilePath = logFilePath;
	}
	public Integer getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
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
	public Date getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}
	public String getFileHaseCode() {
		return fileHaseCode;
	}
	public void setFileHaseCode(String fileHaseCode) {
		this.fileHaseCode = fileHaseCode;
	}
	public String getDealStatus() {
		SapUploadLogStatus status = SapUploadLogStatus.parse(isDeal);
		if(status != null) {
			return status.getDisplayName();
		}
		return "";
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}
