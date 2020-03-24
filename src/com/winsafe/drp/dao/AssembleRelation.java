package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class AssembleRelation implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String arproductid;

    /** nullable persistent field */
    private String arproductname;

    /** nullable persistent field */
    private String arspecmode;

    /** nullable persistent field */
    private Integer arunitid;

    /** nullable persistent field */
    private Double arquantity;

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
    private String keyscontent;

    /** full constructor */
    public AssembleRelation(String id, String arproductid, String arproductname, String arspecmode, Integer arunitid, Double arquantity, String remark, String makeorganid, Integer makedeptid, Integer makeid, Date makedate, Integer isaudit, Integer auditid, Date auditdate, String keyscontent) {
        this.id = id;
        this.arproductid = arproductid;
        this.arproductname = arproductname;
        this.arspecmode = arspecmode;
        this.arunitid = arunitid;
        this.arquantity = arquantity;
        this.remark = remark;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
        this.keyscontent = keyscontent;
    }

    /** default constructor */
    public AssembleRelation() {
    }

    /** minimal constructor */
    public AssembleRelation(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArproductid() {
        return this.arproductid;
    }

    public void setArproductid(String arproductid) {
        this.arproductid = arproductid;
    }

    public String getArproductname() {
        return this.arproductname;
    }

    public void setArproductname(String arproductname) {
        this.arproductname = arproductname;
    }

    public String getArspecmode() {
        return this.arspecmode;
    }

    public void setArspecmode(String arspecmode) {
        this.arspecmode = arspecmode;
    }

    public Integer getArunitid() {
        return this.arunitid;
    }

    public void setArunitid(Integer arunitid) {
        this.arunitid = arunitid;
    }

    public Double getArquantity() {
        return this.arquantity;
    }

    public void setArquantity(Double arquantity) {
        this.arquantity = arquantity;
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
        if ( !(other instanceof AssembleRelation) ) return false;
        AssembleRelation castOther = (AssembleRelation) other;
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
