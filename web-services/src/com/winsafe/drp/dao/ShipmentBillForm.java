package com.winsafe.drp.dao;


public class ShipmentBillForm {
	 /** identifier field */
    private String id;

    private Integer objectsort;
    
    /** nullable persistent field */
    private String soid;

    /** nullable persistent field */
    private Long saledept;
    
    private String saledeptname;

    /** nullable persistent field */
    private Long saleid;
    
    private String saleidname;

    /** nullable persistent field */
    private String cid;
    
    private String cname;
    
    private String cmobile;

    /** nullable persistent field */
    private String linkman;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private String receiveaddr;

    /** nullable persistent field */
    private String requiredate;

    /** nullable persistent field */
    private Integer transportmode;
    
    private String transportmodename;

    /** nullable persistent field */
    private String transportnum;

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
    private Integer istrans;
    
    private String istransname;
    
    private Integer isblankout;
    
    private String isblankoutname;

    /** nullable persistent field */
    private Long blankoutid;
    
    private String blankoutidname;

    /** nullable persistent field */
    private String blankoutdate;
    
    private Integer paymentmode;
    
    private String paymentmodename;
    
    private Integer invmsg;
    
    private String invmsgname;
    
    private String tickettitle;
    
    private Double erasum;
    
    private String blankoutreason;

	public String getBlankoutreason() {
		return blankoutreason;
	}

	public void setBlankoutreason(String blankoutreason) {
		this.blankoutreason = blankoutreason;
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

	public String getBlankoutdate() {
		return blankoutdate;
	}

	public void setBlankoutdate(String blankoutdate) {
		this.blankoutdate = blankoutdate;
	}

	public Long getBlankoutid() {
		return blankoutid;
	}

	public void setBlankoutid(Long blankoutid) {
		this.blankoutid = blankoutid;
	}

	public String getBlankoutidname() {
		return blankoutidname;
	}

	public void setBlankoutidname(String blankoutidname) {
		this.blankoutidname = blankoutidname;
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
 

	public String getIsblankoutname() {
		return isblankoutname;
	}

	public void setIsblankoutname(String isblankoutname) {
		this.isblankoutname = isblankoutname;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
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

	public String getReceiveaddr() {
		return receiveaddr;
	}

	public void setReceiveaddr(String receiveaddr) {
		this.receiveaddr = receiveaddr;
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

	public Long getSaledept() {
		return saledept;
	}

	public void setSaledept(Long saledept) {
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

	public String getSoid() {
		return soid;
	}

	public void setSoid(String soid) {
		this.soid = soid;
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

	public String getTransportnum() {
		return transportnum;
	}

	public void setTransportnum(String transportnum) {
		this.transportnum = transportnum;
	}

	public Integer getIstrans() {
		return istrans;
	}

	public void setIstrans(Integer istrans) {
		this.istrans = istrans;
	}

	public String getIstransname() {
		return istransname;
	}

	public void setIstransname(String istransname) {
		this.istransname = istransname;
	}

	public Integer getPaymentmode() {
		return paymentmode;
	}

	public void setPaymentmode(Integer paymentmode) {
		this.paymentmode = paymentmode;
	}

	public String getPaymentmodename() {
		return paymentmodename;
	}

	public void setPaymentmodename(String paymentmodename) {
		this.paymentmodename = paymentmodename;
	}

	public Double getErasum() {
		return erasum;
	}

	public void setErasum(Double erasum) {
		this.erasum = erasum;
	}

	public Integer getIsblankout() {
		return isblankout;
	}

	public void setIsblankout(Integer isblankout) {
		this.isblankout = isblankout;
	}

	public Integer getInvmsg() {
		return invmsg;
	}

	public void setInvmsg(Integer invmsg) {
		this.invmsg = invmsg;
	}

	public String getInvmsgname() {
		return invmsgname;
	}

	public void setInvmsgname(String invmsgname) {
		this.invmsgname = invmsgname;
	}

	public String getCmobile() {
		return cmobile;
	}

	public void setCmobile(String cmobile) {
		this.cmobile = cmobile;
	}

	public String getTickettitle() {
		return tickettitle;
	}

	public void setTickettitle(String tickettitle) {
		this.tickettitle = tickettitle;
	}

	public Integer getObjectsort() {
		return objectsort;
	}

	public void setObjectsort(Integer objectsort) {
		this.objectsort = objectsort;
	}
    
	
    
}
