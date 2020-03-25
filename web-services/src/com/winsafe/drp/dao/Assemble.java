package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Assemble implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String aproductid;

    /** nullable persistent field */
    private String aproductname;

    /** nullable persistent field */
    private String aspecmode;

    /** nullable persistent field */
    private Integer aunitid;

    /** nullable persistent field */
    private Double aquantity;

    /** nullable persistent field */
    private Double cquantity;

    /** nullable persistent field */
    private Integer adept;

    /** nullable persistent field */
    private Date completeintenddate;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Integer isaudit;

    /** nullable persistent field */
    private Integer auditid;

    /** nullable persistent field */
    private Date auditdate;

    /** nullable persistent field */
    private Integer isendcase;

    /** nullable persistent field */
    private Integer printtimes;

    /** nullable persistent field */
    private String keyscontent;

    /** full constructor */
    public Assemble(String id, String aproductid, String aproductname, String aspecmode, Integer aunitid, Double aquantity, Double cquantity, Integer adept, Date completeintenddate, String remark, String makeorganid, Integer makedeptid, Integer makeid, Date makedate, Integer isaudit, Integer auditid, Date auditdate, Integer isendcase, Integer printtimes, String keyscontent) {
        this.id = id;
        this.aproductid = aproductid;
        this.aproductname = aproductname;
        this.aspecmode = aspecmode;
        this.aunitid = aunitid;
        this.aquantity = aquantity;
        this.cquantity = cquantity;
        this.adept = adept;
        this.completeintenddate = completeintenddate;
        this.remark = remark;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
        this.isendcase = isendcase;
        this.printtimes = printtimes;
        this.keyscontent = keyscontent;
    }

    /** default constructor */
    public Assemble() {
    }

    /** minimal constructor */
    public Assemble(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAproductid() {
        return this.aproductid;
    }

    public void setAproductid(String aproductid) {
        this.aproductid = aproductid;
    }

    public String getAproductname() {
        return this.aproductname;
    }

    public void setAproductname(String aproductname) {
        this.aproductname = aproductname;
    }

    public String getAspecmode() {
        return this.aspecmode;
    }

    public void setAspecmode(String aspecmode) {
        this.aspecmode = aspecmode;
    }

    public Integer getAunitid() {
        return this.aunitid;
    }

    public void setAunitid(Integer aunitid) {
        this.aunitid = aunitid;
    }

    public Double getAquantity() {
        return this.aquantity;
    }

    public void setAquantity(Double aquantity) {
        this.aquantity = aquantity;
    }

    public Double getCquantity() {
        return this.cquantity;
    }

    public void setCquantity(Double cquantity) {
        this.cquantity = cquantity;
    }

    public Integer getAdept() {
        return this.adept;
    }

    public void setAdept(Integer adept) {
        this.adept = adept;
    }

    public Date getCompleteintenddate() {
        return this.completeintenddate;
    }

    public void setCompleteintenddate(Date completeintenddate) {
        this.completeintenddate = completeintenddate;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Integer getPrinttimes() {
        return this.printtimes;
    }

    public void setPrinttimes(Integer printtimes) {
        this.printtimes = printtimes;
    }

    public String getKeyscontent() {
        return this.keyscontent;
    }

    public void setKeyscontent(String keyscontent) {
        this.keyscontent = keyscontent;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Assemble) ) return false;
        Assemble castOther = (Assemble) other;
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
