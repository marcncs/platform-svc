package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class ProductIntegralForm extends ActionForm implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String productid;
    
    private String productidname;
    
    private Integer salesort;
    
    private String salesortname;
    
    private Integer unitid;
    
    private String unitidname;

    /** nullable persistent field */
    private Double integral;

    /** nullable persistent field */
    private Double integralrate;

    /** full constructor */
    public ProductIntegralForm(Long id, String productid, Double integral, Double integralrate) {
        this.id = id;
        this.productid = productid;
        this.integral = integral;
        this.integralrate = integralrate;
    }

    /** default constructor */
    public ProductIntegralForm() {
    }

    /** minimal constructor */
    public ProductIntegralForm(Long id) {
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
        if ( !(other instanceof ProductIntegralForm) ) return false;
        ProductIntegralForm castOther = (ProductIntegralForm) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public String getProductidname() {
		return productidname;
	}

	public void setProductidname(String productidname) {
		this.productidname = productidname;
	}

	public Integer getSalesort() {
		return salesort;
	}

	public void setSalesort(Integer salesort) {
		this.salesort = salesort;
	}

	public String getSalesortname() {
		return salesortname;
	}

	public void setSalesortname(String salesortname) {
		this.salesortname = salesortname;
	}

	public Integer getUnitid() {
		return unitid;
	}

	public void setUnitid(Integer unitid) {
		this.unitid = unitid;
	}

	public String getUnitidname() {
		return unitidname;
	}

	public void setUnitidname(String unitidname) {
		this.unitidname = unitidname;
	}

}
