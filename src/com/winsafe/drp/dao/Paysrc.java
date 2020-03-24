package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class Paysrc extends ActionForm implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String srcname;

    /** nullable persistent field */
    private String srcmemo;

    /** nullable persistent field */
    private Long parentid;

    /** nullable persistent field */
    private Integer rank;

    /** full constructor */
    public Paysrc(Long id, String srcname, String srcmemo, Long parentid, Integer rank) {
        this.id = id;
        this.srcname = srcname;
        this.srcmemo = srcmemo;
        this.parentid = parentid;
        this.rank = rank;
    }

    /** default constructor */
    public Paysrc() {
    }

    /** minimal constructor */
    public Paysrc(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSrcname() {
        return this.srcname;
    }

    public void setSrcname(String srcname) {
        this.srcname = srcname;
    }

    public String getSrcmemo() {
        return this.srcmemo;
    }

    public void setSrcmemo(String srcmemo) {
        this.srcmemo = srcmemo;
    }

    public Long getParentid() {
        return this.parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public Integer getRank() {
        return this.rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Paysrc) ) return false;
        Paysrc castOther = (Paysrc) other;
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
