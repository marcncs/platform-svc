package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class IntegralOrder implements Serializable {

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

    /** nullable persistent field */
    private Integer city;

    /** nullable persistent field */
    private Integer areas;

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

    /** nullable persistent field */
    private Integer saleid;

    /** nullable persistent field */
    private Date consignmentdate;

    /** nullable persistent field */
    private Integer source;

    /** nullable persistent field */
    private Double integralsum;

    /** nullable persistent field */
    private Integer transportmode;

    /** nullable persistent field */
    private Integer transit;

    /** nullable persistent field */
    private String transportaddr;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private String equiporganid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Integer isaudit;

    /** nullable persistent field */
    private Integer auditid;

    /** nullable persistent field */
    private Date auditdate;

    /** nullable persistent field */
    private Integer isendcase;

    /** nullable persistent field */
    private Integer endcaseid;

    /** nullable persistent field */
    private Date endcasedate;

    /** nullable persistent field */
    private Integer isblankout;

    /** nullable persistent field */
    private Integer blankoutid;

    /** nullable persistent field */
    private Date blankoutdate;

    /** nullable persistent field */
    private String blankoutreason;

    /** nullable persistent field */
    private Integer takestatus;
    
    private Integer billtype;
    
    private String keyscontent;

    public String getKeyscontent() {
		return keyscontent;
	}

	public void setKeyscontent(String keyscontent) {
		this.keyscontent = keyscontent;
	}

	/** full constructor */
    public IntegralOrder(String id, String cid, String cname, String cmobile, Integer province, Integer city, Integer areas, String decideman, String decidemantel, String receiveman, String receivemobile, String receivetel, Integer saledept, Integer saleid, Date consignmentdate, Integer source, Double integralsum, Integer transportmode, Integer transit, String transportaddr, String makeorganid, Integer makedeptid, String equiporganid, Integer makeid, Date makedate, String remark, Integer isaudit, Integer auditid, Date auditdate, Integer isendcase, Integer endcaseid, Date endcasedate, Integer isblankout, Integer blankoutid, Date blankoutdate, String blankoutreason, Integer takestatus) {
        this.id = id;
        this.cid = cid;
        this.cname = cname;
        this.cmobile = cmobile;
        this.province = province;
        this.city = city;
        this.areas = areas;
        this.decideman = decideman;
        this.decidemantel = decidemantel;
        this.receiveman = receiveman;
        this.receivemobile = receivemobile;
        this.receivetel = receivetel;
        this.saledept = saledept;
        this.saleid = saleid;
        this.consignmentdate = consignmentdate;
        this.source = source;
        this.integralsum = integralsum;
        this.transportmode = transportmode;
        this.transit = transit;
        this.transportaddr = transportaddr;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.equiporganid = equiporganid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.remark = remark;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
        this.isendcase = isendcase;
        this.endcaseid = endcaseid;
        this.endcasedate = endcasedate;
        this.isblankout = isblankout;
        this.blankoutid = blankoutid;
        this.blankoutdate = blankoutdate;
        this.blankoutreason = blankoutreason;
        this.takestatus = takestatus;
    }

    /** default constructor */
    public IntegralOrder() {
    }

    /** minimal constructor */
    public IntegralOrder(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCid() {
        return this.cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return this.cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCmobile() {
        return this.cmobile;
    }

    public void setCmobile(String cmobile) {
        this.cmobile = cmobile;
    }

    public Integer getProvince() {
        return this.province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return this.city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getAreas() {
        return this.areas;
    }

    public void setAreas(Integer areas) {
        this.areas = areas;
    }

    public String getDecideman() {
        return this.decideman;
    }

    public void setDecideman(String decideman) {
        this.decideman = decideman;
    }

    public String getDecidemantel() {
        return this.decidemantel;
    }

    public void setDecidemantel(String decidemantel) {
        this.decidemantel = decidemantel;
    }

    public String getReceiveman() {
        return this.receiveman;
    }

    public void setReceiveman(String receiveman) {
        this.receiveman = receiveman;
    }

    public String getReceivemobile() {
        return this.receivemobile;
    }

    public void setReceivemobile(String receivemobile) {
        this.receivemobile = receivemobile;
    }

    public String getReceivetel() {
        return this.receivetel;
    }

    public void setReceivetel(String receivetel) {
        this.receivetel = receivetel;
    }

    public Integer getSaledept() {
        return this.saledept;
    }

    public void setSaledept(Integer saledept) {
        this.saledept = saledept;
    }

    public Integer getSaleid() {
        return this.saleid;
    }

    public void setSaleid(Integer saleid) {
        this.saleid = saleid;
    }

    public Date getConsignmentdate() {
        return this.consignmentdate;
    }

    public void setConsignmentdate(Date consignmentdate) {
        this.consignmentdate = consignmentdate;
    }

    public Integer getSource() {
        return this.source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Double getIntegralsum() {
        return this.integralsum;
    }

    public void setIntegralsum(Double integralsum) {
        this.integralsum = integralsum;
    }

    public Integer getTransportmode() {
        return this.transportmode;
    }

    public void setTransportmode(Integer transportmode) {
        this.transportmode = transportmode;
    }

    public Integer getTransit() {
        return this.transit;
    }

    public void setTransit(Integer transit) {
        this.transit = transit;
    }

    public String getTransportaddr() {
        return this.transportaddr;
    }

    public void setTransportaddr(String transportaddr) {
        this.transportaddr = transportaddr;
    }

    public String getMakeorganid() {
        return this.makeorganid;
    }

    public void setMakeorganid(String makeorganid) {
        this.makeorganid = makeorganid;
    }

    public Integer getMakedeptid() {
        return this.makedeptid;
    }

    public void setMakedeptid(Integer makedeptid) {
        this.makedeptid = makedeptid;
    }

    public String getEquiporganid() {
        return this.equiporganid;
    }

    public void setEquiporganid(String equiporganid) {
        this.equiporganid = equiporganid;
    }

    public Integer getMakeid() {
        return this.makeid;
    }

    public void setMakeid(Integer makeid) {
        this.makeid = makeid;
    }

    public Date getMakedate() {
        return this.makedate;
    }

    public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsaudit() {
        return this.isaudit;
    }

    public void setIsaudit(Integer isaudit) {
        this.isaudit = isaudit;
    }

    public Integer getAuditid() {
        return this.auditid;
    }

    public void setAuditid(Integer auditid) {
        this.auditid = auditid;
    }

    public Date getAuditdate() {
        return this.auditdate;
    }

    public void setAuditdate(Date auditdate) {
        this.auditdate = auditdate;
    }

    public Integer getIsendcase() {
        return this.isendcase;
    }

    public void setIsendcase(Integer isendcase) {
        this.isendcase = isendcase;
    }

    public Integer getEndcaseid() {
        return this.endcaseid;
    }

    public void setEndcaseid(Integer endcaseid) {
        this.endcaseid = endcaseid;
    }

    public Date getEndcasedate() {
        return this.endcasedate;
    }

    public void setEndcasedate(Date endcasedate) {
        this.endcasedate = endcasedate;
    }

    public Integer getIsblankout() {
        return this.isblankout;
    }

    public void setIsblankout(Integer isblankout) {
        this.isblankout = isblankout;
    }

    public Integer getBlankoutid() {
        return this.blankoutid;
    }

    public void setBlankoutid(Integer blankoutid) {
        this.blankoutid = blankoutid;
    }

    public Date getBlankoutdate() {
        return this.blankoutdate;
    }

    public void setBlankoutdate(Date blankoutdate) {
        this.blankoutdate = blankoutdate;
    }

    public String getBlankoutreason() {
        return this.blankoutreason;
    }

    public void setBlankoutreason(String blankoutreason) {
        this.blankoutreason = blankoutreason;
    }

    public Integer getTakestatus() {
        return this.takestatus;
    }

    public void setTakestatus(Integer takestatus) {
        this.takestatus = takestatus;
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

	public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof IntegralOrder) ) return false;
        IntegralOrder castOther = (IntegralOrder) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}
