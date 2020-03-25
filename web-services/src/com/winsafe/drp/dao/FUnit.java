package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class FUnit extends ActionForm implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private Integer funitid;

    /** nullable persistent field */
    private Double xquantity;
    
    private Integer ismain;

    public Integer getIsmain() {
		return ismain;
	}

	public void setIsmain(Integer ismain) {
		this.ismain = ismain;
	}

	/** full constructor */
    public FUnit(Integer id, String productid, Integer funitid, Double xquantity) {
        this.id = id;
        this.productid = productid;
        this.funitid = funitid;
        this.xquantity = xquantity;
    }

    /** default constructor */
    public FUnit() {
    }

    /** minimal constructor */
    public FUnit(Integer id) {
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

    public Integer getFunitid() {
        return this.funitid;
    }

    public void setFunitid(Integer funitid) {
        this.funitid = funitid;
    }

    public Double getXquantity() {
        return this.xquantity;
    }

    public void setXquantity(Double xquantity) {
        this.xquantity = xquantity;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof FUnit) ) return false;
        FUnit castOther = (FUnit) other;
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
