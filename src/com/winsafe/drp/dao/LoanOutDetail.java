package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class LoanOutDetail implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String loid;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;

    /** nullable persistent field */
    private Long warehouseid;

    /** nullable persistent field */
    private Long unitid;

    /** nullable persistent field */
    private String batch;

    /** nullable persistent field */
    private Double unitprice;

    /** nullable persistent field */
    private Double quantity;

    /** nullable persistent field */
    private Double takequantity;

    /** nullable persistent field */
    private Double backquantity;

    /** nullable persistent field */
    private Double cost;

    /** nullable persistent field */
    private Double subsum;

    /** full constructor */
    public LoanOutDetail(Long id, String loid, String productid, String productname, String specmode, Long warehouseid, Long unitid, String batch, Double unitprice, Double quantity, Double takequantity, Double backquantity, Double cost, Double subsum) {
        this.id = id;
        this.loid = loid;
        this.productid = productid;
        this.productname = productname;
        this.specmode = specmode;
        this.warehouseid = warehouseid;
        this.unitid = unitid;
        this.batch = batch;
        this.unitprice = unitprice;
        this.quantity = quantity;
        this.takequantity = takequantity;
        this.backquantity = backquantity;
        this.cost = cost;
        this.subsum = subsum;
    }

    /** default constructor */
    public LoanOutDetail() {
    }

    /** minimal constructor */
    public LoanOutDetail(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoid() {
        return this.loid;
    }

    public void setLoid(String loid) {
        this.loid = loid;
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

    public Long getWarehouseid() {
        return this.warehouseid;
    }

    public void setWarehouseid(Long warehouseid) {
        this.warehouseid = warehouseid;
    }

    public Long getUnitid() {
        return this.unitid;
    }

    public void setUnitid(Long unitid) {
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

    public Double getTakequantity() {
        return this.takequantity;
    }

    public void setTakequantity(Double takequantity) {
        this.takequantity = takequantity;
    }

    public Double getBackquantity() {
        return this.backquantity;
    }

    public void setBackquantity(Double backquantity) {
        this.backquantity = backquantity;
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
        if ( !(other instanceof LoanOutDetail) ) return false;
        LoanOutDetail castOther = (LoanOutDetail) other;
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
