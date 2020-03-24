package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class SupplySaleMoveDetail implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String ssmid;

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
    private Double punitprice;

    /** nullable persistent field */
    private Double sunitprice;

    /** nullable persistent field */
    private Double quantity;

    /** nullable persistent field */
    private Double takequantity;

    /** nullable persistent field */
    private Double psubsum;

    /** nullable persistent field */
    private Double ssubsum;
    
    private Double cost;
    
    private Date makedate;

    /** full constructor */
    public SupplySaleMoveDetail(Integer id, String ssmid, String productid, String productname, String specmode, Integer unitid, String batch, Double punitprice, Double sunitprice, Double quantity, Double takequantity, Double psubsum, Double ssubsum) {
        this.id = id;
        this.ssmid = ssmid;
        this.productid = productid;
        this.productname = productname;
        this.specmode = specmode;
        this.unitid = unitid;
        this.batch = batch;
        this.punitprice = punitprice;
        this.sunitprice = sunitprice;
        this.quantity = quantity;
        this.takequantity = takequantity;
        this.psubsum = psubsum;
        this.ssubsum = ssubsum;
    }

    /** default constructor */
    public SupplySaleMoveDetail() {
    }

    /** minimal constructor */
    public SupplySaleMoveDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSsmid() {
        return this.ssmid;
    }

    public void setSsmid(String ssmid) {
        this.ssmid = ssmid;
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

    public Double getPunitprice() {
        return this.punitprice;
    }

    public void setPunitprice(Double punitprice) {
        this.punitprice = punitprice;
    }

    public Double getSunitprice() {
        return this.sunitprice;
    }

    public void setSunitprice(Double sunitprice) {
        this.sunitprice = sunitprice;
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

    public Double getPsubsum() {
        return this.psubsum;
    }

    public void setPsubsum(Double psubsum) {
        this.psubsum = psubsum;
    }

    public Double getSsubsum() {
        return this.ssubsum;
    }

    public void setSsubsum(Double ssubsum) {
        this.ssubsum = ssubsum;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof SupplySaleMoveDetail) ) return false;
        SupplySaleMoveDetail castOther = (SupplySaleMoveDetail) other;
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

	public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}

}
