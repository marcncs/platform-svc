package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class SaleOrderDetail implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String soid;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;

    /** nullable persistent field */
    private Integer salesort;
    
    private Integer wise;

    /** nullable persistent field */
    private String warehouseid;

    /** nullable persistent field */
    private Integer unitid;

    /** nullable persistent field */
    private String batch;

    /** nullable persistent field */
    private Double orgunitprice;

    /** nullable persistent field */
    private Double unitprice;

    /** nullable persistent field */
    private Double taxunitprice;

    /** nullable persistent field */
    private Double quantity;

    /** nullable persistent field */
    private Double takequantity;

    /** nullable persistent field */
    private Double discount;

    /** nullable persistent field */
    private Double taxrate;

    /** nullable persistent field */
    private Double cost;

    /** nullable persistent field */
    private Double subsum;

    /** full constructor */
    public SaleOrderDetail(Integer id, String soid, String productid, String productname, String specmode, Integer salesort, String warehouseid, Integer unitid, String batch, Double orgunitprice, Double unitprice, Double taxunitprice, Double quantity, Double takequantity, Double discount, Double taxrate, Double cost, Double subsum) {
        this.id = id;
        this.soid = soid;
        this.productid = productid;
        this.productname = productname;
        this.specmode = specmode;
        this.salesort = salesort;
        this.warehouseid = warehouseid;
        this.unitid = unitid;
        this.batch = batch;
        this.orgunitprice = orgunitprice;
        this.unitprice = unitprice;
        this.taxunitprice = taxunitprice;
        this.quantity = quantity;
        this.takequantity = takequantity;
        this.discount = discount;
        this.taxrate = taxrate;
        this.cost = cost;
        this.subsum = subsum;
    }

    /** default constructor */
    public SaleOrderDetail() {
    }

    /** minimal constructor */
    public SaleOrderDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSoid() {
        return this.soid;
    }

    public void setSoid(String soid) {
        this.soid = soid;
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

    public Integer getSalesort() {
        return this.salesort;
    }

    public void setSalesort(Integer salesort) {
        this.salesort = salesort;
    }

    public String getWarehouseid() {
        return this.warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid;
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

    public Double getOrgunitprice() {
        return this.orgunitprice;
    }

    public void setOrgunitprice(Double orgunitprice) {
        this.orgunitprice = orgunitprice;
    }

    public Double getUnitprice() {
        return this.unitprice;
    }

    public void setUnitprice(Double unitprice) {
        this.unitprice = unitprice;
    }

    public Double getTaxunitprice() {
        return this.taxunitprice;
    }

    public void setTaxunitprice(Double taxunitprice) {
        this.taxunitprice = taxunitprice;
    }

    public Double getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getTakequantity() {
        return this.takequantity;
    }

    public void setTakequantity(Double takequantity) {
        this.takequantity = takequantity;
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
    
    

    public Integer getWise() {
		return wise;
	}

	public void setWise(Integer wise) {
		this.wise = wise;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof SaleOrderDetail) ) return false;
        SaleOrderDetail castOther = (SaleOrderDetail) other;
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
