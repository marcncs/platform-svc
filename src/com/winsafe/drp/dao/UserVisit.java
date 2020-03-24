package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class UserVisit implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Integer userid;

    /** nullable persistent field */
    private String visitorgan;

    /** nullable persistent field */
    private String visitdept;

    /** nullable persistent field */
    private String visitusers;

    /** full constructor */
    public UserVisit(Integer id, Integer userid, String visitorgan, String visitdept, String visitusers) {
        this.id = id;
        this.userid = userid;
        this.visitorgan = visitorgan;
        this.visitdept = visitdept;
        this.visitusers = visitusers;
    }

    /** default constructor */
    public UserVisit() {
    }

    /** minimal constructor */
    public UserVisit(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return this.userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getVisitorgan() {
        return this.visitorgan;
    }

    public void setVisitorgan(String visitorgan) {
        this.visitorgan = visitorgan;
    }

    public String getVisitdept() {
        return this.visitdept;
    }

    public void setVisitdept(String visitdept) {
        this.visitdept = visitdept;
    }

    public String getVisitusers() {
        return this.visitusers;
    }

    public void setVisitusers(String visitusers) {
        this.visitusers = visitusers;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof UserVisit) ) return false;
        UserVisit castOther = (UserVisit) other;
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
