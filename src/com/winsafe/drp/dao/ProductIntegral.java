package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ProductIntegral implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private Integer salesort;

    /** nullable persistent field */
    private Integer unitid;

    /** nullable persistent field */
    private Double integral;

    /** nullable persistent field */
    private Double integralrate;

    /** full constructor */
    public ProductIntegral(Long id, String productid, Integer salesort, Integer unitid, Double integral, Double integralrate) {
        this.id = id;
        this.productid = productid;
        this.salesort = salesort;
        this.unitid = unitid;
        this.integral = integral;
        this.integralrate = integralrate;
    }

    /** default constructor */
    public ProductIntegral() {
    }

    /** minimal constructor */
    public ProductIntegral(Long id) {
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

    public Integer getSalesort() {
        return this.salesort;
    }

    public void setSalesort(Integer salesort) {
        this.salesort = salesort;
    }

    public Integer getUnitid() {
        return this.unitid;
    }

    public void setUnitid(Integer unitid) {
        this.unitid = unitid;
    }

    public Double getIntegral() {
        return this.integral;
    }

    public void setIntegral(Double integral) {
        this.integral = integral;
    }

    public Double getIntegralrate() {
        return this.integralrate;
    }

    public void setIntegralrate(Double integralrate) {
        this.integralrate = integralrate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ProductIntegral) ) return false;
        ProductIntegral castOther = (ProductIntegral) other;
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
