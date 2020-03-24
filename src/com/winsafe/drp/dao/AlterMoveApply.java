package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class AlterMoveApply implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private Date movedate;

    /** nullable persistent field */
    private String outorganid;

    /** nullable persistent field */
    private Double totalsum;

    /** nullable persistent field */
    private Integer paymentmode;

    /** nullable persistent field */
    private Integer invmsg;

    /** nullable persistent field */
    private Integer transportmode;

    /** nullable persistent field */
    private String transportaddr;

    /** nullable persistent field */
    private String tickettitle;

    /** nullable persistent field */
    private String inwarehouseid;

    /** nullable persistent field */
    private String movecause;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Integer isaudit;

    /** nullable persistent field */
    private Integer auditid;

    /** nullable persistent field */
    private Date auditdate;

    /** nullable persistent field */
    private Integer isratify;

    /** nullable persistent field */
    private Integer ratifyid;

    /** nullable persistent field */
    private Date ratifydate;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private String makeorganidname;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

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
    private Integer istrans;

    /** nullable persistent field */
    private String keyscontent;

    /** full constructor */
    public AlterMoveApply(String id, Date movedate, String outorganid, Double totalsum, Integer paymentmode, Integer invmsg, Integer transportmode, String transportaddr, String tickettitle, String inwarehouseid, String movecause, String remark, Integer isaudit, Integer auditid, Date auditdate, Integer isratify, Integer ratifyid, Date ratifydate, String makeorganid, String makeorganidname, Integer makedeptid, Integer makeid, Date makedate, Integer isblankout, Integer blankoutid, Date blankoutdate, String blankoutreason, Integer printtimes, Integer istrans, String keyscontent) {
        this.id = id;
        this.movedate = movedate;
        this.outorganid = outorganid;
        this.totalsum = totalsum;
        this.paymentmode = paymentmode;
        this.invmsg = invmsg;
        this.transportmode = transportmode;
        this.transportaddr = transportaddr;
        this.tickettitle = tickettitle;
        this.inwarehouseid = inwarehouseid;
        this.movecause = movecause;
        this.remark = remark;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
        this.isratify = isratify;
        this.ratifyid = ratifyid;
        this.ratifydate = ratifydate;
        this.makeorganid = makeorganid;
        this.makeorganidname = makeorganidname;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.isblankout = isblankout;
        this.blankoutid = blankoutid;
        this.blankoutdate = blankoutdate;
        this.blankoutreason = blankoutreason;
        this.printtimes = printtimes;
        this.istrans = istrans;
        this.keyscontent = keyscontent;
    }

    /** default constructor */
    public AlterMoveApply() {
    }

    /** minimal constructor */
    public AlterMoveApply(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getMovedate() {
        return this.movedate;
    }

    public void setMovedate(Date movedate) {
        this.movedate = movedate;
    }

    public String getOutorganid() {
        return this.outorganid;
    }

    public void setOutorganid(String outorganid) {
        this.outorganid = outorganid;
    }

    public Double getTotalsum() {
        return this.totalsum;
    }

    public void setTotalsum(Double totalsum) {
        this.totalsum = totalsum;
    }

    public Integer getPaymentmode() {
        return this.paymentmode;
    }

    public void setPaymentmode(Integer paymentmode) {
        this.paymentmode = paymentmode;
    }

    public Integer getInvmsg() {
        return this.invmsg;
    }

    public void setInvmsg(Integer invmsg) {
        this.invmsg = invmsg;
    }

    public Integer getTransportmode() {
        return this.transportmode;
    }

    public void setTransportmode(Integer transportmode) {
        this.transportmode = transportmode;
    }

    public String getTransportaddr() {
        return this.transportaddr;
    }

    public void setTransportaddr(String transportaddr) {
        this.transportaddr = transportaddr;
    }

    public String getTickettitle() {
        return this.tickettitle;
    }

    public void setTickettitle(String tickettitle) {
        this.tickettitle = tickettitle;
    }

    public String getInwarehouseid() {
        return this.inwarehouseid;
    }

    public void setInwarehouseid(String inwarehouseid) {
        this.inwarehouseid = inwarehouseid;
    }

    public String getMovecause() {
        return this.movecause;
    }

    public void setMovecause(String movecause) {
        this.movecause = movecause;
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

    public Integer getIsratify() {
        return this.isratify;
    }

    public void setIsratify(Integer isratify) {
        this.isratify = isratify;
    }

    public Integer getRatifyid() {
        return this.ratifyid;
    }

    public void setRatifyid(Integer ratifyid) {
        this.ratifyid = ratifyid;
    }

    public Date getRatifydate() {
        return this.ratifydate;
    }

    public void setRatifydate(Date ratifydate) {
        this.ratifydate = ratifydate;
    }

    public String getMakeorganid() {
        return this.makeorganid;
    }

    public void setMakeorganid(String makeorganid) {
        this.makeorganid = makeorganid;
    }

    public String getMakeorganidname() {
        return this.makeorganidname;
    }

    public void setMakeorganidname(String makeorganidname) {
        this.makeorganidname = makeorganidname;
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

    public Integer getIstrans() {
        return this.istrans;
    }

    public void setIstrans(Integer istrans) {
        this.istrans = istrans;
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
        if ( !(other instanceof AlterMoveApply) ) return false;
        AlterMoveApply castOther = (AlterMoveApply) other;
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
