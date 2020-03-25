package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ConsignMachin implements Serializable {

    /** identifier field */

    /** nullable persistent field */
    private String id;

    /** nullable persistent field */
    private String pid;

    /** nullable persistent field */
    private String plinkman;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private Integer paymode;

    /** nullable persistent field */
    private Double ctotalsum;

    /** nullable persistent field */
    private String cproductid;
    
    private String cproductname;

    /** nullable persistent field */
    private String cspecmode;

    /** nullable persistent field */
    private Integer cunitid;

    /** nullable persistent field */
    private Double cquantity;

    /** nullable persistent field */
    private Double cunitprice;

    /** nullable persistent field */
    private Double completequantity;

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
    public ConsignMachin(String cproductname, String id, String pid, String plinkman, String tel, Integer paymode, Double ctotalsum, String cproductid, String cspecmode, Integer cunitid, Double cquantity, Double cunitprice, Double completequantity, Date completeintenddate, String remark, String makeorganid, Integer makedeptid, Integer makeid, Date makedate, Integer isaudit, Integer auditid, Date auditdate, Integer isendcase, Integer printtimes, String keyscontent) {
        this.cproductname = cproductname;
        this.id = id;
        this.pid = pid;
        this.plinkman = plinkman;
        this.tel = tel;
        this.paymode = paymode;
        this.ctotalsum = ctotalsum;
        this.cproductid = cproductid;
        this.cspecmode = cspecmode;
        this.cunitid = cunitid;
        this.cquantity = cquantity;
        this.cunitprice = cunitprice;
        this.completequantity = completequantity;
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
    public ConsignMachin() {
    }

    /** minimal constructor */
    public ConsignMachin(String cproductname) {
        this.cproductname = cproductname;
    }

    public String getCproductname() {
        return this.cproductname;
    }

    public void setCproductname(String cproductname) {
        this.cproductname = cproductname;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPlinkman() {
        return this.plinkman;
    }

    public void setPlinkman(String plinkman) {
        this.plinkman = plinkman;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getPaymode() {
        return this.paymode;
    }

    public void setPaymode(Integer paymode) {
        this.paymode = paymode;
    }

    public Double getCtotalsum() {
        return this.ctotalsum;
    }

    public void setCtotalsum(Double ctotalsum) {
        this.ctotalsum = ctotalsum;
    }

    public String getCproductid() {
        return this.cproductid;
    }

    public void setCproductid(String cproductid) {
        this.cproductid = cproductid;
    }

    public String getCspecmode() {
        return this.cspecmode;
    }

    public void setCspecmode(String cspecmode) {
        this.cspecmode = cspecmode;
    }

    public Integer getCunitid() {
        return this.cunitid;
    }

    public void setCunitid(Integer cunitid) {
        this.cunitid = cunitid;
    }

    public Double getCquantity() {
        return this.cquantity;
    }

    public void setCquantity(Double cquantity) {
        this.cquantity = cquantity;
    }

    public Double getCunitprice() {
        return this.cunitprice;
    }

    public void setCunitprice(Double cunitprice) {
        this.cunitprice = cunitprice;
    }

    public Double getCompletequantity() {
        return this.completequantity;
    }

    public void setCompletequantity(Double completequantity) {
        this.completequantity = completequantity;
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
            .append("cproductname", getCproductname())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ConsignMachin) ) return false;
        ConsignMachin castOther = (ConsignMachin) other;
        return new EqualsBuilder()
            .append(this.getCproductname(), castOther.getCproductname())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCproductname())
            .toHashCode();
    }

}
