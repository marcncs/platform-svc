package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class GatherNotify extends ActionForm implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private String clinkman;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private Long saleid;

    /** nullable persistent field */
    private Long saledept;

    /** nullable persistent field */
    private String billno;

    /** nullable persistent field */
    private Integer paymentmode;

    /** nullable persistent field */
    private String bankaccount;

    /** nullable persistent field */
    private Double totalsum;

    /** nullable persistent field */
    private String memo;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Long makedeptid;

    /** nullable persistent field */
    private Long makeid;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Integer isendcase;

    /** nullable persistent field */
    private Long endcaseid;

    /** nullable persistent field */
    private Date endcasedate;

    /** full constructor */
    public GatherNotify(Long id, String cid, String cname, String clinkman, String tel, Long saleid, Long saledept, String billno, Integer paymentmode, String bankaccount, Double totalsum, String memo, String makeorganid, Long makedeptid, Long makeid, Date makedate, Integer isendcase, Long endcaseid, Date endcasedate) {
        this.id = id;
        this.cid = cid;
        this.cname = cname;
        this.clinkman = clinkman;
        this.tel = tel;
        this.saleid = saleid;
        this.saledept = saledept;
        this.billno = billno;
        this.paymentmode = paymentmode;
        this.bankaccount = bankaccount;
        this.totalsum = totalsum;
        this.memo = memo;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
        this.isendcase = isendcase;
        this.endcaseid = endcaseid;
        this.endcasedate = endcasedate;
    }

    /** default constructor */
    public GatherNotify() {
    }

    /** minimal constructor */
    public GatherNotify(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getSaleid() {
        return this.saleid;
    }

    public void setSaleid(Long saleid) {
        this.saleid = saleid;
    }

    public Long getSaledept() {
        return this.saledept;
    }

    public void setSaledept(Long saledept) {
        this.saledept = saledept;
    }

    public String getBillno() {
        return this.billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public Integer getPaymentmode() {
        return this.paymentmode;
    }

    public void setPaymentmode(Integer paymentmode) {
        this.paymentmode = paymentmode;
    }

    public String getBankaccount() {
        return this.bankaccount;
    }

    public void setBankaccount(String bankaccount) {
        this.bankaccount = bankaccount;
    }

    public Double getTotalsum() {
        return this.totalsum;
    }

    public void setTotalsum(Double totalsum) {
        this.totalsum = totalsum;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    public Integer getIsendcase() {
        return this.isendcase;
    }

    public void setIsendcase(Integer isendcase) {
        this.isendcase = isendcase;
    }

    public Long getEndcaseid() {
        return this.endcaseid;
    }

    public void setEndcaseid(Long endcaseid) {
        this.endcaseid = endcaseid;
    }

    public Date getEndcasedate() {
        return this.endcasedate;
    }

    public void setEndcasedate(Date endcasedate) {
        this.endcasedate = endcasedate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof GatherNotify) ) return false;
        GatherNotify castOther = (GatherNotify) other;
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
