package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ContactLog implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer objsort;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String cname;

    /** nullable persistent field */
    private Date contactdate;

    /** nullable persistent field */
    private Integer contactmode;

    /** nullable persistent field */
    private Integer contactproperty;

    /** nullable persistent field */
    private String contactcontent;

    /** nullable persistent field */
    private String feedback;

    /** nullable persistent field */
    private String linkman;

    /** nullable persistent field */
    private Date nextcontact;

    /** nullable persistent field */
    private String nextgoal;

    /** nullable persistent field */
    private String makeorganid;

    /** nullable persistent field */
    private Integer makedeptid;

    /** nullable persistent field */
    private Integer makeid;

    /** nullable persistent field */
    private Date makedate;

    /** full constructor */
    public ContactLog(Integer id, Integer objsort, String cid, String cname, Date contactdate, Integer contactmode, Integer contactproperty, String contactcontent, String feedback, String linkman, Date nextcontact, String nextgoal, String makeorganid, Integer makedeptid, Integer makeid, Date makedate) {
        this.id = id;
        this.objsort = objsort;
        this.cid = cid;
        this.cname = cname;
        this.contactdate = contactdate;
        this.contactmode = contactmode;
        this.contactproperty = contactproperty;
        this.contactcontent = contactcontent;
        this.feedback = feedback;
        this.linkman = linkman;
        this.nextcontact = nextcontact;
        this.nextgoal = nextgoal;
        this.makeorganid = makeorganid;
        this.makedeptid = makedeptid;
        this.makeid = makeid;
        this.makedate = makedate;
    }

    /** default constructor */
    public ContactLog() {
    }

    /** minimal constructor */
    public ContactLog(Integer id) {
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

    public Date getContactdate() {
        return this.contactdate;
    }

    public void setContactdate(Date contactdate) {
        this.contactdate = contactdate;
    }

    public Integer getContactmode() {
        return this.contactmode;
    }

    public void setContactmode(Integer contactmode) {
        this.contactmode = contactmode;
    }

    public Integer getContactproperty() {
        return this.contactproperty;
    }

    public void setContactproperty(Integer contactproperty) {
        this.contactproperty = contactproperty;
    }

    public String getContactcontent() {
        return this.contactcontent;
    }

    public void setContactcontent(String contactcontent) {
        this.contactcontent = contactcontent;
    }

    public String getFeedback() {
        return this.feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getLinkman() {
        return this.linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public Date getNextcontact() {
        return this.nextcontact;
    }

    public void setNextcontact(Date nextcontact) {
        this.nextcontact = nextcontact;
    }

    public String getNextgoal() {
        return this.nextgoal;
    }

    public void setNextgoal(String nextgoal) {
        this.nextgoal = nextgoal;
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
        if ( !(other instanceof ContactLog) ) return false;
        ContactLog castOther = (ContactLog) other;
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
