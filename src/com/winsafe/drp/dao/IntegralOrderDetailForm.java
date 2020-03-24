package com.winsafe.drp.dao;

import org.apache.struts.action.ActionForm;

/**
 * @author jelli
 * 2009-3-9
 */
public class IntegralOrderDetailForm extends ActionForm {
	 /** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String ioid;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;

    /** nullable persistent field */
    private String warehouseid;
    
    private String warehouseidname;

    /** nullable persistent field */
    private Integer unitid;
    
    private String unitidname;

    /** nullable persistent field */
    private Double integralprice;

    /** nullable persistent field */
    private Double quantity;

    /** nullable persistent field */
    private Double takequantity;

    /** nullable persistent field */
    private Double subsum;
    
    private String cname;
    
    private String cmobile;
    
    private Double cost;

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the ioid
	 */
	public String getIoid() {
		return ioid;
	}

	/**
	 * @param ioid the ioid to set
	 */
	public void setIoid(String ioid) {
		this.ioid = ioid;
	}

	/**
	 * @return the productid
	 */
	public String getProductid() {
		return productid;
	}

	/**
	 * @param productid the productid to set
	 */
	public void setProductid(String productid) {
		this.productid = productid;
	}

	/**
	 * @return the productname
	 */
	public String getProductname() {
		return productname;
	}

	/**
	 * @param productname the productname to set
	 */
	public void setProductname(String productname) {
		this.productname = productname;
	}

	/**
	 * @return the specmode
	 */
	public String getSpecmode() {
		return specmode;
	}

	/**
	 * @param specmode the specmode to set
	 */
	public void setSpecmode(String specmode) {
		this.specmode = specmode;
	}

	/**
	 * @return the warehouseid
	 */
	public String getWarehouseid() {
		return warehouseid;
	}

	/**
	 * @param warehouseid the warehouseid to set
	 */
	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

	/**
	 * @return the warehouseidname
	 */
	public String getWarehouseidname() {
		return warehouseidname;
	}

	/**
	 * @param warehouseidname the warehouseidname to set
	 */
	public void setWarehouseidname(String warehouseidname) {
		this.warehouseidname = warehouseidname;
	}

	/**
	 * @return the unitid
	 */
	public Integer getUnitid() {
		return unitid;
	}

	/**
	 * @param unitid the unitid to set
	 */
	public void setUnitid(Integer unitid) {
		this.unitid = unitid;
	}

	/**
	 * @return the unitidname
	 */
	public String getUnitidname() {
		return unitidname;
	}

	/**
	 * @param unitidname the unitidname to set
	 */
	public void setUnitidname(String unitidname) {
		this.unitidname = unitidname;
	}

	/**
	 * @return the integralprice
	 */
	public Double getIntegralprice() {
		return integralprice;
	}

	/**
	 * @param integralprice the integralprice to set
	 */
	public void setIntegralprice(Double integralprice) {
		this.integralprice = integralprice;
	}

	/**
	 * @return the quantity
	 */
	public Double getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the takequantity
	 */
	public Double getTakequantity() {
		return takequantity;
	}

	/**
	 * @param takequantity the takequantity to set
	 */
	public void setTakequantity(Double takequantity) {
		this.takequantity = takequantity;
	}

	/**
	 * @return the subsum
	 */
	public Double getSubsum() {
		return subsum;
	}

	/**
	 * @param subsum the subsum to set
	 */
	public void setSubsum(Double subsum) {
		this.subsum = subsum;
	}

	/**
	 * @return the cname
	 */
	public String getCname() {
		return cname;
	}

	/**
	 * @param cname the cname to set
	 */
	public void setCname(String cname) {
		this.cname = cname;
	}

	/**
	 * @return the cmobile
	 */
	public String getCmobile() {
		return cmobile;
	}

	/**
	 * @param cmobile the cmobile to set
	 */
	public void setCmobile(String cmobile) {
		this.cmobile = cmobile;
	}
    
    
}
