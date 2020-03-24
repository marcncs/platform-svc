package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class SaleIndentDetailForm implements Serializable {

    /** identifier field */
    private Integer id;
    
    private String idname;

    /** nullable persistent field */
    
    private Integer siid;
    private String siidname;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;

    /** nullable persistent field */
    private Integer unitid;
    
    private String unitidname;

    /** nullable persistent field */
    private Double unitprice;
    
    private String unitpricename;

    /** nullable persistent field */
    private Double quantity;
    
    private String quantityname;

    /** nullable persistent field */
    private Double discount;
    
    private String discountname;

    /** nullable persistent field */
    private Double taxrate;
    private String taxratename;

    /** nullable persistent field */
    private Double cost;
    
    private String costname;

    /** nullable persistent field */
    private Double subsum;
    
    private String subsumname;

    /** full constructor */
    

    /** default constructor */
    public SaleIndentDetailForm() {
    }

    /** minimal constructor */
    public SaleIndentDetailForm(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSiid() {
        return this.siid;
    }

    public void setSiid(Integer siid) {
        this.siid = siid;
    }

    public String getProductid() {
        return this.productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return this.productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getSpecmode() {
        return this.specmode;
    }

    public void setSpecmode(String specmode) {
        this.specmode = specmode;
    }

    public Integer getUnitid() {
        return this.unitid;
    }

    public void setUnitid(Integer unitid) {
        this.unitid = unitid;
    }

    public Double getUnitprice() {
        return this.unitprice;
    }

    public void setUnitprice(Double unitprice) {
        this.unitprice = unitprice;
    }

    public Double getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getTaxrate() {
        return this.taxrate;
    }

    public void setTaxrate(Double taxrate) {
        this.taxrate = taxrate;
    }

    public Double getCost() {
        return this.cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getSubsum() {
        return this.subsum;
    }

    public void setSubsum(Double subsum) {
        this.subsum = subsum;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof SaleIndentDetailForm) ) return false;
        SaleIndentDetailForm castOther = (SaleIndentDetailForm) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public String getCostname() {
		return costname;
	}

	public void setCostname(String costname) {
		this.costname = costname;
	}

	public String getDiscountname() {
		return discountname;
	}

	public void setDiscountname(String discountname) {
		this.discountname = discountname;
	}

	public String getIdname() {
		return idname;
	}

	public void setIdname(String idname) {
		this.idname = idname;
	}

	public String getQuantityname() {
		return quantityname;
	}

	public void setQuantityname(String quantityname) {
		this.quantityname = quantityname;
	}

	public String getSiidname() {
		return siidname;
	}

	public void setSiidname(String siidname) {
		this.siidname = siidname;
	}

	public String getSubsumname() {
		return subsumname;
	}

	public void setSubsumname(String subsumname) {
		this.subsumname = subsumname;
	}

	public String getTaxratename() {
		return taxratename;
	}

	public void setTaxratename(String taxratename) {
		this.taxratename = taxratename;
	}

	public String getUnitidname() {
		return unitidname;
	}

	public void setUnitidname(String unitidname) {
		this.unitidname = unitidname;
	}

	public String getUnitpricename() {
		return unitpricename;
	}

	public void setUnitpricename(String unitpricename) {
		this.unitpricename = unitpricename;
	}

}
