package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class SaleRepair extends ActionForm implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private Long warehouseinid;

    /** nullable persistent field */
    private Long warehouseoutid;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private String clinkman;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private Date tradesdate;

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
    private String makeorganid;

    /** nullable persistent field */
    private Long makedeptid;

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
    private Integer isblankout;

    /** nullable persistent field */
    private Long blankoutid;

    /** nullable persistent field */
    private Date blankoutdate;

    /** nullable persistent field */
    private Integer printtimes;

    /** full constructor */
    public SaleRepair(String id, Long warehouseinid, Long warehouseoutid, String cid, String cname, String clinkman, String tel, Date tradesdate, Double totalsum, String remark, Integer isaudit, Long auditid, Date auditdate, String makeorganid, Long makedeptid, Long makeid, Date makedate, Integer isbacktrack, Long backtrackid, Date backtrackdate, Integer isblankout, Long blankoutid, Date blankoutdate, Integer printtimes) {
        this.id = id;
        this.warehouseinid = warehouseinid;
        this.warehouseoutid = warehouseoutid;
        this.cid = cid;
        this.cname = cname;
        this.clinkman = clinkman;
        this.tel = tel;
        this.tradesdate = tradesdate;
        this.totalsum = totalsum;
        this.remark = remark;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.isbacktrack = isbacktrack;
        this.backtrackid = backtrackid;
        this.backtrackdate = backtrackdate;
        this.isblankout = isblankout;
        this.blankoutid = blankoutid;
        this.blankoutdate = blankoutdate;
        this.printtimes = printtimes;
    }

    /** default constructor */
    public SaleRepair() {
    }

    /** minimal constructor */
    public SaleRepair(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getWarehouseinid() {
        return this.warehouseinid;
    }

    public void setWarehouseinid(Long warehouseinid) {
        this.warehouseinid = warehouseinid;
    }

    public Long getWarehouseoutid() {
        return this.warehouseoutid;
    }

    public void setWarehouseoutid(Long warehouseoutid) {
        this.warehouseoutid = warehouseoutid;
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

    public String getClinkman() {
        return this.clinkman;
    }

    public void setClinkman(String clinkman) {
        this.clinkman = clinkman;
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

    public String getMakeorganid() {
        return this.makeorganid;
    }

    public void setMakeorganid(String makeorganid) {
        this.makeorganid = makeorganid;
    }

    public Long getMakedeptid() {
        return this.makedeptid;
    }

    public void setMakedeptid(Long makedeptid) {
        this.makedeptid = makedeptid;
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

    public Integer getIsblankout() {
        return this.isblankout;
    }

    public void setIsblankout(Integer isblankout) {
        this.isblankout = isblankout;
    }

    public Long getBlankoutid() {
        return this.blankoutid;
    }

    public void setBlankoutid(Long blankoutid) {
        this.blankoutid = blankoutid;
    }

    public Date getBlankoutdate() {
        return this.blankoutdate;
    }

    public void setBlankoutdate(Date blankoutdate) {
        this.blankoutdate = blankoutdate;
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
        if ( !(other instanceof SaleRepair) ) return false;
        SaleRepair castOther = (SaleRepair) other;
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
