package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class PurchaseOrder extends ActionForm implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String ppid;

    /** nullable persistent field */
    private String pid;

    /** nullable persistent field */
    private String plinkman;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private String batch;

    /** nullable persistent field */
    private Integer purchasedept;

    /** nullable persistent field */
    private Integer purchaseid;

    /** nullable persistent field */
    private Integer paymentmode;

    /** nullable persistent field */
    private Double totalsum;

    /** nullable persistent field */
    private Date receivedate;

    /** nullable persistent field */
    private String receiveaddr;

    /** nullable persistent field */
    private Integer isrefer;

    /** nullable persistent field */
    private Integer approvestatus;

    /** nullable persistent field */
    private Date approvedate;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private String remark;

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
    private Integer ischange;

    /** nullable persistent field */
    private Integer printtimes;

    /** full constructor */
    public PurchaseOrder(String id, String ppid, String pid, String plinkman, String tel, String batch, Integer purchasedept, Integer purchaseid, Integer paymentmode, Double totalsum, Date receivedate, String receiveaddr, Integer isrefer, Integer approvestatus, Date approvedate, String makeorganid, Integer makedeptid, Integer makeid, Date makedate, String remark, Integer isendcase, Integer endcaseid, Date endcasedate, Integer isblankout, Integer blankoutid, Date blankoutdate, Integer ischange, Integer printtimes) {
        this.id = id;
        this.ppid = ppid;
        this.pid = pid;
        this.plinkman = plinkman;
        this.tel = tel;
        this.batch = batch;
        this.purchasedept = purchasedept;
        this.purchaseid = purchaseid;
        this.paymentmode = paymentmode;
        this.totalsum = totalsum;
        this.receivedate = receivedate;
        this.receiveaddr = receiveaddr;
        this.isrefer = isrefer;
        this.approvestatus = approvestatus;
        this.approvedate = approvedate;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.remark = remark;
        this.isendcase = isendcase;
        this.endcaseid = endcaseid;
        this.endcasedate = endcasedate;
        this.isblankout = isblankout;
        this.blankoutid = blankoutid;
        this.blankoutdate = blankoutdate;
        this.ischange = ischange;
        this.printtimes = printtimes;
    }

    /** default constructor */
    public PurchaseOrder() {
    }

    /** minimal constructor */
    public PurchaseOrder(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPpid() {
        return this.ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
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

    public String getBatch() {
        return this.batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Integer getPurchasedept() {
        return this.purchasedept;
    }

    public void setPurchasedept(Integer purchasedept) {
        this.purchasedept = purchasedept;
    }

    public Integer getPurchaseid() {
        return this.purchaseid;
    }

    public void setPurchaseid(Integer purchaseid) {
        this.purchaseid = purchaseid;
    }

    public Integer getPaymentmode() {
        return this.paymentmode;
    }

    public void setPaymentmode(Integer paymentmode) {
        this.paymentmode = paymentmode;
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

    public String getReceiveaddr() {
        return this.receiveaddr;
    }

    public void setReceiveaddr(String receiveaddr) {
        this.receiveaddr = receiveaddr;
    }

    public Integer getIsrefer() {
        return this.isrefer;
    }

    public void setIsrefer(Integer isrefer) {
        this.isrefer = isrefer;
    }

    public Integer getApprovestatus() {
        return this.approvestatus;
    }

    public void setApprovestatus(Integer approvestatus) {
        this.approvestatus = approvestatus;
    }

    public Date getApprovedate() {
        return this.approvedate;
    }

    public void setApprovedate(Date approvedate) {
        this.approvedate = approvedate;
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

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Integer getIschange() {
        return this.ischange;
    }

    public void setIschange(Integer ischange) {
        this.ischange = ischange;
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
        if ( !(other instanceof PurchaseOrder) ) return false;
        PurchaseOrder castOther = (PurchaseOrder) other;
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
