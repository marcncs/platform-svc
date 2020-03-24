package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class ProductPriceii extends ActionForm implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private Integer unitid;

    /** nullable persistent field */
    private Integer policyid;

    /** nullable persistent field */
    private Double unitprice;
    
    private Double frate;

    public Double getFrate() {
		return frate;
	}

	public void setFrate(Double frate) {
		this.frate = frate;
	}

	/** full constructor */
    public ProductPriceii(Integer id, String productid, Integer unitid, Integer policyid, Double unitprice,Double frate) {
        this.id = id;
        this.productid = productid;
        this.unitid = unitid;
        this.policyid = policyid;
        this.unitprice = unitprice;
        this.frate=frate;
    }

    /** default constructor */
    public ProductPriceii() {
    }

    /** minimal constructor */
    public ProductPriceii(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
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

    public Integer getPolicyid() {
        return this.policyid;
    }

    public void setPolicyid(Integer policyid) {
        this.policyid = policyid;
    }

    public Double getUnitprice() {
        return this.unitprice;
    }

    public void setUnitprice(Double unitprice) {
        this.unitprice = unitprice;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof ProductPriceii) ) return false;
        ProductPriceii castOther = (ProductPriceii) other;
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
