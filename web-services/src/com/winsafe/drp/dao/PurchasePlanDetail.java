package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class PurchasePlanDetail implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String ppid;

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
    private Double changequantity;

    /** nullable persistent field */
    private Date requiredate;

    /** nullable persistent field */
    private Date advicedate;

    /** nullable persistent field */
    private String requireexplain;

    /** full constructor */
    public PurchasePlanDetail(Integer id, String ppid, String productid, String productname, String specmode, Integer unitid, Double unitprice, Double quantity, Double changequantity, Date requiredate, Date advicedate, String requireexplain) {
        this.id = id;
        this.ppid = ppid;
        this.productid = productid;
        this.productname = productname;
        this.specmode = specmode;
        this.unitid = unitid;
        this.unitprice = unitprice;
        this.quantity = quantity;
        this.changequantity = changequantity;
        this.requiredate = requiredate;
        this.advicedate = advicedate;
        this.requireexplain = requireexplain;
    }

    /** default constructor */
    public PurchasePlanDetail() {
    }

    /** minimal constructor */
    public PurchasePlanDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPpid() {
        return this.ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
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

    public Double getChangequantity() {
        return this.changequantity;
    }

    public void setChangequantity(Double changequantity) {
        this.changequantity = changequantity;
    }

    public Date getRequiredate() {
        return this.requiredate;
    }

    public void setRequiredate(Date requiredate) {
        this.requiredate = requiredate;
    }

    public Date getAdvicedate() {
        return this.advicedate;
    }

    public void setAdvicedate(Date advicedate) {
        this.advicedate = advicedate;
    }

    public String getRequireexplain() {
        return this.requireexplain;
    }

    public void setRequireexplain(String requireexplain) {
        this.requireexplain = requireexplain;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof PurchasePlanDetail) ) return false;
        PurchasePlanDetail castOther = (PurchasePlanDetail) other;
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
