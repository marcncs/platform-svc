package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class IntegralOrderDetail implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String ioid;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;

    /** nullable persistent field */
    private String warehouseid;

    /** nullable persistent field */
    private Integer unitid;

    /** nullable persistent field */
    private Double integralprice;

    /** nullable persistent field */
    private Double quantity;

    /** nullable persistent field */
    private Double takequantity;

    /** nullable persistent field */
    private Double subsum;
    
    private Double cost;

    public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	/** full constructor */
    public IntegralOrderDetail(Integer id, String ioid, String productid, String productname, String specmode, String warehouseid, Integer unitid, Double integralprice, Double quantity, Double takequantity, Double subsum) {
        this.id = id;
        this.ioid = ioid;
        this.productid = productid;
        this.productname = productname;
        this.specmode = specmode;
        this.warehouseid = warehouseid;
        this.unitid = unitid;
        this.integralprice = integralprice;
        this.quantity = quantity;
        this.takequantity = takequantity;
        this.subsum = subsum;
    }

    /** default constructor */
    public IntegralOrderDetail() {
    }

    /** minimal constructor */
    public IntegralOrderDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIoid() {
        return this.ioid;
    }

    public void setIoid(String ioid) {
        this.ioid = ioid;
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

    public Double getIntegralprice() {
        return this.integralprice;
    }

    public void setIntegralprice(Double integralprice) {
        this.integralprice = integralprice;
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
        if ( !(other instanceof IntegralOrderDetail) ) return false;
        IntegralOrderDetail castOther = (IntegralOrderDetail) other;
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
