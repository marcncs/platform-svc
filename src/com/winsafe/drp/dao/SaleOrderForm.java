package com.winsafe.drp.dao;



public class SaleOrderForm {

	/** identifier field */
    private String id;

    /** nullable persistent field */
    private String customerbillid;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String cname;
    
    private String cmobile;

    /** nullable persistent field */
    private Integer province;
    
    private String provincename;

    /** nullable persistent field */
    private Integer city;
    
    private String cityname;

    /** nullable persistent field */
    private Integer areas;
    
    private String areasname;
    
    private String decideman;
    
    private String decidemantel;

    /** nullable persistent field */
    private String receiveman;
    
    private String receivemobile;

    /** nullable persistent field */
    private String receivetel;

    /** nullable persistent field */
    private Long saledept;
    
    private String saledeptname;

    /** nullable persistent field */
    private Long saleid;
    
    private String saleidname;

    /** nullable persistent field */
    private Integer paymentmode;
    
    private String paymentmodename;

    /** nullable persistent field */
    private String consignmentdate;
    
    private String consignmenttime;
    
    private Integer source;
    
    private String sourcename;

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
    private Integer invmsg;
    
    private String invmsgname;

    /** nullable persistent field */
    private Integer ismaketicket;
    
    private String ismaketicketname;
    
    private String tickettitle;

    /** nullable persistent field */
    private String makeorganid;
    
    private String makeorganidname;

    /** nullable persistent field */
    private Long makedeptid;
    
    private String makedeptidname;
    
    private String equiporganid;
    
    private String equiporganidname;

    /** nullable persistent field */
    private Long makeid;
    
    private String makeidname;

    /** nullable persistent field */
    private String makedate;

    /** nullable persistent field */
    private Long updateid;
    
    private String updateidname;

    /** nullable persistent field */
    private String lastupdate;

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
    private Integer isendcase;
    
    private String isendcasename;

    /** nullable persistent field */
    private Long endcaseid;
    
    private String endcaseidname;

    /** nullable persistent field */
    private String endcasedate;

    /** nullable persistent field */
    private Integer isblankout;

    private String isblankoutname;
    
    /** nullable persistent field */
    private Long blankoutid;
    
    private String blankoutidname;

    /** nullable persistent field */
    private String blankoutdate;

    /** nullable persistent field */
    private String blankoutreason;

    /** nullable persistent field */
    private Integer takestatus;
    
    private String takestatusname;

    /** nullable persistent field */
    private Integer printtimes;

	public Integer getAreas() {
		return areas;
	}

	public void setAreas(Integer areas) {
		this.areas = areas;
	}

	public String getAreasname() {
		return areasname;
	}

	public void setAreasname(String areasname) {
		this.areasname = areasname;
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

	public String getBlankoutreason() {
		return blankoutreason;
	}

	public void setBlankoutreason(String blankoutreason) {
		this.blankoutreason = blankoutreason;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getConsignmentdate() {
		return consignmentdate;
	}

	public void setConsignmentdate(String consignmentdate) {
		this.consignmentdate = consignmentdate;
	}

	public String getCustomerbillid() {
		return customerbillid;
	}

	public void setCustomerbillid(String customerbillid) {
		this.customerbillid = customerbillid;
	}

	public String getEndcasedate() {
		return endcasedate;
	}

	public void setEndcasedate(String endcasedate) {
		this.endcasedate = endcasedate;
	}

	public Long getEndcaseid() {
		return endcaseid;
	}

	public void setEndcaseid(Long endcaseid) {
		this.endcaseid = endcaseid;
	}

	public String getEndcaseidname() {
		return endcaseidname;
	}

	public void setEndcaseidname(String endcaseidname) {
		this.endcaseidname = endcaseidname;
	}

	public String getEquiporganid() {
		return equiporganid;
	}

	public void setEquiporganid(String equiporganid) {
		this.equiporganid = equiporganid;
	}

	public String getEquiporganidname() {
		return equiporganidname;
	}

	public void setEquiporganidname(String equiporganidname) {
		this.equiporganidname = equiporganidname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Integer getIsblankout() {
		return isblankout;
	}

	public void setIsblankout(Integer isblankout) {
		this.isblankout = isblankout;
	}

	public Integer getIsendcase() {
		return isendcase;
	}

	public void setIsendcase(Integer isendcase) {
		this.isendcase = isendcase;
	}

	public String getIsendcasename() {
		return isendcasename;
	}

	public void setIsendcasename(String isendcasename) {
		this.isendcasename = isendcasename;
	}

	public Integer getIsmaketicket() {
		return ismaketicket;
	}

	public void setIsmaketicket(Integer ismaketicket) {
		this.ismaketicket = ismaketicket;
	}

	public String getIsmaketicketname() {
		return ismaketicketname;
	}

	public void setIsmaketicketname(String ismaketicketname) {
		this.ismaketicketname = ismaketicketname;
	}

	public String getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(String lastupdate) {
		this.lastupdate = lastupdate;
	}

	public String getMakedate() {
		return makedate;
	}

	public void setMakedate(String makedate) {
		this.makedate = makedate;
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

	public Integer getPrinttimes() {
		return printtimes;
	}

	public void setPrinttimes(Integer printtimes) {
		this.printtimes = printtimes;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public String getProvincename() {
		return provincename;
	}

	public void setProvincename(String provincename) {
		this.provincename = provincename;
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

	public String getCmobile() {
		return cmobile;
	}

	public void setCmobile(String cmobile) {
		this.cmobile = cmobile;
	}

	public String getReceivemobile() {
		return receivemobile;
	}

	public void setReceivemobile(String receivemobile) {
		this.receivemobile = receivemobile;
	}

	public String getReceivetel() {
		return receivetel;
	}

	public void setReceivetel(String receivetel) {
		this.receivetel = receivetel;
	}

	public String getTickettitle() {
		return tickettitle;
	}

	public void setTickettitle(String tickettitle) {
		this.tickettitle = tickettitle;
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

	public Long getUpdateid() {
		return updateid;
	}

	public void setUpdateid(Long updateid) {
		this.updateid = updateid;
	}

	public String getUpdateidname() {
		return updateidname;
	}

	public void setUpdateidname(String updateidname) {
		this.updateidname = updateidname;
	}

	public String getDecideman() {
		return decideman;
	}

	public void setDecideman(String decideman) {
		this.decideman = decideman;
	}

	public String getDecidemantel() {
		return decidemantel;
	}

	public void setDecidemantel(String decidemantel) {
		this.decidemantel = decidemantel;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getSourcename() {
		return sourcename;
	}

	public void setSourcename(String sourcename) {
		this.sourcename = sourcename;
	}

	public String getConsignmenttime() {
		return consignmenttime;
	}

	public void setConsignmenttime(String consignmenttime) {
		this.consignmenttime = consignmenttime;
	}
	
	
    
}
