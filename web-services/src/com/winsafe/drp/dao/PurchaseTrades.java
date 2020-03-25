package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class PurchaseTrades implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String warehouseinid;

    /** nullable persistent field */
    private String warehouseoutid;

    /** nullable persistent field */
    private String provideid;

    /** nullable persistent field */
    private String providename;

    /** nullable persistent field */
    private String plinkman;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private Date tradesdate;

    /** nullable persistent field */
    private String newbatch;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Integer isaudit;

    /** nullable persistent field */
    private Integer auditid;

    /** nullable persistent field */
    private Date auditdate;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Integer isreceive;

    /** nullable persistent field */
    private Integer receiveid;

    /** nullable persistent field */
    private Date receivedate;

    /** nullable persistent field */
    private Integer isblankout;

    /** nullable persistent field */
    private Integer blankoutid;

    /** nullable persistent field */
    private Date blankoutdate;

    /** nullable persistent field */
    private String blankoutreason;

    /** nullable persistent field */
    private Integer printtimes;

    /** nullable persistent field */
    private Integer takestatus;

    /** nullable persistent field */
    private String keyscontent;

    /** full constructor */
    public PurchaseTrades(String id, String warehouseinid, String warehouseoutid, String provideid, String providename, String plinkman, String tel, Date tradesdate, String newbatch, String remark, Integer isaudit, Integer auditid, Date auditdate, String makeorganid, Integer makedeptid, Integer makeid, Date makedate, Integer isreceive, Integer receiveid, Date receivedate, Integer isblankout, Integer blankoutid, Date blankoutdate, String blankoutreason, Integer printtimes, Integer takestatus, String keyscontent) {
        this.id = id;
        this.warehouseinid = warehouseinid;
        this.warehouseoutid = warehouseoutid;
        this.provideid = provideid;
        this.providename = providename;
        this.plinkman = plinkman;
        this.tel = tel;
        this.tradesdate = tradesdate;
        this.newbatch = newbatch;
        this.remark = remark;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.isreceive = isreceive;
        this.receiveid = receiveid;
        this.receivedate = receivedate;
        this.isblankout = isblankout;
        this.blankoutid = blankoutid;
        this.blankoutdate = blankoutdate;
        this.blankoutreason = blankoutreason;
        this.printtimes = printtimes;
        this.takestatus = takestatus;
        this.keyscontent = keyscontent;
    }

    /** default constructor */
    public PurchaseTrades() {
    }

    /** minimal constructor */
    public PurchaseTrades(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWarehouseinid() {
        return this.warehouseinid;
    }

    public void setWarehouseinid(String warehouseinid) {
        this.warehouseinid = warehouseinid;
    }

    public String getWarehouseoutid() {
        return this.warehouseoutid;
    }

    public void setWarehouseoutid(String warehouseoutid) {
        this.warehouseoutid = warehouseoutid;
    }

    public String getProvideid() {
        return this.provideid;
    }

    public void setProvideid(String provideid) {
        this.provideid = provideid;
    }

    public String getProvidename() {
        return this.providename;
    }

    public void setProvidename(String providename) {
        this.providename = providename;
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

    public Date getTradesdate() {
        return this.tradesdate;
    }

    public void setTradesdate(Date tradesdate) {
        this.tradesdate = tradesdate;
    }

    public String getNewbatch() {
        return this.newbatch;
    }

    public void setNewbatch(String newbatch) {
        this.newbatch = newbatch;
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

    public Integer getIsreceive() {
        return this.isreceive;
    }

    public void setIsreceive(Integer isreceive) {
        this.isreceive = isreceive;
    }

    public Integer getReceiveid() {
        return this.receiveid;
    }

    public void setReceiveid(Integer receiveid) {
        this.receiveid = receiveid;
    }

    public Date getReceivedate() {
        return this.receivedate;
    }

    public void setReceivedate(Date receivedate) {
        this.receivedate = receivedate;
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

    public Integer getPrinttimes() {
        return this.printtimes;
    }

    public void setPrinttimes(Integer printtimes) {
        this.printtimes = printtimes;
    }

    public Integer getTakestatus() {
        return this.takestatus;
    }

    public void setTakestatus(Integer takestatus) {
        this.takestatus = takestatus;
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
        if ( !(other instanceof PurchaseTrades) ) return false;
        PurchaseTrades castOther = (PurchaseTrades) other;
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
