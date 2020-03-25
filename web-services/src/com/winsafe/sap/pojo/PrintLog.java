package com.winsafe.sap.pojo;

import java.util.Date;

public class PrintLog {
	private Integer printLogId;
	private Date printDate;
	private Integer printUser;
	private Integer printJobId;
	private String printContent;
	private String printReason;
	private Integer printFlag;
	private String printlang;
	public Integer getPrintLogId() {
		return printLogId;
	}
	public void setPrintLogId(Integer printLogId) {
		this.printLogId = printLogId;
	}
	public Date getPrintDate() {
		return printDate;
	}
	public void setPrintDate(Date printDate) {
		this.printDate = printDate;
	}
	public Integer getPrintUser() {
		return printUser;
	}
	public void setPrintUser(Integer printUser) {
		this.printUser = printUser;
	}
	public Integer getPrintJobId() {
		return printJobId;
	}
	public void setPrintJobId(Integer printJobId) {
		this.printJobId = printJobId;
	}
	public String getPrintContent() {
		return printContent;
	}
	public void setPrintContent(String printContent) {
		this.printContent = printContent;
	}
	public String getPrintReason() {
		return printReason;
	}
	public void setPrintReason(String printReason) {
		this.printReason = printReason;
	}
	public Integer getPrintFlag() {
		return printFlag;
	}
	public void setPrintFlag(Integer printFlag) {
		this.printFlag = printFlag;
	}
	public String getPrintlang() {
		return printlang;
	}
	public void setPrintlang(String printlang) {
		this.printlang = printlang;
	}
	
}
