package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class CTitle extends ActionForm implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String cid;

    /** nullable persistent field */
    private String ctitle;

    /** full constructor */
    public CTitle(Integer id, String cid, String ctitle) {
        this.id = id;
        this.cid = cid;
        this.ctitle = ctitle;
    }

    /** default constructor */
    public CTitle() {
    }

    /** minimal constructor */
    public CTitle(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCid() {
        return this.cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCtitle() {
        return this.ctitle;
    }

    public void setCtitle(String ctitle) {
        this.ctitle = ctitle;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CTitle) ) return false;
        CTitle castOther = (CTitle) other;
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
