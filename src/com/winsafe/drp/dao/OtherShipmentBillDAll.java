package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class OtherShipmentBillDAll implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String osid;

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
    private Double subsum;

    /** nullable persistent field */
    private Double takequantity;
    
    private Double cost;
    
    // 计量单位(用于页面显示)
    private Integer countunit;
    // 数量(计量单位)(用于页面显示)
    private Double cUnitQuantity;

    private String remark;
    public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	/** full constructor */
    public OtherShipmentBillDAll(Integer id, String osid, String productid, String productname, String specmode, Integer unitid, String batch, Double unitprice, Double quantity, Double subsum, Double takequantity) {
        this.id = id;
        this.osid = osid;
        this.productid = productid;
        this.productname = productname;
        this.specmode = specmode;
        this.unitid = unitid;
        this.batch = batch;
        this.unitprice = unitprice;
        this.quantity = quantity;
        this.subsum = subsum;
        this.takequantity = takequantity;
    }

    /** default constructor */
    public OtherShipmentBillDAll() {
    }

    /** minimal constructor */
    public OtherShipmentBillDAll(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOsid() {
        return this.osid;
    }

    public void setOsid(String osid) {
        this.osid = osid;
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

    public Double getSubsum() {
        return this.subsum;
    }

    public void setSubsum(Double subsum) {
        this.subsum = subsum;
    }

    public Double getTakequantity() {
        return this.takequantity;
    }

    public void setTakequantity(Double takequantity) {
        this.takequantity = takequantity;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof OtherShipmentBillDAll) ) return false;
        OtherShipmentBillDAll castOther = (OtherShipmentBillDAll) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}

	public Integer getCountunit() {
		return countunit;
	}

	public void setCountunit(Integer countunit) {
		this.countunit = countunit;
	}

	public Double getcUnitQuantity() {
		return cUnitQuantity;
	}

	public void setcUnitQuantity(Double cUnitQuantity) {
		this.cUnitQuantity = cUnitQuantity;
	}

}
