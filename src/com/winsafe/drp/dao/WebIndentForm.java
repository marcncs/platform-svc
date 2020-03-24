package com.winsafe.drp.dao;

import org.apache.struts.action.ActionForm;

/**
 * @author jelli
 * 2009-3-6
 */
public class WebIndentForm extends ActionForm {

	/** identifier field */
    private String id;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String cname;
    
    private String cmoblie;
    
    private String decidemantel;

    /** nullable persistent field */
    private Integer province;
    
    private String provincename;

    /** nullable persistent field */
    private Integer city;
    
    private String cityname;

    /** nullable persistent field */
    private Integer areas;
    
    private String areasname;

    /** nullable persistent field */
    private String receiveman;

    /** nullable persistent field */
    private String receivemobile;

    /** nullable persistent field */
    private String receivetel;

    /** nullable persistent field */
    private Integer paymentmode;
    
    private String paymentmodename;

    /** nullable persistent field */
    private String consignmentdate;

    /** nullable persistent field */
    private Double totalsum;

    /** nullable persistent field */
    private Integer transportmode;
    
    private String transportmodename;

    /** nullable persistent field */
    private String transportaddr;

    /** nullable persistent field */
    private Integer invmsg;
    
    private String invmsgname;

    /** nullable persistent field */
    private Integer ismaketicket;
    
    private String ismaketicketname;

    /** nullable persistent field */
    private String tickettitle;

    /** nullable persistent field */
    private String equiporganid;
    
    private String equiporganidname;

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

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the cid
	 */
	public String getCid() {
		return cid;
	}

	/**
	 * @param cid the cid to set
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}

	/**
	 * @return the cname
	 */
	public String getCname() {
		return cname;
	}

	/**
	 * @param cname the cname to set
	 */
	public void setCname(String cname) {
		this.cname = cname;
	}

	/**
	 * @return the province
	 */
	public Integer getProvince() {
		return province;
	}

	/**
	 * @param province the province to set
	 */
	public void setProvince(Integer province) {
		this.province = province;
	}

	/**
	 * @return the provincename
	 */
	public String getProvincename() {
		return provincename;
	}

	/**
	 * @param provincename the provincename to set
	 */
	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}

	/**
	 * @return the city
	 */
	public Integer getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(Integer city) {
		this.city = city;
	}

	/**
	 * @return the cityname
	 */
	public String getCityname() {
		return cityname;
	}

	/**
	 * @param cityname the cityname to set
	 */
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	/**
	 * @return the areas
	 */
	public Integer getAreas() {
		return areas;
	}

	/**
	 * @param areas the areas to set
	 */
	public void setAreas(Integer areas) {
		this.areas = areas;
	}

	/**
	 * @return the areasname
	 */
	public String getAreasname() {
		return areasname;
	}

	/**
	 * @param areasname the areasname to set
	 */
	public void setAreasname(String areasname) {
		this.areasname = areasname;
	}

	/**
	 * @return the receiveman
	 */
	public String getReceiveman() {
		return receiveman;
	}

	/**
	 * @param receiveman the receiveman to set
	 */
	public void setReceiveman(String receiveman) {
		this.receiveman = receiveman;
	}


	/**
	 * @return the paymentmode
	 */
	public Integer getPaymentmode() {
		return paymentmode;
	}

	/**
	 * @param paymentmode the paymentmode to set
	 */
	public void setPaymentmode(Integer paymentmode) {
		this.paymentmode = paymentmode;
	}

	/**
	 * @return the paymentmodename
	 */
	public String getPaymentmodename() {
		return paymentmodename;
	}

	/**
	 * @param paymentmodename the paymentmodename to set
	 */
	public void setPaymentmodename(String paymentmodename) {
		this.paymentmodename = paymentmodename;
	}

	/**
	 * @return the consignmentdate
	 */
	public String getConsignmentdate() {
		return consignmentdate;
	}

	/**
	 * @param consignmentdate the consignmentdate to set
	 */
	public void setConsignmentdate(String consignmentdate) {
		this.consignmentdate = consignmentdate;
	}

	/**
	 * @return the totalsum
	 */
	public Double getTotalsum() {
		return totalsum;
	}

	/**
	 * @param totalsum the totalsum to set
	 */
	public void setTotalsum(Double totalsum) {
		this.totalsum = totalsum;
	}

	/**
	 * @return the transportmode
	 */
	public Integer getTransportmode() {
		return transportmode;
	}

	/**
	 * @param transportmode the transportmode to set
	 */
	public void setTransportmode(Integer transportmode) {
		this.transportmode = transportmode;
	}

	/**
	 * @return the transportmodename
	 */
	public String getTransportmodename() {
		return transportmodename;
	}

	/**
	 * @param transportmodename the transportmodename to set
	 */
	public void setTransportmodename(String transportmodename) {
		this.transportmodename = transportmodename;
	}

	/**
	 * @return the transportaddr
	 */
	public String getTransportaddr() {
		return transportaddr;
	}

	/**
	 * @param transportaddr the transportaddr to set
	 */
	public void setTransportaddr(String transportaddr) {
		this.transportaddr = transportaddr;
	}

	/**
	 * @return the invmsg
	 */
	public Integer getInvmsg() {
		return invmsg;
	}

	/**
	 * @param invmsg the invmsg to set
	 */
	public void setInvmsg(Integer invmsg) {
		this.invmsg = invmsg;
	}

	/**
	 * @return the invmsgname
	 */
	public String getInvmsgname() {
		return invmsgname;
	}

	/**
	 * @param invmsgname the invmsgname to set
	 */
	public void setInvmsgname(String invmsgname) {
		this.invmsgname = invmsgname;
	}

	/**
	 * @return the ismaketicket
	 */
	public Integer getIsmaketicket() {
		return ismaketicket;
	}

	/**
	 * @param ismaketicket the ismaketicket to set
	 */
	public void setIsmaketicket(Integer ismaketicket) {
		this.ismaketicket = ismaketicket;
	}

	/**
	 * @return the ismaketicketname
	 */
	public String getIsmaketicketname() {
		return ismaketicketname;
	}

	/**
	 * @param ismaketicketname the ismaketicketname to set
	 */
	public void setIsmaketicketname(String ismaketicketname) {
		this.ismaketicketname = ismaketicketname;
	}

	/**
	 * @return the tickettitle
	 */
	public String getTickettitle() {
		return tickettitle;
	}

	/**
	 * @param tickettitle the tickettitle to set
	 */
	public void setTickettitle(String tickettitle) {
		this.tickettitle = tickettitle;
	}

	/**
	 * @return the equiporganid
	 */
	public String getEquiporganid() {
		return equiporganid;
	}

	/**
	 * @param equiporganid the equiporganid to set
	 */
	public void setEquiporganid(String equiporganid) {
		this.equiporganid = equiporganid;
	}

	/**
	 * @return the equiporganidname
	 */
	public String getEquiporganidname() {
		return equiporganidname;
	}

	/**
	 * @param equiporganidname the equiporganidname to set
	 */
	public void setEquiporganidname(String equiporganidname) {
		this.equiporganidname = equiporganidname;
	}

	/**
	 * @return the makedate
	 */
	public String getMakedate() {
		return makedate;
	}

	/**
	 * @param makedate the makedate to set
	 */
	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the isaudit
	 */
	public Integer getIsaudit() {
		return isaudit;
	}

	/**
	 * @param isaudit the isaudit to set
	 */
	public void setIsaudit(Integer isaudit) {
		this.isaudit = isaudit;
	}

	/**
	 * @return the isauditname
	 */
	public String getIsauditname() {
		return isauditname;
	}

	/**
	 * @param isauditname the isauditname to set
	 */
	public void setIsauditname(String isauditname) {
		this.isauditname = isauditname;
	}

	/**
	 * @return the auditid
	 */
	public Long getAuditid() {
		return auditid;
	}

	/**
	 * @param auditid the auditid to set
	 */
	public void setAuditid(Long auditid) {
		this.auditid = auditid;
	}

	/**
	 * @return the auditidname
	 */
	public String getAuditidname() {
		return auditidname;
	}

	/**
	 * @param auditidname the auditidname to set
	 */
	public void setAuditidname(String auditidname) {
		this.auditidname = auditidname;
	}

	/**
	 * @return the auditdate
	 */
	public String getAuditdate() {
		return auditdate;
	}

	/**
	 * @param auditdate the auditdate to set
	 */
	public void setAuditdate(String auditdate) {
		this.auditdate = auditdate;
	}

	/**
	 * @return the isendcase
	 */
	public Integer getIsendcase() {
		return isendcase;
	}

	/**
	 * @param isendcase the isendcase to set
	 */
	public void setIsendcase(Integer isendcase) {
		this.isendcase = isendcase;
	}

	/**
	 * @return the isendcasename
	 */
	public String getIsendcasename() {
		return isendcasename;
	}

	/**
	 * @param isendcasename the isendcasename to set
	 */
	public void setIsendcasename(String isendcasename) {
		this.isendcasename = isendcasename;
	}

	/**
	 * @return the endcaseid
	 */
	public Long getEndcaseid() {
		return endcaseid;
	}

	/**
	 * @param endcaseid the endcaseid to set
	 */
	public void setEndcaseid(Long endcaseid) {
		this.endcaseid = endcaseid;
	}

	/**
	 * @return the endcaseidname
	 */
	public String getEndcaseidname() {
		return endcaseidname;
	}

	/**
	 * @param endcaseidname the endcaseidname to set
	 */
	public void setEndcaseidname(String endcaseidname) {
		this.endcaseidname = endcaseidname;
	}

	/**
	 * @return the endcasedate
	 */
	public String getEndcasedate() {
		return endcasedate;
	}

	/**
	 * @param endcasedate the endcasedate to set
	 */
	public void setEndcasedate(String endcasedate) {
		this.endcasedate = endcasedate;
	}

	/**
	 * @return the isblankout
	 */
	public Integer getIsblankout() {
		return isblankout;
	}

	/**
	 * @param isblankout the isblankout to set
	 */
	public void setIsblankout(Integer isblankout) {
		this.isblankout = isblankout;
	}

	/**
	 * @return the isblankoutname
	 */
	public String getIsblankoutname() {
		return isblankoutname;
	}

	/**
	 * @param isblankoutname the isblankoutname to set
	 */
	public void setIsblankoutname(String isblankoutname) {
		this.isblankoutname = isblankoutname;
	}

	/**
	 * @return the blankoutid
	 */
	public Long getBlankoutid() {
		return blankoutid;
	}

	/**
	 * @param blankoutid the blankoutid to set
	 */
	public void setBlankoutid(Long blankoutid) {
		this.blankoutid = blankoutid;
	}

	/**
	 * @return the blankoutidname
	 */
	public String getBlankoutidname() {
		return blankoutidname;
	}

	/**
	 * @param blankoutidname the blankoutidname to set
	 */
	public void setBlankoutidname(String blankoutidname) {
		this.blankoutidname = blankoutidname;
	}

	/**
	 * @return the blankoutdate
	 */
	public String getBlankoutdate() {
		return blankoutdate;
	}

	/**
	 * @param blankoutdate the blankoutdate to set
	 */
	public void setBlankoutdate(String blankoutdate) {
		this.blankoutdate = blankoutdate;
	}

	/**
	 * @return the blankoutreason
	 */
	public String getBlankoutreason() {
		return blankoutreason;
	}

	/**
	 * @param blankoutreason the blankoutreason to set
	 */
	public void setBlankoutreason(String blankoutreason) {
		this.blankoutreason = blankoutreason;
	}

	/**
	 * @return the takestatus
	 */
	public Integer getTakestatus() {
		return takestatus;
	}

	/**
	 * @param takestatus the takestatus to set
	 */
	public void setTakestatus(Integer takestatus) {
		this.takestatus = takestatus;
	}

	/**
	 * @return the takestatusname
	 */
	public String getTakestatusname() {
		return takestatusname;
	}

	/**
	 * @param takestatusname the takestatusname to set
	 */
	public void setTakestatusname(String takestatusname) {
		this.takestatusname = takestatusname;
	}

	public String getCmoblie() {
		return cmoblie;
	}

	public void setCmoblie(String cmoblie) {
		this.cmoblie = cmoblie;
	}

	public String getDecidemantel() {
		return decidemantel;
	}

	public void setDecidemantel(String decidemantel) {
		this.decidemantel = decidemantel;
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
    
    
}
