package com.winsafe.drp.dao;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class FUnitForm {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String productid;
    
    private String productidname;

    /** nullable persistent field */
    private Integer funitid;
    
    private String funitidname;

    /** nullable persistent field */
    private Double xquantity;
    
    private Integer ismain;


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

	public String getFunitidname() {
		return funitidname;
	}

	public void setFunitidname(String funitidname) {
		this.funitidname = funitidname;
	}

	public String getProductidname() {
		return productidname;
	}

	public void setProductidname(String productidname) {
		this.productidname = productidname;
	}

	public Integer getIsmain() {
		return ismain;
	}

	public void setIsmain(Integer ismain) {
		this.ismain = ismain;
	}

}
