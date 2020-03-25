package com.winsafe.drp.dao;


public class AdsumGoodsForm {
	/** identifier field */
    private String id;

    /** nullable persistent field */
    private String poid;

    /** nullable persistent field */
    private String pid;
    
    private String pidname;

    /** nullable persistent field */
    private String plinkman;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private String obtaincode;

    /** nullable persistent field */
    private Long purchasedept;
    
    private String purchasedeptname;

    /** nullable persistent field */
    private Long purchaseid;
    
    private String purchaseidname;

    /** nullable persistent field */
    private Double totalsum;

    /** nullable persistent field */
    private String receivedate;

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
    private String remark;
    
    private Integer printtimes;

	public Integer getPrinttimes() {
		return printtimes;
	}

	public void setPrinttimes(Integer printtimes) {
		this.printtimes = printtimes;
	}

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

	public String getObtaincode() {
		return obtaincode;
	}

	public void setObtaincode(String obtaincode) {
		this.obtaincode = obtaincode;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPidname() {
		return pidname;
	}

	public void setPidname(String pidname) {
		this.pidname = pidname;
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

	public Long getPurchasedept() {
		return purchasedept;
	}

	public void setPurchasedept(Long purchasedept) {
		this.purchasedept = purchasedept;
	}

	public String getPurchasedeptname() {
		return purchasedeptname;
	}

	public void setPurchasedeptname(String purchasedeptname) {
		this.purchasedeptname = purchasedeptname;
	}

	public Long getPurchaseid() {
		return purchaseid;
	}

	public void setPurchaseid(Long purchaseid) {
		this.purchaseid = purchaseid;
	}

	public String getPurchaseidname() {
		return purchaseidname;
	}

	public void setPurchaseidname(String purchaseidname) {
		this.purchaseidname = purchaseidname;
	}

	public String getReceivedate() {
		return receivedate;
	}

	public void setReceivedate(String receivedate) {
		this.receivedate = receivedate;
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
}
