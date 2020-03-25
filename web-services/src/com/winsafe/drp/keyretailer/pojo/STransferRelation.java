package com.winsafe.drp.keyretailer.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * STransferRelation entity. @author MyEclipse Persistence Tools
 */

public class STransferRelation implements java.io.Serializable {

	// Fields

	private Integer id;
	private String organizationId;
	private String oppOrganId;
	private String remark;
	private Date modifieDate;
	private Integer version;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public String getOppOrganId() {
		return oppOrganId;
	}
	public void setOppOrganId(String oppOrganId) {
		this.oppOrganId = oppOrganId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getModifieDate() {
		return modifieDate;
	}
	public void setModifieDate(Date modifieDate) {
		this.modifieDate = modifieDate;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	

	// Constructors

	
}