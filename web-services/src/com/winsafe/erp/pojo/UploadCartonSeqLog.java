package com.winsafe.erp.pojo;

import java.util.Date;

import com.winsafe.sap.metadata.SapUploadLogStatus;

public class UploadCartonSeqLog {
	private Long id;
	private Integer isDeal;
	private String fileName; 
	private String filePath;
	private String logFilePath;
	private Integer errorCount;
	private Integer totalCount;
	private Integer makeId;
	private Date makeDate;
	private String fileHaseCode;
	private String plNo;
	
	public String getPlNo() {
		return plNo;
	}

	public void setPlNo(String plNo) {
		this.plNo = plNo;
	}

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
	
	public Date getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}

	public Integer getMakeId() {
		return makeId;
	}

	public void setMakeId(Integer makeId) {
		this.makeId = makeId;
	}

	public String getStatus() {
		SapUploadLogStatus status = SapUploadLogStatus.parse(isDeal);
		if(status != null) {
			return status.getDisplayName();
		}
		return "";
	}
	
	public String getFileNameWithNoExt() {
		return fileName.substring(0, fileName.indexOf(".xml"));
	}

	public String getFileHaseCode() {
		return fileHaseCode;
	}

	public void setFileHaseCode(String fileHaseCode) {
		this.fileHaseCode = fileHaseCode;
	}

}
