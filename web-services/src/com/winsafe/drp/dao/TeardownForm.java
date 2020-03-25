package com.winsafe.drp.dao;


public class TeardownForm {
	 /** identifier field */
    private String id;

    /** nullable persistent field */
    private String tproductid;

    /** nullable persistent field */
    private String tproductname;

    /** nullable persistent field */
    private String batch;

    /** nullable persistent field */
    private Double tquantity;

    /** nullable persistent field */
    private Long warehouseoutid;
    
    private String warehouseoutidname;

    /** nullable persistent field */
    private Long tdept;
    
    private String tdeptname;

    /** nullable persistent field */
    private String completeintenddate;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Long makeid;
    
    private String makeidname;

    /** nullable persistent field */
    private String makedate;

    /** nullable persistent field */
    private Integer isaudit;
    
    private String isauditname;

    /** nullable persistent field */
    private Long auditid;
    
    private String auditidname;

    /** nullable persistent field */
    private String auditdate;

    /** nullable persistent field */
    private Integer iscomplete;
    
    private String iscompletename;

    /** nullable persistent field */
    private Long completeid;
    
    private String completeidname;

    /** nullable persistent field */
    private String completedate;

    /** nullable persistent field */
    private String stuffbatch;

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

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getCompletedate() {
		return completedate;
	}

	public void setCompletedate(String completedate) {
		this.completedate = completedate;
	}

	public Long getCompleteid() {
		return completeid;
	}

	public void setCompleteid(Long completeid) {
		this.completeid = completeid;
	}

	public String getCompleteidname() {
		return completeidname;
	}

	public void setCompleteidname(String completeidname) {
		this.completeidname = completeidname;
	}

	public String getCompleteintenddate() {
		return completeintenddate;
	}

	public void setCompleteintenddate(String completeintenddate) {
		this.completeintenddate = completeintenddate;
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

	public String getIsauditname() {
		return isauditname;
	}

	public void setIsauditname(String isauditname) {
		this.isauditname = isauditname;
	}

	public Integer getIscomplete() {
		return iscomplete;
	}

	public void setIscomplete(Integer iscomplete) {
		this.iscomplete = iscomplete;
	}

	public String getIscompletename() {
		return iscompletename;
	}

	public void setIscompletename(String iscompletename) {
		this.iscompletename = iscompletename;
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

	public String getStuffbatch() {
		return stuffbatch;
	}

	public void setStuffbatch(String stuffbatch) {
		this.stuffbatch = stuffbatch;
	}

	public Long getTdept() {
		return tdept;
	}

	public void setTdept(Long tdept) {
		this.tdept = tdept;
	}

	public String getTdeptname() {
		return tdeptname;
	}

	public void setTdeptname(String tdeptname) {
		this.tdeptname = tdeptname;
	}

	public String getTproductid() {
		return tproductid;
	}

	public void setTproductid(String tproductid) {
		this.tproductid = tproductid;
	}

	public String getTproductname() {
		return tproductname;
	}

	public void setTproductname(String tproductname) {
		this.tproductname = tproductname;
	}

	public Double getTquantity() {
		return tquantity;
	}

	public void setTquantity(Double tquantity) {
		this.tquantity = tquantity;
	}

	public Long getWarehouseoutid() {
		return warehouseoutid;
	}

	public void setWarehouseoutid(Long warehouseoutid) {
		this.warehouseoutid = warehouseoutid;
	}

	public String getWarehouseoutidname() {
		return warehouseoutidname;
	}

	public void setWarehouseoutidname(String warehouseoutidname) {
		this.warehouseoutidname = warehouseoutidname;
	}

}
