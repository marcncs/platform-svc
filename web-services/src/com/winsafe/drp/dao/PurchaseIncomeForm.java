package com.winsafe.drp.dao;

import java.util.Date;

public class PurchaseIncomeForm {
	 /** identifier field */
    private String id;

    /** nullable persistent field */
    private Long warehouseid;
    
    private String warehouseidname;

    /** nullable persistent field */
    private String poid;

    /** nullable persistent field */
    private String provideid;

    /** nullable persistent field */
    private String providename;

    /** nullable persistent field */
    private String plinkman;

    /** nullable persistent field */
    private String tel;
    
    private Integer paymode;
    
    private String paymodename;

    /** nullable persistent field */
    private String batch;

    /** nullable persistent field */
    private Integer incomesort;
    
    private String incomesortname;

    /** nullable persistent field */
    private String incomedate;

    /** nullable persistent field */
    private Double totalsum;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Integer isaudit;
    
    private String isauditname;

    /** nullable persistent field */
    private Long auditid;
    
    private String auditidname;

    /** nullable persistent field */
    private String auditdate;
    
    /** nullable persistent field */
    private Integer istally;
    
    private String istallyname;

    /** nullable persistent field */
    private Long tallyid;
    
    private String tallyidname;

    /** nullable persistent field */
    private Date tallydate;
    
    /** nullable persistent field */
    private String makeorganid;
    
    private String makeorganidname;

    /** nullable persistent field */
    private Long makedeptid;
    
    private String makedeptidname;

    /** nullable persistent field */
    private Long makeid;
    
    private String makeidname;

    /** nullable persistent field */
    private String makedate;

	public String getAuditdate() {
		return auditdate;
	}

	public void setAuditdate(String auditdate) {
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

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
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

	public String getMakedate() {
		return makedate;
	}

	public void setMakedate(String makedate) {
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

	public String getPlinkman() {
		return plinkman;
	}

	public void setPlinkman(String plinkman) {
		this.plinkman = plinkman;
	}

	public String getPoid() {
		return poid;
	}

	public void setPoid(String poid) {
		this.poid = poid;
	}

	public String getProvideid() {
		return provideid;
	}

	public void setProvideid(String provideid) {
		this.provideid = provideid;
	}

	public String getProvidename() {
		return providename;
	}

	public void setProvidename(String providename) {
		this.providename = providename;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
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

	public String getWarehouseidname() {
		return warehouseidname;
	}

	public void setWarehouseidname(String warehouseidname) {
		this.warehouseidname = warehouseidname;
	}

	public Integer getIstally() {
		return istally;
	}

	public void setIstally(Integer istally) {
		this.istally = istally;
	}

	public String getIstallyname() {
		return istallyname;
	}

	public void setIstallyname(String istallyname) {
		this.istallyname = istallyname;
	}

	public Date getTallydate() {
		return tallydate;
	}

	public void setTallydate(Date tallydate) {
		this.tallydate = tallydate;
	}

	public Long getTallyid() {
		return tallyid;
	}

	public void setTallyid(Long tallyid) {
		this.tallyid = tallyid;
	}

	public String getTallyidname() {
		return tallyidname;
	}

	public void setTallyidname(String tallyidname) {
		this.tallyidname = tallyidname;
	}

	public Long getMakedeptid() {
		return makedeptid;
	}

	public void setMakedeptid(Long makedeptid) {
		this.makedeptid = makedeptid;
	}

	public String getMakedeptidname() {
		return makedeptidname;
	}

	public void setMakedeptidname(String makedeptidname) {
		this.makedeptidname = makedeptidname;
	}

	public String getMakeorganid() {
		return makeorganid;
	}

	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
	}

	public String getMakeorganidname() {
		return makeorganidname;
	}

	public void setMakeorganidname(String makeorganidname) {
		this.makeorganidname = makeorganidname;
	}

	public Integer getPaymode() {
		return paymode;
	}

	public void setPaymode(Integer paymode) {
		this.paymode = paymode;
	}

	public String getPaymodename() {
		return paymodename;
	}

	public void setPaymodename(String paymodename) {
		this.paymodename = paymodename;
	}
    
    

	
}
