package com.winsafe.sap.pojo;

import java.util.Date;

public class ApplyQrCode {
	private Integer id; 
	private String organId; 
	private String productId; 
	private Integer quantity;
	private Integer redundance;
	//1.未生成2生成中3已生成4生成失败
	private Integer status;
	private String filePath;
	private Integer isAudit;
	private Integer makeId;
	private Date makeDate;
	private Integer auditId;
	private Date auditDate;
	private Integer version;
	private String pono;
	//是否需要暗码
	private Integer needCovertCode;  


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Integer getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(Integer isAudit) {
		this.isAudit = isAudit;
	}

	public Integer getMakeId() {
		return makeId;
	}

	public void setMakeId(Integer makeId) {
		this.makeId = makeId;
	}

	public Date getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}

	public Integer getAuditId() {
		return auditId;
	}

	public void setAuditId(Integer auditId) {
		this.auditId = auditId;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getRedundance() {
		return redundance;
	}

	public void setRedundance(Integer redundance) {
		this.redundance = redundance;
	}

	public String getPono() {
		return pono;
	}

	public void setPono(String pono) {
		this.pono = pono;
	}

	public Integer getNeedCovertCode() {
		return needCovertCode;
	}

	public void setNeedCovertCode(Integer needCovertCode) {
		this.needCovertCode = needCovertCode;
	}

}
