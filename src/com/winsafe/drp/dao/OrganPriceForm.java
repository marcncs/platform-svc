package com.winsafe.drp.dao;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class OrganPriceForm {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String organid;
    
    private String organidname;

    /** nullable persistent field */
    private String productid;
    
    private String productidname;

    /** nullable persistent field */
    private Integer unitid;
    
    private String unitidname;

    /** nullable persistent field */
    private Integer policyid;
    
    private String policyidname;

    /** nullable persistent field */
    private Double unitprice;

    /** full constructor */
    public OrganPriceForm(Integer id, String organid, String productid, Integer unitid, Integer policyid, Double unitprice) {
        this.id = id;
        this.organid = organid;
        this.productid = productid;
        this.unitid = unitid;
        this.policyid = policyid;
        this.unitprice = unitprice;
    }

    /** default constructor */
    public OrganPriceForm() {
    }

    /** minimal constructor */
    public OrganPriceForm(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrganid() {
        return this.organid;
    }

    public void setOrganid(String organid) {
        this.organid = organid;
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
        if ( !(other instanceof ProductPrice) ) return false;
        ProductPrice castOther = (ProductPrice) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public String getOrganidname() {
		return organidname;
	}

	public void setOrganidname(String organidname) {
		this.organidname = organidname;
	}

	public String getPolicyidname() {
		return policyidname;
	}

	public void setPolicyidname(String policyidname) {
		this.policyidname = policyidname;
	}

	public String getProductidname() {
		return productidname;
	}

	public void setProductidname(String productidname) {
		this.productidname = productidname;
	}

	public String getUnitidname() {
		return unitidname;
	}

	public void setUnitidname(String unitidname) {
		this.unitidname = unitidname;
	}

}
