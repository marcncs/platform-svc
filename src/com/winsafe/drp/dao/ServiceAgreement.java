package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ServiceAgreement implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer objsort;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private String linkman;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private Integer scontent;

    /** nullable persistent field */
    private Double sfee;

    /** nullable persistent field */
    private Integer sstatus;

    /** nullable persistent field */
    private Integer rank;

    /** nullable persistent field */
    private Date sdate;

    /** nullable persistent field */
    private String question;

    /** nullable persistent field */
    private String memo;

    /** nullable persistent field */
    private Integer isallot;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** full constructor */
    public ServiceAgreement(Integer id, Integer objsort, String cid, String cname, String linkman, String tel, Integer scontent, Double sfee, Integer sstatus, Integer rank, Date sdate, String question, String memo, Integer isallot, String makeorganid, Integer makedeptid, Integer makeid, Date makedate) {
        this.id = id;
        this.objsort = objsort;
        this.cid = cid;
        this.cname = cname;
        this.linkman = linkman;
        this.tel = tel;
        this.scontent = scontent;
        this.sfee = sfee;
        this.sstatus = sstatus;
        this.rank = rank;
        this.sdate = sdate;
        this.question = question;
        this.memo = memo;
        this.isallot = isallot;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
    }

    /** default constructor */
    public ServiceAgreement() {
    }

    /** minimal constructor */
    public ServiceAgreement(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getObjsort() {
        return this.objsort;
    }

    public void setObjsort(Integer objsort) {
        this.objsort = objsort;
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

    public String getLinkman() {
        return this.linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getScontent() {
        return this.scontent;
    }

    public void setScontent(Integer scontent) {
        this.scontent = scontent;
    }

    public Double getSfee() {
        return this.sfee;
    }

    public void setSfee(Double sfee) {
        this.sfee = sfee;
    }

    public Integer getSstatus() {
        return this.sstatus;
    }

    public void setSstatus(Integer sstatus) {
        this.sstatus = sstatus;
    }

    public Integer getRank() {
        return this.rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Date getSdate() {
        return this.sdate;
    }

    public void setSdate(Date sdate) {
        this.sdate = sdate;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getIsallot() {
        return this.isallot;
    }

    public void setIsallot(Integer isallot) {
        this.isallot = isallot;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ServiceAgreement) ) return false;
        ServiceAgreement castOther = (ServiceAgreement) other;
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
