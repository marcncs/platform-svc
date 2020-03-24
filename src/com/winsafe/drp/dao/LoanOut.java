package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class LoanOut implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String uid;

    /** nullable persistent field */
    private String uname;

    /** nullable persistent field */
    private String receiveman;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private Integer saledept;

    /** nullable persistent field */
    private Long saleid;

    /** nullable persistent field */
    private Date consignmentdate;

    /** nullable persistent field */
    private Double totalsum;

    /** nullable persistent field */
    private Integer transportmode;

    /** nullable persistent field */
    private Integer transit;

    /** nullable persistent field */
    private String transportaddr;

    /** nullable persistent field */
    private Long makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Integer isaudit;

    /** nullable persistent field */
    private Long auditid;

    /** nullable persistent field */
    private Date auditdate;

    /** nullable persistent field */
    private Integer isreceive;

    /** nullable persistent field */
    private Long receiveid;

    /** nullable persistent field */
    private Date receivedate;

    /** nullable persistent field */
    private Integer istranssale;

    /** nullable persistent field */
    private Long transsaleid;

    /** nullable persistent field */
    private Date transsaledate;

    /** nullable persistent field */
    private Integer printtimes;

    /** full constructor */
    public LoanOut(String id, String uid, String uname, String receiveman, String tel, Integer saledept, Long saleid, Date consignmentdate, Double totalsum, Integer transportmode, Integer transit, String transportaddr, Long makeid, Date makedate, String remark, Integer isaudit, Long auditid, Date auditdate, Integer isreceive, Long receiveid, Date receivedate, Integer istranssale, Long transsaleid, Date transsaledate, Integer printtimes) {
        this.id = id;
        this.uid = uid;
        this.uname = uname;
        this.receiveman = receiveman;
        this.tel = tel;
        this.saledept = saledept;
        this.saleid = saleid;
        this.consignmentdate = consignmentdate;
        this.totalsum = totalsum;
        this.transportmode = transportmode;
        this.transit = transit;
        this.transportaddr = transportaddr;
        this.makeid = makeid;
        this.makedate = makedate;
        this.remark = remark;
        this.isaudit = isaudit;
        this.auditid = auditid;
        this.auditdate = auditdate;
        this.isreceive = isreceive;
        this.receiveid = receiveid;
        this.receivedate = receivedate;
        this.istranssale = istranssale;
        this.transsaleid = transsaleid;
        this.transsaledate = transsaledate;
        this.printtimes = printtimes;
    }

    /** default constructor */
    public LoanOut() {
    }

    /** minimal constructor */
    public LoanOut(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return this.uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getReceiveman() {
        return this.receiveman;
    }

    public void setReceiveman(String receiveman) {
        this.receiveman = receiveman;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getSaledept() {
        return this.saledept;
    }

    public void setSaledept(Integer saledept) {
        this.saledept = saledept;
    }

    public Long getSaleid() {
        return this.saleid;
    }

    public void setSaleid(Long saleid) {
        this.saleid = saleid;
    }

    public Date getConsignmentdate() {
        return this.consignmentdate;
    }

    public void setConsignmentdate(Date consignmentdate) {
        this.consignmentdate = consignmentdate;
    }

    public Double getTotalsum() {
        return this.totalsum;
    }

    public void setTotalsum(Double totalsum) {
        this.totalsum = totalsum;
    }

    public Integer getTransportmode() {
        return this.transportmode;
    }

    public void setTransportmode(Integer transportmode) {
        this.transportmode = transportmode;
    }

    public Integer getTransit() {
        return this.transit;
    }

    public void setTransit(Integer transit) {
        this.transit = transit;
    }

    public String getTransportaddr() {
        return this.transportaddr;
    }

    public void setTransportaddr(String transportaddr) {
        this.transportaddr = transportaddr;
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

    public Integer getIsreceive() {
        return this.isreceive;
    }

    public void setIsreceive(Integer isreceive) {
        this.isreceive = isreceive;
    }

    public Long getReceiveid() {
        return this.receiveid;
    }

    public void setReceiveid(Long receiveid) {
        this.receiveid = receiveid;
    }

    public Date getReceivedate() {
        return this.receivedate;
    }

    public void setReceivedate(Date receivedate) {
        this.receivedate = receivedate;
    }

    public Integer getIstranssale() {
        return this.istranssale;
    }

    public void setIstranssale(Integer istranssale) {
        this.istranssale = istranssale;
    }

    public Long getTranssaleid() {
        return this.transsaleid;
    }

    public void setTranssaleid(Long transsaleid) {
        this.transsaleid = transsaleid;
    }

    public Date getTranssaledate() {
        return this.transsaledate;
    }

    public void setTranssaledate(Date transsaledate) {
        this.transsaledate = transsaledate;
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
        if ( !(other instanceof LoanOut) ) return false;
        LoanOut castOther = (LoanOut) other;
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
