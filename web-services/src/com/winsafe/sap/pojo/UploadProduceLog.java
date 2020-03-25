package com.winsafe.sap.pojo;

import java.util.Date;

import com.winsafe.sap.metadata.SapUploadLogStatus;

public class UploadProduceLog {
	private Integer id;
	private Integer isDeal;
	private String fileName; 
	private String filePath;
	private String logFilePath;
	private Integer errorCount;
	private Integer totalCount;
	private String fileType;
	private Integer makeId;
	private String makeOrganId;
	private Integer makeDeptId;
	private Date makeDate;
	private String fileHaseCode;
	private Integer printJobId;

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

	public Integer getMakeId() {
		return makeId;
	}

	public void setMakeId(Integer makeId) {
		this.makeId = makeId;
	}

	public Integer getMakeDeptId() {
		return makeDeptId;
	}

	public void setMakeDeptId(Integer makeDeptId) {
		this.makeDeptId = makeDeptId;
	}
	
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
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

	public Integer getPrintJobId() {
		return printJobId;
	}

	public void setPrintJobId(Integer printJobId) {
		this.printJobId = printJobId;
	}
}
