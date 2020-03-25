package com.winsafe.drp.dao;

import java.io.Serializable;

/** 
 * @author jerry
 * @version 2009-8-24 上午11:33:03 
 * www.winsafe.cn 
 */
public class BaseBillDetail implements Serializable {

	private Integer bdid;
	private String productid;
	private String productname;
	private String batch;
	private Double quantity;
	private Integer unitid;
	private String specmode;
	public Integer getBdid() {
		return bdid;
	}
	public void setBdid(Integer bdid) {
		this.bdid = bdid;
	}
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	
	
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public Integer getUnitid() {
		return unitid;
	}
	public void setUnitid(Integer unitid) {
		this.unitid = unitid;
	}
	public String getSpecmode() {
		return specmode;
	}
	public void setSpecmode(String specmode) {
		this.specmode = specmode;
	}
	
	
	
	
}
