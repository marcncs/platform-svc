package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date; 


public class Manufacturer implements Serializable {

	private static final long serialVersionUID = -5020542426960660041L;

	private Integer id;

	private String fullName;

	private String shortName;

	//enum
	private Integer auditStatus;
	
	//enum
	private Integer certifyStatus;
	
	private String address;

	private String tel;
	
	private String businessLicenseNo;
	
	private String organizationNo;
	
	private String businessLicenseCopyPic;
	
	private String contactName;
	
	private String contactPhone;
	
	private Date createTime;

	private Date lastModifyTime;
	
	private Integer lvl;
	
	private Date serviceExpiredTime;
	
	private Boolean isDeleted;

	private Date deleteTime;
	
	/** 逻辑删除 */
	public void delete() {
		this.setIsDeleted(true);
		this.setDeleteTime(new Date());
		this.setLastModifyTime(new Date());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Integer getCertifyStatus() {
		return certifyStatus;
	}

	public void setCertifyStatus(Integer certifyStatus) {
		this.certifyStatus = certifyStatus;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public String getBusinessLicenseNo() {
		return businessLicenseNo;
	}

	public void setBusinessLicenseNo(String businessLicenseNo) {
		this.businessLicenseNo = businessLicenseNo;
	}

	public String getOrganizationNo() {
		return organizationNo;
	}

	public void setOrganizationNo(String organizationNo) {
		this.organizationNo = organizationNo;
	}

	public String getBusinessLicenseCopyPic() {
		return businessLicenseCopyPic;
	}

	public void setBusinessLicenseCopyPic(String businessLicenseCopyPic) {
		this.businessLicenseCopyPic = businessLicenseCopyPic;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}


	public Date getServiceExpiredTime() {
		return serviceExpiredTime;
	}

	public void setServiceExpiredTime(Date serviceExpiredTime) {
		this.serviceExpiredTime = serviceExpiredTime;
	}

	public Integer getLvl() {
		return lvl;
	}

	public void setLvl(Integer lvl) {
		this.lvl = lvl;
	}
}
