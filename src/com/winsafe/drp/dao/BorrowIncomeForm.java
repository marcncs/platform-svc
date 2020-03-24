package com.winsafe.drp.dao;

public class BorrowIncomeForm {
	 /** identifier field */
    private String id;

    /** nullable persistent field */
    private Long warehouseid;
    
    private String warehouseidname;

    /** nullable persistent field */
    private Long warehouseout;
    
    private String warehouseoutname;

    /** nullable persistent field */
    private String poid;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private String plinkman;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private String batch;

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
    private Long makeid;
    
    private String makeidname;

    /** nullable persistent field */
    private String makedate;

    /** nullable persistent field */
    private Integer isbacktrack;
    
    private String isbacktrackname;

    /** nullable persistent field */
    private Long backtrackid;
    
    private String backtrackidname;

    /** nullable persistent field */
    private String backtrackdate;

    /** nullable persistent field */
    private Integer istranswithdraw;
    
    private String istranswithdrawname;

    /** nullable persistent field */
    private Long transwithdrawid;
    
    private String transwithdrawidname;

    /** nullable persistent field */
    private String transwithdrawdate;

    /** nullable persistent field */
    private Integer printtimes;

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

	public String getBacktrackdate() {
		return backtrackdate;
	}

	public void setBacktrackdate(String backtrackdate) {
		this.backtrackdate = backtrackdate;
	}

	public Long getBacktrackid() {
		return backtrackid;
	}

	public void setBacktrackid(Long backtrackid) {
		this.backtrackid = backtrackid;
	}

	public String getBacktrackidname() {
		return backtrackidname;
	}

	public void setBacktrackidname(String backtrackidname) {
		this.backtrackidname = backtrackidname;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
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

	public Integer getIsbacktrack() {
		return isbacktrack;
	}

	public void setIsbacktrack(Integer isbacktrack) {
		this.isbacktrack = isbacktrack;
	}

	public String getIsbacktrackname() {
		return isbacktrackname;
	}

	public void setIsbacktrackname(String isbacktrackname) {
		this.isbacktrackname = isbacktrackname;
	}

	public Integer getIstranswithdraw() {
		return istranswithdraw;
	}

	public void setIstranswithdraw(Integer istranswithdraw) {
		this.istranswithdraw = istranswithdraw;
	}

	public String getIstranswithdrawname() {
		return istranswithdrawname;
	}

	public void setIstranswithdrawname(String istranswithdrawname) {
		this.istranswithdrawname = istranswithdrawname;
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

	public Integer getPrinttimes() {
		return printtimes;
	}

	public void setPrinttimes(Integer printtimes) {
		this.printtimes = printtimes;
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

	public String getTranswithdrawdate() {
		return transwithdrawdate;
	}

	public void setTranswithdrawdate(String transwithdrawdate) {
		this.transwithdrawdate = transwithdrawdate;
	}

	public Long getTranswithdrawid() {
		return transwithdrawid;
	}

	public void setTranswithdrawid(Long transwithdrawid) {
		this.transwithdrawid = transwithdrawid;
	}

	public String getTranswithdrawidname() {
		return transwithdrawidname;
	}

	public void setTranswithdrawidname(String transwithdrawidname) {
		this.transwithdrawidname = transwithdrawidname;
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

	public Long getWarehouseout() {
		return warehouseout;
	}

	public void setWarehouseout(Long warehouseout) {
		this.warehouseout = warehouseout;
	}

	public String getWarehouseoutname() {
		return warehouseoutname;
	}

	public void setWarehouseoutname(String warehouseoutname) {
		this.warehouseoutname = warehouseoutname;
	}
}
