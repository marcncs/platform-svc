package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class SaleIndentDetail implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String siid;

    /** nullable persistent field */
    private String receiveman;

    /** nullable persistent field */
    private String tel;

    /** nullable persistent field */
    private String transportaddr;

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
    private Double discount;

    /** nullable persistent field */
    private Double taxrate;

    /** nullable persistent field */
    private Double cost;

    /** nullable persistent field */
    private Double subsum;

    /** full constructor */
    public SaleIndentDetail(Integer id, String siid, String receiveman, String tel, String transportaddr, String productid, String productname, String specmode, Integer unitid, Double unitprice, Double quantity, Double discount, Double taxrate, Double cost, Double subsum) {
        this.id = id;
        this.siid = siid;
        this.receiveman = receiveman;
        this.tel = tel;
        this.transportaddr = transportaddr;
        this.productid = productid;
        this.productname = productname;
        this.specmode = specmode;
        this.unitid = unitid;
        this.unitprice = unitprice;
        this.quantity = quantity;
        this.discount = discount;
        this.taxrate = taxrate;
        this.cost = cost;
        this.subsum = subsum;
    }

    /** default constructor */
    public SaleIndentDetail() {
    }

    /** minimal constructor */
    public SaleIndentDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSiid() {
        return this.siid;
    }

    public void setSiid(String siid) {
        this.siid = siid;
    }

    public String getReceiveman() {
        return this.receiveman;
    }

    public void setReceiveman(String receiveman) {
        this.receiveman = receiveman;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTransportaddr() {
        return this.transportaddr;
    }

    public void setTransportaddr(String transportaddr) {
        this.transportaddr = transportaddr;
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

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof SaleIndentDetail) ) return false;
        SaleIndentDetail castOther = (SaleIndentDetail) other;
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
