package com.winsafe.drp.dao;

import java.util.Date;

public class OtherIncomeForm {
	/** identifier field */
    private String id;

    /** persistent field */
    private Long warehouseid;
    
    private String warehouseidname;

    /** nullable persistent field */
    private Integer incomesort;
    
    private String incomesortname;
    
    private String billno;

    /** nullable persistent field */
    private Long incomedept;
    
    private String incomedeptname;

    /** nullable persistent field */
    private String incomedate;

    /** nullable persistent field */
    private String remark;

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

	public String getAuditidname() {
		return auditidname;
	}

	public void setAuditidname(String auditidname) {
		this.auditidname = auditidname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIncomedate() {
		return incomedate;
	}

	public void setIncomedate(String incomedate) {
		this.incomedate = incomedate;
	}

	public Long getIncomedept() {
		return incomedept;
	}

	public void setIncomedept(Long incomedept) {
		this.incomedept = incomedept;
	}

	public String getIncomedeptname() {
		return incomedeptname;
	}

	public void setIncomedeptname(String incomedeptname) {
		this.incomedeptname = incomedeptname;
	}

	public Integer getIncomesort() {
		return incomesort;
	}

	public void setIncomesort(Integer incomesort) {
		this.incomesort = incomesort;
	}

	public String getIncomesortname() {
		return incomesortname;
	}

	public void setIncomesortname(String incomesortname) {
		this.incomesortname = incomesortname;
	}

	public Integer getIsaudit() {
		return isaudit;
	}

	public void setIsaudit(Integer isaudit) {
		this.isaudit = isaudit;
	}

	public String getIsauditname() {
		return isauditname;
	}

	public void setIsauditname(String isauditname) {
		this.isauditname = isauditname;
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

	public String getMakeidname() {
		return makeidname;
	}

	public void setMakeidname(String makeidname) {
		this.makeidname = makeidname;
	}


	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(Long warehouseid) {
		this.warehouseid = warehouseid;
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

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}
}
