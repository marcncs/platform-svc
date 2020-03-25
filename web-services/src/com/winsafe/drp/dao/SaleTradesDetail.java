package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class SaleTradesDetail implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String stid;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;

    /** nullable persistent field */
    private String oldbatch;

    /** nullable persistent field */
    private String warehouseid;

    /** nullable persistent field */
    private Integer unitid;

    /** nullable persistent field */
    private String batch;

    /** nullable persistent field */
    private Double quantity;

    /** nullable persistent field */
    private Double takequantity;
    
    private Double unitprice;

    /** full constructor */
    public SaleTradesDetail(Integer id, String stid, String productid, String productname, String specmode, String oldbatch, String warehouseid, Integer unitid, String batch, Double quantity, Double takequantity) {
        this.id = id;
        this.stid = stid;
        this.productid = productid;
        this.productname = productname;
        this.specmode = specmode;
        this.oldbatch = oldbatch;
        this.warehouseid = warehouseid;
        this.unitid = unitid;
        this.batch = batch;
        this.quantity = quantity;
        this.takequantity = takequantity;
    }

    /** default constructor */
    public SaleTradesDetail() {
    }

    /** minimal constructor */
    public SaleTradesDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStid() {
        return this.stid;
    }

    public void setStid(String stid) {
        this.stid = stid;
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

    public String getOldbatch() {
        return this.oldbatch;
    }

    public void setOldbatch(String oldbatch) {
        this.oldbatch = oldbatch;
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
    
    

    public Double getUnitprice() {
		return unitprice;
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
        if ( !(other instanceof SaleTradesDetail) ) return false;
        SaleTradesDetail castOther = (SaleTradesDetail) other;
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
