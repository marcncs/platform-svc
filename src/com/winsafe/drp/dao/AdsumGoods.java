package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class AdsumGoods implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String poid;

    /** nullable persistent field */
    private String pid;

    /** nullable persistent field */
    private String plinkman;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private String obtaincode;

    /** nullable persistent field */
    private Long purchasedept;

    /** nullable persistent field */
    private Long purchaseid;

    /** nullable persistent field */
    private Double totalsum;

    /** nullable persistent field */
    private Date receivedate;

    /** nullable persistent field */
    private Integer isaudit;

    /** nullable persistent field */
    private Long auditid;

    /** nullable persistent field */
    private Date auditdate;

    /** nullable persistent field */
    private Long makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Integer printtimes;

    /** full constructor */
    public AdsumGoods(String id, String poid, String pid, String plinkman, String tel, String obtaincode, Long purchasedept, Long purchaseid, Double totalsum, Date receivedate, Integer isaudit, Long auditid, Date auditdate, Long makeid, Date makedate, String remark, Integer printtimes) {
        this.id = id;
        this.poid = poid;
        this.pid = pid;
        this.plinkman = plinkman;
        this.tel = tel;
        this.obtaincode = obtaincode;
        this.purchasedept = purchasedept;
        this.purchaseid = purchaseid;
        this.totalsum = totalsum;
        this.receivedate = receivedate;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
        this.makeid = makeid;
        this.makedate = makedate;
        this.remark = remark;
        this.printtimes = printtimes;
    }

    /** default constructor */
    public AdsumGoods() {
    }

    /** minimal constructor */
    public AdsumGoods(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoid() {
        return this.poid;
    }

    public void setPoid(String poid) {
        this.poid = poid;
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

    public String getObtaincode() {
        return this.obtaincode;
    }

    public void setObtaincode(String obtaincode) {
        this.obtaincode = obtaincode;
    }

    public Long getPurchasedept() {
        return this.purchasedept;
    }

    public void setPurchasedept(Long purchasedept) {
        this.purchasedept = purchasedept;
    }

    public Long getPurchaseid() {
        return this.purchaseid;
    }

    public void setPurchaseid(Long purchaseid) {
        this.purchaseid = purchaseid;
    }

    public Double getTotalsum() {
        return this.totalsum;
    }

    public void setTotalsum(Double totalsum) {
        this.totalsum = totalsum;
    }

    public Date getReceivedate() {
        return this.receivedate;
    }

    public void setReceivedate(Date receivedate) {
        this.receivedate = receivedate;
    }

    public Integer getIsaudit() {
        return this.isaudit;
    }

    public void setIsaudit(Integer isaudit) {
        this.isaudit = isaudit;
    }

    public Long getAuditid() {
        return this.auditid;
    }

    public void setAuditid(Long auditid) {
        this.auditid = auditid;
    }

    public Date getAuditdate() {
        return this.auditdate;
    }

    public void setAuditdate(Date auditdate) {
        this.auditdate = auditdate;
    }

    public Long getMakeid() {
        return this.makeid;
    }

    public void setMakeid(Long makeid) {
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
        if ( !(other instanceof AdsumGoods) ) return false;
        AdsumGoods castOther = (AdsumGoods) other;
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
