package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class SupplySaleApplyDetail implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String ssid;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;

    /** nullable persistent field */
    private Integer unitid;

    /** nullable persistent field */
    private Double punitprice;

    /** nullable persistent field */
    private Double sunitprice;

    /** nullable persistent field */
    private Double quantity;

    /** nullable persistent field */
    private Double canquantity;

    /** nullable persistent field */
    private Double alreadyquantity;

    /** nullable persistent field */
    private Double psubsum;

    /** nullable persistent field */
    private Double ssubsum;

    /** full constructor */
    public SupplySaleApplyDetail(Integer id, String ssid, String productid, String productname, String specmode, Integer unitid, Double punitprice, Double sunitprice, Double quantity, Double canquantity, Double alreadyquantity, Double psubsum, Double ssubsum) {
        this.id = id;
        this.ssid = ssid;
        this.productid = productid;
        this.productname = productname;
        this.specmode = specmode;
        this.unitid = unitid;
        this.punitprice = punitprice;
        this.sunitprice = sunitprice;
        this.quantity = quantity;
        this.canquantity = canquantity;
        this.alreadyquantity = alreadyquantity;
        this.psubsum = psubsum;
        this.ssubsum = ssubsum;
    }

    /** default constructor */
    public SupplySaleApplyDetail() {
    }

    /** minimal constructor */
    public SupplySaleApplyDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSsid() {
        return this.ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
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

    public Double getCanquantity() {
        return this.canquantity;
    }

    public void setCanquantity(Double canquantity) {
        this.canquantity = canquantity;
    }

    public Double getAlreadyquantity() {
        return this.alreadyquantity;
    }

    public void setAlreadyquantity(Double alreadyquantity) {
        this.alreadyquantity = alreadyquantity;
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
        if ( !(other instanceof SupplySaleApplyDetail) ) return false;
        SupplySaleApplyDetail castOther = (SupplySaleApplyDetail) other;
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
