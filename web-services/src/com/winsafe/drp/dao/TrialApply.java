package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class TrialApply extends ActionForm implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String companyname;

    /** nullable persistent field */
    private String companyaddr;

    /** nullable persistent field */
    private String linkman;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private String qq;

    /** nullable persistent field */
    private String msn;

    /** nullable persistent field */
    private String email;

    /** nullable persistent field */
    private String companytel;

    /** nullable persistent field */
    private String companysite;

    /** nullable persistent field */
    private Integer intent;

    /** nullable persistent field */
    private Integer trialmode;

    /** nullable persistent field */
    private Integer route;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Date makedate;

    /** nullable persistent field */
    private Integer isdispose;

    /** full constructor */
    public TrialApply(Long id, String companyname, String companyaddr, String linkman, String tel, String qq, String msn, String email, String companytel, String companysite, Integer intent, Integer trialmode, Integer route, String remark, Date makedate, Integer isdispose) {
        this.id = id;
        this.companyname = companyname;
        this.companyaddr = companyaddr;
        this.linkman = linkman;
        this.tel = tel;
        this.qq = qq;
        this.msn = msn;
        this.email = email;
        this.companytel = companytel;
        this.companysite = companysite;
        this.intent = intent;
        this.trialmode = trialmode;
        this.route = route;
        this.remark = remark;
        this.makedate = makedate;
        this.isdispose = isdispose;
    }

    /** default constructor */
    public TrialApply() {
    }

    /** minimal constructor */
    public TrialApply(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyname() {
        return this.companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getCompanyaddr() {
        return this.companyaddr;
    }

    public void setCompanyaddr(String companyaddr) {
        this.companyaddr = companyaddr;
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

    public String getQq() {
        return this.qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getMsn() {
        return this.msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanytel() {
        return this.companytel;
    }

    public void setCompanytel(String companytel) {
        this.companytel = companytel;
    }

    public String getCompanysite() {
        return this.companysite;
    }

    public void setCompanysite(String companysite) {
        this.companysite = companysite;
    }

    public Integer getIntent() {
        return this.intent;
    }

    public void setIntent(Integer intent) {
        this.intent = intent;
    }

    public Integer getTrialmode() {
        return this.trialmode;
    }

    public void setTrialmode(Integer trialmode) {
        this.trialmode = trialmode;
    }

    public Integer getRoute() {
        return this.route;
    }

    public void setRoute(Integer route) {
        this.route = route;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getMakedate() {
        return this.makedate;
    }

    public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }

    public Integer getIsdispose() {
        return this.isdispose;
    }

    public void setIsdispose(Integer isdispose) {
        this.isdispose = isdispose;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof TrialApply) ) return false;
        TrialApply castOther = (TrialApply) other;
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
