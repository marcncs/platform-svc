package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class ContactDitch extends ActionForm implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String did;

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
    
    private String nextgoal;

    /** nullable persistent field */
    private Long userid;

    /** full constructor */
    public ContactDitch(Long id, String did, Date contactdate, Integer contactmode, Integer contactproperty, String contactcontent, String feedback, String linkman, Date nextcontact, Long userid) {
        this.id = id;
        this.did = did;
        this.contactdate = contactdate;
        this.contactmode = contactmode;
        this.contactproperty = contactproperty;
        this.contactcontent = contactcontent;
        this.feedback = feedback;
        this.linkman = linkman;
        this.nextcontact = nextcontact;
        this.userid = userid;
    }

    /** default constructor */
    public ContactDitch() {
    }

    /** minimal constructor */
    public ContactDitch(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDid() {
        return this.did;
    }

    public void setDid(String did) {
        this.did = did;
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

    public Long getUserid() {
        return this.userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
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

	public String getNextgoal() {
		return nextgoal;
	}

	public void setNextgoal(String nextgoal) {
		this.nextgoal = nextgoal;
	}

}
