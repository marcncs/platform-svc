package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

public class TrackApply implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String applyOrgId;
	private int applyUserId;
	private String idCode;
	private int codeType;
	private Date createDate;
	private int status;
	private String remark;
	private String errorMsg;
	
	public  TrackApply(){
		
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}


	public void setApplyUserId(int applyUserId) {
		this.applyUserId = applyUserId;
	}

	public int getApplyUserId() {
		return applyUserId;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setCodeType(int codeType) {
		this.codeType = codeType;
	}

	public int getCodeType() {
		return codeType;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setApplyOrgId(String applyOrgId) {
		this.applyOrgId = applyOrgId;
	}

	public String getApplyOrgId() {
		return applyOrgId;
	}
}
