package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class StockCheck implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String warehouseid;

    /** nullable persistent field */
    private String warehousebit;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private String memo;

    /** nullable persistent field */
    private Integer isaudit;

    /** nullable persistent field */
    private Integer auditid;

    /** nullable persistent field */
    private Date auditdate;

    /** nullable persistent field */
    private Integer iscreate;

    /** nullable persistent field */
    private Integer isbar;

    /** nullable persistent field */
    private Integer printtimes;

    /** nullable persistent field */
    private String keyscontent;

    /** full constructor */
    public StockCheck(String id, String warehouseid, String warehousebit, String makeorganid, Integer makedeptid, Integer makeid, Date makedate, String memo, Integer isaudit, Integer auditid, Date auditdate, Integer iscreate, Integer isbar, Integer printtimes, String keyscontent) {
        this.id = id;
        this.warehouseid = warehouseid;
        this.warehousebit = warehousebit;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.memo = memo;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
        this.iscreate = iscreate;
        this.isbar = isbar;
        this.printtimes = printtimes;
        this.keyscontent = keyscontent;
    }

    /** default constructor */
    public StockCheck() {
    }

    /** minimal constructor */
    public StockCheck(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWarehouseid() {
        return this.warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid;
    }

    public String getWarehousebit() {
        return this.warehousebit;
    }

    public void setWarehousebit(String warehousebit) {
        this.warehousebit = warehousebit;
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

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    public Integer getIscreate() {
        return this.iscreate;
    }

    public void setIscreate(Integer iscreate) {
        this.iscreate = iscreate;
    }

    public Integer getIsbar() {
        return this.isbar;
    }

    public void setIsbar(Integer isbar) {
        this.isbar = isbar;
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
        if ( !(other instanceof StockCheck) ) return false;
        StockCheck castOther = (StockCheck) other;
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
