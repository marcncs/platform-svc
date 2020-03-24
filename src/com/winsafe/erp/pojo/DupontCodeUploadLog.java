package com.winsafe.erp.pojo;

import java.util.Date;

public class DupontCodeUploadLog {
	private Integer id;
	private String fileName;
	private String filePath;
	private String logFilePath;
	private Integer errorCount;
	private Integer totalCount;
	private Integer makeId;
	private String makeOrganId;
	private Date makeDate;
	private String fileHashCode;
	private String codeFilePath;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getFileHashCode() {
		return fileHashCode;
	}
	public void setFileHashCode(String fileHashCode) {
		this.fileHashCode = fileHashCode;
	}
	public String getCodeFilePath() {
		return codeFilePath;
	}
	public void setCodeFilePath(String codeFilePath) {
		this.codeFilePath = codeFilePath;
	}

}
