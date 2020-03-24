package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class BorrowIncome implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private Long warehouseid;

    /** nullable persistent field */
    private Long warehouseout;

    /** nullable persistent field */
    private String poid;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private String plinkman;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private String batch;

    /** nullable persistent field */
    private Date incomedate;

    /** nullable persistent field */
    private Double totalsum;

    /** nullable persistent field */
    private String remark;

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
    private Integer isbacktrack;

    /** nullable persistent field */
    private Long backtrackid;

    /** nullable persistent field */
    private Date backtrackdate;

    /** nullable persistent field */
    private Integer istranswithdraw;

    /** nullable persistent field */
    private Long transwithdrawid;

    /** nullable persistent field */
    private Date transwithdrawdate;

    /** nullable persistent field */
    private Integer printtimes;

    /** full constructor */
    public BorrowIncome(String id, Long warehouseid, Long warehouseout, String poid, String cid, String cname, String plinkman, String tel, String batch, Date incomedate, Double totalsum, String remark, Integer isaudit, Long auditid, Date auditdate, Long makeid, Date makedate, Integer isbacktrack, Long backtrackid, Date backtrackdate, Integer istranswithdraw, Long transwithdrawid, Date transwithdrawdate, Integer printtimes) {
        this.id = id;
        this.warehouseid = warehouseid;
        this.warehouseout = warehouseout;
        this.poid = poid;
        this.cid = cid;
        this.cname = cname;
        this.plinkman = plinkman;
        this.tel = tel;
        this.batch = batch;
        this.incomedate = incomedate;
        this.totalsum = totalsum;
        this.remark = remark;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
        this.makeid = makeid;
        this.makedate = makedate;
        this.isbacktrack = isbacktrack;
        this.backtrackid = backtrackid;
        this.backtrackdate = backtrackdate;
        this.istranswithdraw = istranswithdraw;
        this.transwithdrawid = transwithdrawid;
        this.transwithdrawdate = transwithdrawdate;
        this.printtimes = printtimes;
    }

    /** default constructor */
    public BorrowIncome() {
    }

    /** minimal constructor */
    public BorrowIncome(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getWarehouseid() {
        return this.warehouseid;
    }

    public void setWarehouseid(Long warehouseid) {
        this.warehouseid = warehouseid;
    }

    public Long getWarehouseout() {
        return this.warehouseout;
    }

    public void setWarehouseout(Long warehouseout) {
        this.warehouseout = warehouseout;
    }

    public String getPoid() {
        return this.poid;
    }

    public void setPoid(String poid) {
        this.poid = poid;
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

    public Date getIncomedate() {
        return this.incomedate;
    }

    public void setIncomedate(Date incomedate) {
        this.incomedate = incomedate;
    }

    public Double getTotalsum() {
        return this.totalsum;
    }

    public void setTotalsum(Double totalsum) {
        this.totalsum = totalsum;
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

    public Integer getIsbacktrack() {
        return this.isbacktrack;
    }

    public void setIsbacktrack(Integer isbacktrack) {
        this.isbacktrack = isbacktrack;
    }

    public Long getBacktrackid() {
        return this.backtrackid;
    }

    public void setBacktrackid(Long backtrackid) {
        this.backtrackid = backtrackid;
    }

    public Date getBacktrackdate() {
        return this.backtrackdate;
    }

    public void setBacktrackdate(Date backtrackdate) {
        this.backtrackdate = backtrackdate;
    }

    public Integer getIstranswithdraw() {
        return this.istranswithdraw;
    }

    public void setIstranswithdraw(Integer istranswithdraw) {
        this.istranswithdraw = istranswithdraw;
    }

    public Long getTranswithdrawid() {
        return this.transwithdrawid;
    }

    public void setTranswithdrawid(Long transwithdrawid) {
        this.transwithdrawid = transwithdrawid;
    }

    public Date getTranswithdrawdate() {
        return this.transwithdrawdate;
    }

    public void setTranswithdrawdate(Date transwithdrawdate) {
        this.transwithdrawdate = transwithdrawdate;
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
        if ( !(other instanceof BorrowIncome) ) return false;
        BorrowIncome castOther = (BorrowIncome) other;
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
