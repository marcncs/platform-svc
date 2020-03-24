package com.winsafe.drp.dao;

import org.apache.struts.action.ActionForm;

/**
 * @author jelli
 * 2009-3-9
 */
public class IntegralOrderForm extends ActionForm {
	/** identifier field */
    private String id;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
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

    /** nullable persistent field */
    private String decideman;

    /** nullable persistent field */
    private String decidemantel;

    /** nullable persistent field */
    private String receiveman;

    /** nullable persistent field */
    private String receivemobile;

    /** nullable persistent field */
    private String receivetel;

    /** nullable persistent field */
    private Integer saledept;
    
    private String saledeptname;

    /** nullable persistent field */
    private Integer saleid;
    
    private String saleidname;

    /** nullable persistent field */
    private String consignmentdate;

    /** nullable persistent field */
    private Integer source;
    
    private String sourcename;

    /** nullable persistent field */
    private Double integralsum;

    /** nullable persistent field */
    private Integer transportmode;
    
    private String transportmodename;

    /** nullable persistent field */
    private Integer transit;
    
    private String transitname;

    /** nullable persistent field */
    private String transportaddr;

    /** nullable persistent field */
    private String makeorganid;
    
    private String makeorganidname;

    /** nullable persistent field */
    private Integer makedeptid;
    
    private String makedeptidname;

    /** nullable persistent field */
    private String equiporganid;
    
    private String equiporganidname;

	/** nullable persistent field */
    private Integer makeid;
    
    private String makeidname;

    /** nullable persistent field */
    private String makedate;

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
    private Integer isendcase;
    
    private String isendcasename;

    /** nullable persistent field */
    private Integer endcaseid;
    
    private String endcaseidname;

    /** nullable persistent field */
    private String endcasedate;

    /** nullable persistent field */
    private Integer isblankout;
    
    private String isblankoutname;

    /** nullable persistent field */
    private Integer blankoutid;
    
    private String blankoutidname;

    /** nullable persistent field */
    private String blankoutdate;

    /** nullable persistent field */
    private String blankoutreason;

    /** nullable persistent field */
    private Integer takestatus;
    
    private Integer billtype;
    
    private String billtypename;
    
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
	 * @return the cmobile
	 */
	public String getCmobile() {
		return cmobile;
	}

	/**
	 * @param cmobile the cmobile to set
	 */
	public void setCmobile(String cmobile) {
		this.cmobile = cmobile;
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
	 * @return the decideman
	 */
	public String getDecideman() {
		return decideman;
	}

	/**
	 * @param decideman the decideman to set
	 */
	public void setDecideman(String decideman) {
		this.decideman = decideman;
	}

	/**
	 * @return the decidemantel
	 */
	public String getDecidemantel() {
		return decidemantel;
	}

	/**
	 * @param decidemantel the decidemantel to set
	 */
	public void setDecidemantel(String decidemantel) {
		this.decidemantel = decidemantel;
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
	 * @return the receivemobile
	 */
	public String getReceivemobile() {
		return receivemobile;
	}

	/**
	 * @param receivemobile the receivemobile to set
	 */
	public void setReceivemobile(String receivemobile) {
		this.receivemobile = receivemobile;
	}

	/**
	 * @return the receivetel
	 */
	public String getReceivetel() {
		return receivetel;
	}

	/**
	 * @param receivetel the receivetel to set
	 */
	public void setReceivetel(String receivetel) {
		this.receivetel = receivetel;
	}

	/**
	 * @return the saledept
	 */
	public Integer getSaledept() {
		return saledept;
	}

	/**
	 * @param saledept the saledept to set
	 */
	public void setSaledept(Integer saledept) {
		this.saledept = saledept;
	}

	/**
	 * @return the saledeptname
	 */
	public String getSaledeptname() {
		return saledeptname;
	}

	/**
	 * @param saledeptname the saledeptname to set
	 */
	public void setSaledeptname(String saledeptname) {
		this.saledeptname = saledeptname;
	}

	/**
	 * @return the saleid
	 */
	public Integer getSaleid() {
		return saleid;
	}

	/**
	 * @param saleid the saleid to set
	 */
	public void setSaleid(Integer saleid) {
		this.saleid = saleid;
	}

	/**
	 * @return the saleidname
	 */
	public String getSaleidname() {
		return saleidname;
	}

	/**
	 * @param saleidname the saleidname to set
	 */
	public void setSaleidname(String saleidname) {
		this.saleidname = saleidname;
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
	 * @return the source
	 */
	public Integer getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(Integer source) {
		this.source = source;
	}

	/**
	 * @return the sourcename
	 */
	public String getSourcename() {
		return sourcename;
	}

	/**
	 * @param sourcename the sourcename to set
	 */
	public void setSourcename(String sourcename) {
		this.sourcename = sourcename;
	}

	/**
	 * @return the integralsum
	 */
	public Double getIntegralsum() {
		return integralsum;
	}

	/**
	 * @param integralsum the integralsum to set
	 */
	public void setIntegralsum(Double integralsum) {
		this.integralsum = integralsum;
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
	 * @return the transit
	 */
	public Integer getTransit() {
		return transit;
	}

	/**
	 * @param transit the transit to set
	 */
	public void setTransit(Integer transit) {
		this.transit = transit;
	}

	/**
	 * @return the transitname
	 */
	public String getTransitname() {
		return transitname;
	}

	/**
	 * @param transitname the transitname to set
	 */
	public void setTransitname(String transitname) {
		this.transitname = transitname;
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
	 * @return the makeorganid
	 */
	public String getMakeorganid() {
		return makeorganid;
	}

	/**
	 * @param makeorganid the makeorganid to set
	 */
	public void setMakeorganid(String makeorganid) {
		this.makeorganid = makeorganid;
	}

	/**
	 * @return the makedeptid
	 */
	public Integer getMakedeptid() {
		return makedeptid;
	}

	/**
	 * @param makedeptid the makedeptid to set
	 */
	public void setMakedeptid(Integer makedeptid) {
		this.makedeptid = makedeptid;
	}

	/**
	 * @return the makedeptidname
	 */
	public String getMakedeptidname() {
		return makedeptidname;
	}

	/**
	 * @param makedeptidname the makedeptidname to set
	 */
	public void setMakedeptidname(String makedeptidname) {
		this.makedeptidname = makedeptidname;
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
	 * @return the makeid
	 */
	public Integer getMakeid() {
		return makeid;
	}

	/**
	 * @param makeid the makeid to set
	 */
	public void setMakeid(Integer makeid) {
		this.makeid = makeid;
	}

	/**
	 * @return the makeidname
	 */
	public String getMakeidname() {
		return makeidname;
	}

	/**
	 * @param makeidname the makeidname to set
	 */
	public void setMakeidname(String makeidname) {
		this.makeidname = makeidname;
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
	public Integer getAuditid() {
		return auditid;
	}

	/**
	 * @param auditid the auditid to set
	 */
	public void setAuditid(Integer auditid) {
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
	public Integer getEndcaseid() {
		return endcaseid;
	}

	/**
	 * @param endcaseid the endcaseid to set
	 */
	public void setEndcaseid(Integer endcaseid) {
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
	public Integer getBlankoutid() {
		return blankoutid;
	}

	/**
	 * @param blankoutid the blankoutid to set
	 */
	public void setBlankoutid(Integer blankoutid) {
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

	/**
	 * @return the makeorganidname
	 */
	public String getMakeorganidname() {
		return makeorganidname;
	}

	/**
	 * @param makeorganidname the makeorganidname to set
	 */
	public void setMakeorganidname(String makeorganidname) {
		this.makeorganidname = makeorganidname;
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
	 * @return the billtype
	 */
	public Integer getBilltype() {
		return billtype;
	}

	/**
	 * @param billtype the billtype to set
	 */
	public void setBilltype(Integer billtype) {
		this.billtype = billtype;
	}

	/**
	 * @return the billtypename
	 */
	public String getBilltypename() {
		return billtypename;
	}

	/**
	 * @param billtypename the billtypename to set
	 */
	public void setBilltypename(String billtypename) {
		this.billtypename = billtypename;
	}
	
    
    
    
}
