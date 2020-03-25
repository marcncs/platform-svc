package com.winsafe.drp.dao;


public class PurchaseTradesForm {

	/** identifier field */
    private String id;

    /** nullable persistent field */
    private String warehouseinid;
    
    private String warehouseinidname;

    /** nullable persistent field */
    private String warehouseoutid;

    private String warehouseoutidname;
    
    /** nullable persistent field */
    private String provideid;

    /** nullable persistent field */
    private String providename;

    /** nullable persistent field */
    private String plinkman;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private String tradesdate;

    /** nullable persistent field */
    private String newbatch;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Integer isaudit;
    
    private String isauditname;

    /** nullable persistent field */
    private Integer auditid;
    
    private String auditidname;

    /** nullable persistent field */
    private String auditdate;

    /** nullable persistent field */
    private Integer makeid;
    
    private String makeidname;

    /** nullable persistent field */
    private String makedate;

    /** nullable persistent field */
    private Integer isreceive;
    
    private String isreceivename;

    /** nullable persistent field */
    private Integer receiveid;
    
    private String receiveidname;

    /** nullable persistent field */
    private String receivedate;

    /** nullable persistent field */
    private Integer printtimes;

    /** nullable persistent field */
    private Integer takestatus;
    
    private String takestatusname;



	public String getAuditdate() {
		return auditdate;
	}

	public void setAuditdate(String auditdate) {
		this.auditdate = auditdate;
	}

	public Integer getAuditid() {
		return auditid;
	}

	public void setAuditid(Integer auditid) {
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

	public Integer getIsreceive() {
		return isreceive;
	}

	public void setIsreceive(Integer isreceive) {
		this.isreceive = isreceive;
	}

	public String getIsreceivename() {
		return isreceivename;
	}

	public void setIsreceivename(String isreceivename) {
		this.isreceivename = isreceivename;
	}

	public Integer getMakeid() {
		return makeid;
	}

	public void setMakeid(Integer makeid) {
		this.makeid = makeid;
	}

	public String getMakeidname() {
		return makeidname;
	}

	public void setMakeidname(String makeidname) {
		this.makeidname = makeidname;
	}

	public String getNewbatch() {
		return newbatch;
	}

	public void setNewbatch(String newbatch) {
		this.newbatch = newbatch;
	}

	public String getPlinkman() {
		return plinkman;
	}

	public void setPlinkman(String plinkman) {
		this.plinkman = plinkman;
	}

	public Integer getPrinttimes() {
		return printtimes;
	}

	public void setPrinttimes(Integer printtimes) {
		this.printtimes = printtimes;
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

	public String getMakedate() {
		return makedate;
	}

	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}

	public String getReceivedate() {
		return receivedate;
	}

	public void setReceivedate(String receivedate) {
		this.receivedate = receivedate;
	}

	public Integer getReceiveid() {
		return receiveid;
	}

	public void setReceiveid(Integer receiveid) {
		this.receiveid = receiveid;
	}

	public String getReceiveidname() {
		return receiveidname;
	}

	public void setReceiveidname(String receiveidname) {
		this.receiveidname = receiveidname;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getTakestatus() {
		return takestatus;
	}

	public void setTakestatus(Integer takestatus) {
		this.takestatus = takestatus;
	}

	public String getTakestatusname() {
		return takestatusname;
	}

	public void setTakestatusname(String takestatusname) {
		this.takestatusname = takestatusname;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTradesdate() {
		return tradesdate;
	}

	public void setTradesdate(String tradesdate) {
		this.tradesdate = tradesdate;
	}

	public String getWarehouseinid() {
		return warehouseinid;
	}

	public void setWarehouseinid(String warehouseinid) {
		this.warehouseinid = warehouseinid;
	}

	public String getWarehouseinidname() {
		return warehouseinidname;
	}

	public void setWarehouseinidname(String warehouseinidname) {
		this.warehouseinidname = warehouseinidname;
	}

	public String getWarehouseoutid() {
		return warehouseoutid;
	}

	public void setWarehouseoutid(String warehouseoutid) {
		this.warehouseoutid = warehouseoutid;
	}

	public String getWarehouseoutidname() {
		return warehouseoutidname;
	}

	public void setWarehouseoutidname(String warehouseoutidname) {
		this.warehouseoutidname = warehouseoutidname;
	}
}
