package com.winsafe.drp.dao;

import java.util.Date;

public class StuffShipmentBillForm {
	/** identifier field */
    private String id;

    /** nullable persistent field */
    private Long warehouseid;
    
    private String warehouseidname;

    /** nullable persistent field */
    private Integer shipmentsort;
    
    private String shipmentsortname;

    /** nullable persistent field */
    private Long shipmentdept;
    
    private String shipmentdeptname;

    /** nullable persistent field */
    private String requiredate;

    /** nullable persistent field */
    private Double totalsum;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Integer isrefer;
    
    private String isrefername;

    /** nullable persistent field */
    private Integer approvestatus;
    
    private String approvestatusname;

    /** nullable persistent field */
    private String approvedate;
    
    private Integer actid;
    
    private String actidname;

    /** nullable persistent field */
    private Integer isaudit;
    
    private String isauditname;

    /** nullable persistent field */
    private Long auditid;
    
    private String auditidname;

    /** nullable persistent field */
    private Date auditdate;

    /** nullable persistent field */
    private Long makeid;
    
    private String makeidname;

    /** nullable persistent field */
    private Date makedate;

	public String getApprovedate() {
		return approvedate;
	}

	public void setApprovedate(String approvedate) {
		this.approvedate = approvedate;
	}

	public Integer getApprovestatus() {
		return approvestatus;
	}

	public void setApprovestatus(Integer approvestatus) {
		this.approvestatus = approvestatus;
	}

	public Date getAuditdate() {
		return auditdate;
	}

	public void setAuditdate(Date auditdate) {
		this.auditdate = auditdate;
	}

	public Long getAuditid() {
		return auditid;
	}

	public void setAuditid(Long auditid) {
		this.auditid = auditid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getIsaudit() {
		return isaudit;
	}

	public void setIsaudit(Integer isaudit) {
		this.isaudit = isaudit;
	}

	public Integer getIsrefer() {
		return isrefer;
	}

	public void setIsrefer(Integer isrefer) {
		this.isrefer = isrefer;
	}

	public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

	public Long getMakeid() {
		return makeid;
	}

	public void setMakeid(Long makeid) {
		this.makeid = makeid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRequiredate() {
		return requiredate;
	}

	public void setRequiredate(String requiredate) {
		this.requiredate = requiredate;
	}

	public Long getShipmentdept() {
		return shipmentdept;
	}

	public void setShipmentdept(Long shipmentdept) {
		this.shipmentdept = shipmentdept;
	}

	public Integer getShipmentsort() {
		return shipmentsort;
	}

	public void setShipmentsort(Integer shipmentsort) {
		this.shipmentsort = shipmentsort;
	}

	public Double getTotalsum() {
		return totalsum;
	}

	public void setTotalsum(Double totalsum) {
		this.totalsum = totalsum;
	}

	public Long getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(Long warehouseid) {
		this.warehouseid = warehouseid;
	}

	public String getApprovestatusname() {
		return approvestatusname;
	}

	public void setApprovestatusname(String approvestatusname) {
		this.approvestatusname = approvestatusname;
	}

	public String getAuditidname() {
		return auditidname;
	}

	public void setAuditidname(String auditidname) {
		this.auditidname = auditidname;
	}

	public String getIsauditname() {
		return isauditname;
	}

	public void setIsauditname(String isauditname) {
		this.isauditname = isauditname;
	}

	public String getIsrefername() {
		return isrefername;
	}

	public void setIsrefername(String isrefername) {
		this.isrefername = isrefername;
	}

	public String getMakeidname() {
		return makeidname;
	}

	public void setMakeidname(String makeidname) {
		this.makeidname = makeidname;
	}

	public String getShipmentdeptname() {
		return shipmentdeptname;
	}

	public void setShipmentdeptname(String shipmentdeptname) {
		this.shipmentdeptname = shipmentdeptname;
	}

	public String getShipmentsortname() {
		return shipmentsortname;
	}

	public void setShipmentsortname(String shipmentsortname) {
		this.shipmentsortname = shipmentsortname;
	}

	public String getWarehouseidname() {
		return warehouseidname;
	}

	public void setWarehouseidname(String warehouseidname) {
		this.warehouseidname = warehouseidname;
	}

	public Integer getActid() {
		return actid;
	}

	public void setActid(Integer actid) {
		this.actid = actid;
	}

	public String getActidname() {
		return actidname;
	}

	public void setActidname(String actidname) {
		this.actidname = actidname;
	}
}
