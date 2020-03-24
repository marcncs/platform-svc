package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Hap implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer objsort;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private String haptitle;

    /** nullable persistent field */
    private Integer hapcontent;

    /** nullable persistent field */
    private Integer hapkind;

    /** nullable persistent field */
    private Integer hapstatus;

    /** nullable persistent field */
    private Integer hapsrc;

    /** nullable persistent field */
    private Double intend;

    /** nullable persistent field */
    private Date intenddate;

    /** nullable persistent field */
    private Date hapbegin;

    /** nullable persistent field */
    private Date hapend;

    /** nullable persistent field */
    private String memo;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** full constructor */
    public Hap(Integer id, Integer objsort, String cid, String cname, String haptitle, Integer hapcontent, Integer hapkind, Integer hapstatus, Integer hapsrc, Double intend, Date intenddate, Date hapbegin, Date hapend, String memo, String makeorganid, Integer makedeptid, Integer makeid, Date makedate) {
        this.id = id;
        this.objsort = objsort;
        this.cid = cid;
        this.cname = cname;
        this.haptitle = haptitle;
        this.hapcontent = hapcontent;
        this.hapkind = hapkind;
        this.hapstatus = hapstatus;
        this.hapsrc = hapsrc;
        this.intend = intend;
        this.intenddate = intenddate;
        this.hapbegin = hapbegin;
        this.hapend = hapend;
        this.memo = memo;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
    }

    /** default constructor */
    public Hap() {
    }

    /** minimal constructor */
    public Hap(Integer id) {
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

    public String getHaptitle() {
        return this.haptitle;
    }

    public void setHaptitle(String haptitle) {
        this.haptitle = haptitle;
    }

    public Integer getHapcontent() {
        return this.hapcontent;
    }

    public void setHapcontent(Integer hapcontent) {
        this.hapcontent = hapcontent;
    }

    public Integer getHapkind() {
        return this.hapkind;
    }

    public void setHapkind(Integer hapkind) {
        this.hapkind = hapkind;
    }

    public Integer getHapstatus() {
        return this.hapstatus;
    }

    public void setHapstatus(Integer hapstatus) {
        this.hapstatus = hapstatus;
    }

    public Integer getHapsrc() {
        return this.hapsrc;
    }

    public void setHapsrc(Integer hapsrc) {
        this.hapsrc = hapsrc;
    }

    public Double getIntend() {
        return this.intend;
    }

    public void setIntend(Double intend) {
        this.intend = intend;
    }

    public Date getIntenddate() {
        return this.intenddate;
    }

    public void setIntenddate(Date intenddate) {
        this.intenddate = intenddate;
    }

    public Date getHapbegin() {
        return this.hapbegin;
    }

    public void setHapbegin(Date hapbegin) {
        this.hapbegin = hapbegin;
    }

    public Date getHapend() {
        return this.hapend;
    }

    public void setHapend(Date hapend) {
        this.hapend = hapend;
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
        if ( !(other instanceof Hap) ) return false;
        Hap castOther = (Hap) other;
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
