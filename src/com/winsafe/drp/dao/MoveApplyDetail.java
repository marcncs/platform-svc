package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionForm;

/** @author Hibernate CodeGenerator */
public class MoveApplyDetail extends ActionForm implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String maid;

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
    private Double canquantity;
    
    private Double alreadyquantity;

    /** nullable persistent field */
    private Double subsum;

    /** full constructor */
    public MoveApplyDetail(Integer id, String maid, String productid, String productname, String specmode, Integer unitid, Double unitprice, Double quantity, Double canquantity, Double subsum) {
        this.id = id;
        this.maid = maid;
        this.productid = productid;
        this.productname = productname;
        this.specmode = specmode;
        this.unitid = unitid;
        this.unitprice = unitprice;
        this.quantity = quantity;
        this.canquantity = canquantity;
        this.subsum = subsum;
    }

    /** default constructor */
    public MoveApplyDetail() {
    }

    /** minimal constructor */
    public MoveApplyDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaid() {
        return this.maid;
    }

    public void setMaid(String maid) {
        this.maid = maid;
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

    public Double getCanquantity() {
        return this.canquantity;
    }

    public void setCanquantity(Double canquantity) {
        this.canquantity = canquantity;
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
        if ( !(other instanceof MoveApplyDetail) ) return false;
        MoveApplyDetail castOther = (MoveApplyDetail) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

	public Double getAlreadyquantity() {
		return alreadyquantity;
	}

	public void setAlreadyquantity(Double alreadyquantity) {
		this.alreadyquantity = alreadyquantity;
	}

}
