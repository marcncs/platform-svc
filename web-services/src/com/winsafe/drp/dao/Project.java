package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class Project extends ActionForm implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private Integer pcontent;

    /** nullable persistent field */
    private Integer pstatus;

    /** nullable persistent field */
    private Double amount;

    /** nullable persistent field */
    private Date pbegin;

    /** nullable persistent field */
    private Date pend;

    /** nullable persistent field */
    private String memo;

    /** nullable persistent field */
    private Long makeid;

    /** nullable persistent field */
    private Date makedate;

    /** full constructor */
    public Project(Long id, String cid, Integer pcontent, Integer pstatus, Double amount, Date pbegin, Date pend, String memo, Long makeid, Date makedate) {
        this.id = id;
        this.cid = cid;
        this.pcontent = pcontent;
        this.pstatus = pstatus;
        this.amount = amount;
        this.pbegin = pbegin;
        this.pend = pend;
        this.memo = memo;
        this.makeid = makeid;
        this.makedate = makedate;
    }

    /** default constructor */
    public Project() {
    }

    /** minimal constructor */
    public Project(Long id) {
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

    public Integer getPcontent() {
        return this.pcontent;
    }

    public void setPcontent(Integer pcontent) {
        this.pcontent = pcontent;
    }

    public Integer getPstatus() {
        return this.pstatus;
    }

    public void setPstatus(Integer pstatus) {
        this.pstatus = pstatus;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getPbegin() {
        return this.pbegin;
    }

    public void setPbegin(Date pbegin) {
        this.pbegin = pbegin;
    }

    public Date getPend() {
        return this.pend;
    }

    public void setPend(Date pend) {
        this.pend = pend;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Project) ) return false;
        Project castOther = (Project) other;
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
