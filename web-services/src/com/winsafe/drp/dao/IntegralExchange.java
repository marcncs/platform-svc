package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class IntegralExchange extends ActionForm implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private Integer unitid;

    /** nullable persistent field */
    private Double unitintegral;

    /** full constructor */
    public IntegralExchange(Long id, String productid, Integer unitid, Double unitintegral) {
        this.id = id;
        this.productid = productid;
        this.unitid = unitid;
        this.unitintegral = unitintegral;
    }

    /** default constructor */
    public IntegralExchange() {
    }

    /** minimal constructor */
    public IntegralExchange(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductid() {
        return this.productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public Integer getUnitid() {
        return this.unitid;
    }

    public void setUnitid(Integer unitid) {
        this.unitid = unitid;
    }

    public Double getUnitintegral() {
        return this.unitintegral;
    }

    public void setUnitintegral(Double unitintegral) {
        this.unitintegral = unitintegral;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof IntegralExchange) ) return false;
        IntegralExchange castOther = (IntegralExchange) other;
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
