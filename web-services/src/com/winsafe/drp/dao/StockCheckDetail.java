package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class StockCheckDetail implements Serializable {

    /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String scid;

    /** nullable persistent field */
    private String warehousebit;

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
    private Double reckonquantity;

    /** nullable persistent field */
    private Double checkquantity;

    /** full constructor */
    public StockCheckDetail(Integer id, String scid, String warehousebit, String productid, String productname, String specmode, Integer unitid, String batch, Double unitprice, Double reckonquantity, Double checkquantity) {
        this.id = id;
        this.scid = scid;
        this.warehousebit = warehousebit;
        this.productid = productid;
        this.productname = productname;
        this.specmode = specmode;
        this.unitid = unitid;
        this.batch = batch;
        this.unitprice = unitprice;
        this.reckonquantity = reckonquantity;
        this.checkquantity = checkquantity;
    }

    /** default constructor */
    public StockCheckDetail() {
    }

    /** minimal constructor */
    public StockCheckDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getScid() {
        return this.scid;
    }

    public void setScid(String scid) {
        this.scid = scid;
    }

    public String getWarehousebit() {
        return this.warehousebit;
    }

    public void setWarehousebit(String warehousebit) {
        this.warehousebit = warehousebit;
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

    public Double getReckonquantity() {
        return this.reckonquantity;
    }

    public void setReckonquantity(Double reckonquantity) {
        this.reckonquantity = reckonquantity;
    }

    public Double getCheckquantity() {
        return this.checkquantity;
    }

    public void setCheckquantity(Double checkquantity) {
        this.checkquantity = checkquantity;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof StockCheckDetail) ) return false;
        StockCheckDetail castOther = (StockCheckDetail) other;
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
