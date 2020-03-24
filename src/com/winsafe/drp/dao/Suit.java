package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Suit implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private Integer objsort;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private Integer suitcontent;

    /** nullable persistent field */
    private Integer suitway;

    /** nullable persistent field */
    private String suittools;

    /** nullable persistent field */
    private String suitstatus;

    /** nullable persistent field */
    private Date suitdate;

    /** nullable persistent field */
    private String memo;

    /** nullable persistent field */
    private Integer isdeal;

    /** nullable persistent field */
    private Integer dealway;

    /** nullable persistent field */
    private Integer dealuser;

    /** nullable persistent field */
    private Date dealdate;

    /** nullable persistent field */
    private String dealcontent;

    /** nullable persistent field */
    private String dealfinal;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** full constructor */
    public Suit(String id, Integer objsort, String cid, String cname, Integer suitcontent, Integer suitway, String suittools, String suitstatus, Date suitdate, String memo, Integer isdeal, Integer dealway, Integer dealuser, Date dealdate, String dealcontent, String dealfinal, String makeorganid, Integer makedeptid, Integer makeid, Date makedate) {
        this.id = id;
        this.objsort = objsort;
        this.cid = cid;
        this.cname = cname;
        this.suitcontent = suitcontent;
        this.suitway = suitway;
        this.suittools = suittools;
        this.suitstatus = suitstatus;
        this.suitdate = suitdate;
        this.memo = memo;
        this.isdeal = isdeal;
        this.dealway = dealway;
        this.dealuser = dealuser;
        this.dealdate = dealdate;
        this.dealcontent = dealcontent;
        this.dealfinal = dealfinal;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
    }

    /** default constructor */
    public Suit() {
    }

    /** minimal constructor */
    public Suit(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
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

    public Integer getSuitcontent() {
        return this.suitcontent;
    }

    public void setSuitcontent(Integer suitcontent) {
        this.suitcontent = suitcontent;
    }

    public Integer getSuitway() {
        return this.suitway;
    }

    public void setSuitway(Integer suitway) {
        this.suitway = suitway;
    }

    public String getSuittools() {
        return this.suittools;
    }

    public void setSuittools(String suittools) {
        this.suittools = suittools;
    }

    public String getSuitstatus() {
        return this.suitstatus;
    }

    public void setSuitstatus(String suitstatus) {
        this.suitstatus = suitstatus;
    }

    public Date getSuitdate() {
        return this.suitdate;
    }

    public void setSuitdate(Date suitdate) {
        this.suitdate = suitdate;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getIsdeal() {
        return this.isdeal;
    }

    public void setIsdeal(Integer isdeal) {
        this.isdeal = isdeal;
    }

    public Integer getDealway() {
        return this.dealway;
    }

    public void setDealway(Integer dealway) {
        this.dealway = dealway;
    }

    public Integer getDealuser() {
        return this.dealuser;
    }

    public void setDealuser(Integer dealuser) {
        this.dealuser = dealuser;
    }

    public Date getDealdate() {
        return this.dealdate;
    }

    public void setDealdate(Date dealdate) {
        this.dealdate = dealdate;
    }

    public String getDealcontent() {
        return this.dealcontent;
    }

    public void setDealcontent(String dealcontent) {
        this.dealcontent = dealcontent;
    }

    public String getDealfinal() {
        return this.dealfinal;
    }

    public void setDealfinal(String dealfinal) {
        this.dealfinal = dealfinal;
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
        if ( !(other instanceof Suit) ) return false;
        Suit castOther = (Suit) other;
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
