package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class SaleIndent implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String customerbillid;

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
    private String receiveman;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private Integer paymentmode;

    /** nullable persistent field */
    private Date consignmentdate;

    /** nullable persistent field */
    private Double totalsum;

    /** nullable persistent field */
    private Integer transportmode;
    
    private String transportaddr;

    /** nullable persistent field */
    private Integer transit;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Integer updateid;

    /** nullable persistent field */
    private Date lastupdate;

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
    private Integer printtimes;

   
    public SaleIndent(String id, String customerbillid, String cid,
			String cname, String cmobile, Integer province, Integer city,
			Integer areas, String receiveman, String tel, Integer paymentmode,
			Date consignmentdate, Double totalsum, Integer transportmode,
			String transportaddr, Integer transit, String makeorganid,
			Integer makedeptid, Integer makeid, Date makedate,
			Integer updateid, Date lastupdate, String remark, Integer isaudit,
			Integer auditid, Date auditdate, Integer isendcase,
			Integer endcaseid, Date endcasedate, Integer printtimes) {
		super();
		this.id = id;
		this.customerbillid = customerbillid;
		this.cid = cid;
		this.cname = cname;
		this.cmobile = cmobile;
		this.province = province;
		this.city = city;
		this.areas = areas;
		this.receiveman = receiveman;
		this.tel = tel;
		this.paymentmode = paymentmode;
		this.consignmentdate = consignmentdate;
		this.totalsum = totalsum;
		this.transportmode = transportmode;
		this.transportaddr = transportaddr;
		this.transit = transit;
		this.makeorganid = makeorganid;
		this.makedeptid = makedeptid;
		this.makeid = makeid;
		this.makedate = makedate;
		this.updateid = updateid;
		this.lastupdate = lastupdate;
		this.remark = remark;
		this.isaudit = isaudit;
		this.auditid = auditid;
		this.auditdate = auditdate;
		this.isendcase = isendcase;
		this.endcaseid = endcaseid;
		this.endcasedate = endcasedate;
		this.printtimes = printtimes;
	}

	public String getTransportaddr() {
		return transportaddr;
	}

	public void setTransportaddr(String transportaddr) {
		this.transportaddr = transportaddr;
	}

	/** default constructor */
    public SaleIndent() {
    }

    /** minimal constructor */
    public SaleIndent(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerbillid() {
        return this.customerbillid;
    }

    public void setCustomerbillid(String customerbillid) {
        this.customerbillid = customerbillid;
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

    public String getReceiveman() {
        return this.receiveman;
    }

    public void setReceiveman(String receiveman) {
        this.receiveman = receiveman;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getPaymentmode() {
        return this.paymentmode;
    }

    public void setPaymentmode(Integer paymentmode) {
        this.paymentmode = paymentmode;
    }

    public Date getConsignmentdate() {
        return this.consignmentdate;
    }

    public void setConsignmentdate(Date consignmentdate) {
        this.consignmentdate = consignmentdate;
    }

    public Double getTotalsum() {
        return this.totalsum;
    }

    public void setTotalsum(Double totalsum) {
        this.totalsum = totalsum;
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

    public Integer getUpdateid() {
        return this.updateid;
    }

    public void setUpdateid(Integer updateid) {
        this.updateid = updateid;
    }

    public Date getLastupdate() {
        return this.lastupdate;
    }

    public void setLastupdate(Date lastupdate) {
        this.lastupdate = lastupdate;
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

    public Integer getPrinttimes() {
        return this.printtimes;
    }

    public void setPrinttimes(Integer printtimes) {
        this.printtimes = printtimes;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof SaleIndent) ) return false;
        SaleIndent castOther = (SaleIndent) other;
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
