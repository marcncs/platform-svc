package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class WithdrawDetail extends ActionForm implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String wid;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;

    /** nullable persistent field */
    private Integer unitid;

    /** nullable persistent field */
    private String batch;

    /** nullable persistent field */
    private Double unitprice;
    
    private Double taxunitprice;

    /** nullable persistent field */
    private Double quantity;

    /** nullable persistent field */
    private Double discount;

    /** nullable persistent field */
    private Double taxrate;

    /** nullable persistent field */
    private Double subsum;

    /** full constructor */
    public WithdrawDetail(Integer id, String wid, String productid, String productname, String specmode, Integer unitid, String batch, Double unitprice, Double quantity, Double discount, Double taxrate, Double subsum) {
        this.id = id;
        this.wid = wid;
        this.productid = productid;
        this.productname = productname;
        this.specmode = specmode;
        this.unitid = unitid;
        this.batch = batch;
        this.unitprice = unitprice;
        this.quantity = quantity;
        this.discount = discount;
        this.taxrate = taxrate;
        this.subsum = subsum;
    }

    /** default constructor */
    public WithdrawDetail() {
    }

    /** minimal constructor */
    public WithdrawDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWid() {
        return this.wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
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

    public String getBatch() {
        return this.batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
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
        if ( !(other instanceof WithdrawDetail) ) return false;
        WithdrawDetail castOther = (WithdrawDetail) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public Double getTaxunitprice() {
		return taxunitprice;
	}

	public void setTaxunitprice(Double taxunitprice) {
		this.taxunitprice = taxunitprice;
	}

}
