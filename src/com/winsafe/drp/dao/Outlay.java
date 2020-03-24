package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class Outlay extends ActionForm implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private Integer outlayid;

    /** nullable persistent field */
    private Integer outlaydept;

    /** nullable persistent field */
    private Integer castdept;

    /** nullable persistent field */
    private Integer caster;

    /** nullable persistent field */
    private Double totaloutlay;

    /** nullable persistent field */
    private Double thisresist;

    /** nullable persistent field */
    private Double factpay;

    /** nullable persistent field */
    private Integer fundsrc;

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
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** full constructor */
    public Outlay(String id, Integer outlayid, Integer outlaydept, Integer castdept, Integer caster, Double totaloutlay, Double thisresist, Double factpay, Integer fundsrc, String remark, Integer isaudit, Integer auditid, Date auditdate, Integer isendcase, Integer endcaseid, Date endcasedate, String makeorganid, Integer makedeptid, Integer makeid, Date makedate) {
        this.id = id;
        this.outlayid = outlayid;
        this.outlaydept = outlaydept;
        this.castdept = castdept;
        this.caster = caster;
        this.totaloutlay = totaloutlay;
        this.thisresist = thisresist;
        this.factpay = factpay;
        this.fundsrc = fundsrc;
        this.remark = remark;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
        this.isendcase = isendcase;
        this.endcaseid = endcaseid;
        this.endcasedate = endcasedate;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
    }

    /** default constructor */
    public Outlay() {
    }

    /** minimal constructor */
    public Outlay(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getOutlayid() {
        return this.outlayid;
    }

    public void setOutlayid(Integer outlayid) {
        this.outlayid = outlayid;
    }

    public Integer getOutlaydept() {
        return this.outlaydept;
    }

    public void setOutlaydept(Integer outlaydept) {
        this.outlaydept = outlaydept;
    }

    public Integer getCastdept() {
        return this.castdept;
    }

    public void setCastdept(Integer castdept) {
        this.castdept = castdept;
    }

    public Integer getCaster() {
        return this.caster;
    }

    public void setCaster(Integer caster) {
        this.caster = caster;
    }

    public Double getTotaloutlay() {
        return this.totaloutlay;
    }

    public void setTotaloutlay(Double totaloutlay) {
        this.totaloutlay = totaloutlay;
    }

    public Double getThisresist() {
        return this.thisresist;
    }

    public void setThisresist(Double thisresist) {
        this.thisresist = thisresist;
    }

    public Double getFactpay() {
        return this.factpay;
    }

    public void setFactpay(Double factpay) {
        this.factpay = factpay;
    }

    public Integer getFundsrc() {
        return this.fundsrc;
    }

    public void setFundsrc(Integer fundsrc) {
        this.fundsrc = fundsrc;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Outlay) ) return false;
        Outlay castOther = (Outlay) other;
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
