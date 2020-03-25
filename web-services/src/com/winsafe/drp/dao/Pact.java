package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Pact implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String pactcode;

    /** nullable persistent field */
    private Integer pacttype;

    /** nullable persistent field */
    private Integer userid;

    /** nullable persistent field */
    private Integer objsort;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private String cdeputy;

    /** nullable persistent field */
    private Date signdate;

    /** nullable persistent field */
    private String provide;

    /** nullable persistent field */
    private String pdeputy;

    /** nullable persistent field */
    private String signaddr;

    /** nullable persistent field */
    private String pactscopy;

    /** nullable persistent field */
    private String affix;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** full constructor */
    public Pact(Integer id, String pactcode, Integer pacttype, Integer userid, Integer objsort, String cid, String cname, String cdeputy, Date signdate, String provide, String pdeputy, String signaddr, String pactscopy, String affix, String makeorganid, Integer makedeptid, Integer makeid, Date makedate) {
        this.id = id;
        this.pactcode = pactcode;
        this.pacttype = pacttype;
        this.userid = userid;
        this.objsort = objsort;
        this.cid = cid;
        this.cname = cname;
        this.cdeputy = cdeputy;
        this.signdate = signdate;
        this.provide = provide;
        this.pdeputy = pdeputy;
        this.signaddr = signaddr;
        this.pactscopy = pactscopy;
        this.affix = affix;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
    }

    /** default constructor */
    public Pact() {
    }

    /** minimal constructor */
    public Pact(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPactcode() {
        return this.pactcode;
    }

    public void setPactcode(String pactcode) {
        this.pactcode = pactcode;
    }

    public Integer getPacttype() {
        return this.pacttype;
    }

    public void setPacttype(Integer pacttype) {
        this.pacttype = pacttype;
    }

    public Integer getUserid() {
        return this.userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
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

    public String getCdeputy() {
        return this.cdeputy;
    }

    public void setCdeputy(String cdeputy) {
        this.cdeputy = cdeputy;
    }

    public Date getSigndate() {
        return this.signdate;
    }

    public void setSigndate(Date signdate) {
        this.signdate = signdate;
    }

    public String getProvide() {
        return this.provide;
    }

    public void setProvide(String provide) {
        this.provide = provide;
    }

    public String getPdeputy() {
        return this.pdeputy;
    }

    public void setPdeputy(String pdeputy) {
        this.pdeputy = pdeputy;
    }

    public String getSignaddr() {
        return this.signaddr;
    }

    public void setSignaddr(String signaddr) {
        this.signaddr = signaddr;
    }

    public String getPactscopy() {
        return this.pactscopy;
    }

    public void setPactscopy(String pactscopy) {
        this.pactscopy = pactscopy;
    }

    public String getAffix() {
        return this.affix;
    }

    public void setAffix(String affix) {
        this.affix = affix;
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
        if ( !(other instanceof Pact) ) return false;
        Pact castOther = (Pact) other;
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
