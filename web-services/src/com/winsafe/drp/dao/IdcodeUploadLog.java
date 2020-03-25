package com.winsafe.drp.dao;

import java.util.Date;

import com.winsafe.drp.metadata.IdcodeErrorType;

public class IdcodeUploadLog {
	private Long id;
	//上传单据号
	private String billNo;
	//上传日期
	private Date uploadDate;
	//上传人
	private Integer uploadUser;
	//错误信息
	private String errMsg;
	//对应条码上传日志编号
	private Integer idcodeUploadId;
	//上传单据类型
	private Integer bsort;
	//上传机构
	private String uploadOrganId;
	//上传仓库
	private String uploadWarehouseId;
	//错误代码
	private String errCode;
	//上传条码
	private String idcode;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public Integer getUploadUser() {
		return uploadUser;
	}
	public void setUploadUser(Integer uploadUser) {
		this.uploadUser = uploadUser;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public Integer getIdcodeUploadId() {
		return idcodeUploadId;
	}
	public void setIdcodeUploadId(Integer idcodeUploadId) {
		this.idcodeUploadId = idcodeUploadId;
	}
	public Integer getBsort() {
		return bsort;
	}
	public void setBsort(Integer bsort) {
		this.bsort = bsort;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getIdcode() {
		return idcode;
	}
	public void setIdcode(String idcode) {
		this.idcode = idcode;
	}
	public String getUploadOrganId() {
		return uploadOrganId;
	}
	public void setUploadOrganId(String uploadOrganId) {
		this.uploadOrganId = uploadOrganId;
	}
	public String getUploadWarehouseId() {
		return uploadWarehouseId;
	}
	public void setUploadWarehouseId(String uploadWarehouseId) {
		this.uploadWarehouseId = uploadWarehouseId;
	}
	public Integer getErrCodeInt() {
		IdcodeErrorType idcodeErrorType = IdcodeErrorType.parseByDBValue(errCode);
		if(idcodeErrorType != null) {
			return idcodeErrorType.getIntValue();
		} else {
			return null;
		}
		
	}
}
