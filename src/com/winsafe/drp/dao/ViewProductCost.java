package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ViewProductCost implements Serializable {

    /** nullable persistent field */
    private String warehouseid;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String batch;

    /** nullable persistent field */
    private Integer unitid;

    /** nullable persistent field */
    private Double cost;

    /** nullable persistent field */
    private Double mixcost;

    /** nullable persistent field */
    private Double xquantity;
    
    private Double mixquantity;

    /** full constructor */
    public ViewProductCost(String warehouseid, String productid, String productname, String batch, Integer unitid, Double cost, Double mixcost, Double xquantity) {
        this.warehouseid = warehouseid;
        this.productid = productid;
        this.productname = productname;
        this.batch = batch;
        this.unitid = unitid;
        this.cost = cost;
        this.mixcost = mixcost;
        this.xquantity = xquantity;
    }

    /** default constructor */
    public ViewProductCost() {
    }

    public String getWarehouseid() {
        return this.warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid;
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

    public String getBatch() {
        return this.batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Integer getUnitid() {
        return this.unitid;
    }

    public void setUnitid(Integer unitid) {
        this.unitid = unitid;
    }

    public Double getCost() {
        return this.cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getMixcost() {
        return this.mixcost;
    }

    public void setMixcost(Double mixcost) {
        this.mixcost = mixcost;
    }

    public Double getXquantity() {
        return this.xquantity;
    }

    public void setXquantity(Double xquantity) {
        this.xquantity = xquantity;
    }
    
    

    public Double getMixquantity() {
		return mixquantity;
	}

	public void setMixquantity(Double mixquantity) {
		this.mixquantity = mixquantity;
	}

	public String toString() {
        return new ToStringBuilder(this)
            .toString();
    }

}
