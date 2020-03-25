package com.winsafe.drp.dao;


public class LoanOutForm {
	 /** identifier field */
    private String id;

    /** nullable persistent field */
    private String uid;

    /** nullable persistent field */
    private String uname;

    /** nullable persistent field */
    private String receiveman;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private Integer saledept;
    
    private String saledeptname;

    /** nullable persistent field */
    private Long saleid;
    
    private String saleidname;

    /** nullable persistent field */
    private String consignmentdate;

    /** nullable persistent field */
    private Double totalsum;

    /** nullable persistent field */
    private Integer transportmode;
    
    private String transportmodename;

    /** nullable persistent field */
    private Integer transit;
    
    private String transitname;

    /** nullable persistent field */
    private String transportaddr;

    /** nullable persistent field */
    private Long makeid;
    
    private String makeidname;

    /** nullable persistent field */
    private String makedate;

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
    private Integer isreceive;
    
    private String isreceivename;

    /** nullable persistent field */
    private Long receiveid;
    
    private String receiveidname;

    /** nullable persistent field */
    private String receivedate;

    /** nullable persistent field */
    private Integer istranssale;
    
    private String istranssalename;

    /** nullable persistent field */
    private Long transsaleid;
    
    private String transsaleidname;

    /** nullable persistent field */
    private String transsaledate;

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

	public String getConsignmentdate() {
		return consignmentdate;
	}

	public void setConsignmentdate(String consignmentdate) {
		this.consignmentdate = consignmentdate;
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

	public Integer getIstranssale() {
		return istranssale;
	}

	public void setIstranssale(Integer istranssale) {
		this.istranssale = istranssale;
	}

	public String getIstranssalename() {
		return istranssalename;
	}

	public void setIstranssalename(String istranssalename) {
		this.istranssalename = istranssalename;
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

	public String getReceivedate() {
		return receivedate;
	}

	public void setReceivedate(String receivedate) {
		this.receivedate = receivedate;
	}

	public Long getReceiveid() {
		return receiveid;
	}

	public void setReceiveid(Long receiveid) {
		this.receiveid = receiveid;
	}

	public String getReceiveidname() {
		return receiveidname;
	}

	public void setReceiveidname(String receiveidname) {
		this.receiveidname = receiveidname;
	}

	public String getReceiveman() {
		return receiveman;
	}

	public void setReceiveman(String receiveman) {
		this.receiveman = receiveman;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getSaledept() {
		return saledept;
	}

	public void setSaledept(Integer saledept) {
		this.saledept = saledept;
	}

	public String getSaledeptname() {
		return saledeptname;
	}

	public void setSaledeptname(String saledeptname) {
		this.saledeptname = saledeptname;
	}

	public Long getSaleid() {
		return saleid;
	}

	public void setSaleid(Long saleid) {
		this.saleid = saleid;
	}

	public String getSaleidname() {
		return saleidname;
	}

	public void setSaleidname(String saleidname) {
		this.saleidname = saleidname;
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

	public Integer getTransit() {
		return transit;
	}

	public void setTransit(Integer transit) {
		this.transit = transit;
	}

	public String getTransitname() {
		return transitname;
	}

	public void setTransitname(String transitname) {
		this.transitname = transitname;
	}

	public String getTransportaddr() {
		return transportaddr;
	}

	public void setTransportaddr(String transportaddr) {
		this.transportaddr = transportaddr;
	}

	public Integer getTransportmode() {
		return transportmode;
	}

	public void setTransportmode(Integer transportmode) {
		this.transportmode = transportmode;
	}

	public String getTransportmodename() {
		return transportmodename;
	}

	public void setTransportmodename(String transportmodename) {
		this.transportmodename = transportmodename;
	}

	public String getTranssaledate() {
		return transsaledate;
	}

	public void setTranssaledate(String transsaledate) {
		this.transsaledate = transsaledate;
	}

	public Long getTranssaleid() {
		return transsaleid;
	}

	public void setTranssaleid(Long transsaleid) {
		this.transsaleid = transsaleid;
	}

	public String getTranssaleidname() {
		return transsaleidname;
	}

	public void setTranssaleidname(String transsaleidname) {
		this.transsaleidname = transsaleidname;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}
}
