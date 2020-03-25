package com.winsafe.drp.packseparate.pojo;

import java.util.Date;

public class PackSeparate {
	
	private String id;
	private String organId;
	private String warehouseId;
	private Integer isAudit;
	private Date auditDate;
	private Integer auditId;
	private Integer makeId;
	private String makeOrganId;
	private Date makeDate;
	private String remark;
	private Integer isAccount;
	private Date billDate;
	private String keyContent;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public String getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	public Integer getIsAudit() {
		return isAudit;
	}
	public void setIsAudit(Integer isAudit) {
		this.isAudit = isAudit;
	}
	public Date getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	public Integer getAuditId() {
		return auditId;
	}
	public void setAuditId(Integer auditId) {
		this.auditId = auditId;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getIsAccount() {
		return isAccount;
	}
	public void setIsAccount(Integer isAccount) {
		this.isAccount = isAccount;
	}
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public String getKeyContent() {
		return keyContent;
	}
	public void setKeyContent(String keyContent) {
		this.keyContent = keyContent;
	}
	
}
