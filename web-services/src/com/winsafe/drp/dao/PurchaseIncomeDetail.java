package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class PurchaseIncomeDetail implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String piid;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;

    /** nullable persistent field */
    private Integer unitid;

    /** nullable persistent field */
    private Double unitprice;

    /** nullable persistent field */
    private Double quantity;

    /** nullable persistent field */
    private Double factquantity;

    /** nullable persistent field */
    private Double subsum;
    
    private Double cost;
    
    private Date makedate;
    
    private String nccode;

    public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

	
	
	
	/** full constructor */
    public PurchaseIncomeDetail(Integer id, String piid, String productid, String productname, String specmode, Integer unitid, Double unitprice, Double quantity, Double factquantity, Double subsum,String nccode) {
        this.id = id;
        this.piid = piid;
        this.productid = productid;
        this.productname = productname;
        this.specmode = specmode;
        this.unitid = unitid;
        this.unitprice = unitprice;
        this.quantity = quantity;
        this.factquantity = factquantity;
        this.subsum = subsum;
        this.nccode=nccode;
    }

    /** default constructor */
    public PurchaseIncomeDetail() {
    }

    /** minimal constructor */
    public PurchaseIncomeDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPiid() {
        return this.piid;
    }

    public void setPiid(String piid) {
        this.piid = piid;
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

    public Double getFactquantity() {
        return this.factquantity;
    }

    public void setFactquantity(Double factquantity) {
        this.factquantity = factquantity;
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

    public String getNccode() {
		return nccode;
	}

	public void setNccode(String nccode) {
		this.nccode = nccode;
	}

	public boolean equals(Object other) {
        if ( !(other instanceof PurchaseIncomeDetail) ) return false;
        PurchaseIncomeDetail castOther = (PurchaseIncomeDetail) other;
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
