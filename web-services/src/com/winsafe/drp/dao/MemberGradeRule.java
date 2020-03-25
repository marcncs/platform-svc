package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class MemberGradeRule implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private Double startprice;

    /** nullable persistent field */
    private Double endprice;

    /** nullable persistent field */
    private Double startintegral;

    /** nullable persistent field */
    private Double endintegral;

    /** nullable persistent field */
    private Integer mgid;

    /** full constructor */
    public MemberGradeRule(Integer id, Double startprice, Double endprice, Double startintegral, Double endintegral, Integer mgid) {
        this.id = id;
        this.startprice = startprice;
        this.endprice = endprice;
        this.startintegral = startintegral;
        this.endintegral = endintegral;
        this.mgid = mgid;
    }

    /** default constructor */
    public MemberGradeRule() {
    }

    /** minimal constructor */
    public MemberGradeRule(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getStartprice() {
        return this.startprice;
    }

    public void setStartprice(Double startprice) {
        this.startprice = startprice;
    }

    public Double getEndprice() {
        return this.endprice;
    }

    public void setEndprice(Double endprice) {
        this.endprice = endprice;
    }

    public Double getStartintegral() {
        return this.startintegral;
    }

    public void setStartintegral(Double startintegral) {
        this.startintegral = startintegral;
    }

    public Double getEndintegral() {
        return this.endintegral;
    }

    public void setEndintegral(Double endintegral) {
        this.endintegral = endintegral;
    }

    public Integer getMgid() {
        return this.mgid;
    }

    public void setMgid(Integer mgid) {
        this.mgid = mgid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof MemberGradeRule) ) return false;
        MemberGradeRule castOther = (MemberGradeRule) other;
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
