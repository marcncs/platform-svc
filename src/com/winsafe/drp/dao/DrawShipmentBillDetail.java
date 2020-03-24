package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class DrawShipmentBillDetail extends ActionForm implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String dsid;

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

    /** nullable persistent field */
    private Double quantity;

    /** nullable persistent field */
    private Double takequantity;

    /** nullable persistent field */
    private Double subsum;
    
    private Double cost;

    /** full constructor */
    public DrawShipmentBillDetail(Integer id, String dsid, String productid, String productname, String specmode, Integer unitid, String batch, Double unitprice, Double quantity, Double takequantity, Double subsum) {
        this.id = id;
        this.dsid = dsid;
        this.productid = productid;
        this.productname = productname;
        this.specmode = specmode;
        this.unitid = unitid;
        this.batch = batch;
        this.unitprice = unitprice;
        this.quantity = quantity;
        this.takequantity = takequantity;
        this.subsum = subsum;
    }

    /** default constructor */
    public DrawShipmentBillDetail() {
    }

    /** minimal constructor */
    public DrawShipmentBillDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDsid() {
        return this.dsid;
    }

    public void setDsid(String dsid) {
        this.dsid = dsid;
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
        if ( !(other instanceof DrawShipmentBillDetail) ) return false;
        DrawShipmentBillDetail castOther = (DrawShipmentBillDetail) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

}
