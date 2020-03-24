package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class PurchaseInquire implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String ppid;

    /** nullable persistent field */
    private String inquiretitle;

    /** nullable persistent field */
    private String pid;

    /** nullable persistent field */
    private String plinkman;

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
    private Integer validdate;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Integer iscaseend;

    /** nullable persistent field */
    private String keyscontent;

    /** full constructor */
    public PurchaseInquire(Integer id, String ppid, String inquiretitle, String pid, String plinkman, String makeorganid, Integer makedeptid, Integer makeid, Date makedate, Integer isaudit, Integer auditid, Date auditdate, Integer validdate, String remark, Integer iscaseend, String keyscontent) {
        this.id = id;
        this.ppid = ppid;
        this.inquiretitle = inquiretitle;
        this.pid = pid;
        this.plinkman = plinkman;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
        this.validdate = validdate;
        this.remark = remark;
        this.iscaseend = iscaseend;
        this.keyscontent = keyscontent;
    }

    /** default constructor */
    public PurchaseInquire() {
    }

    /** minimal constructor */
    public PurchaseInquire(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPpid() {
        return this.ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public String getInquiretitle() {
        return this.inquiretitle;
    }

    public void setInquiretitle(String inquiretitle) {
        this.inquiretitle = inquiretitle;
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

    public Integer getValiddate() {
        return this.validdate;
    }

    public void setValiddate(Integer validdate) {
        this.validdate = validdate;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIscaseend() {
        return this.iscaseend;
    }

    public void setIscaseend(Integer iscaseend) {
        this.iscaseend = iscaseend;
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
        if ( !(other instanceof PurchaseInquire) ) return false;
        PurchaseInquire castOther = (PurchaseInquire) other;
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
