package com.winsafe.drp.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class TakeTicketDetailBatchBit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** identifier field */
	private Integer id;
	/** nullable persistent field */
	private String ttid;

	/** nullable persistent field */
	private Integer ttdid;

	/** nullable persistent field */
	private String productid;

	/** nullable persistent field */
	private String productname;

	/** nullable persistent field */
	private String warehouseBit;

	private String warehouseBitName;
	/** nullable persistent field */
	private String batch;
	
	private Integer unitid;
	
	// 当前库存数量.供调整时参考用.DB无此字段
	private Double stockQuantity;

	// 条码参考数量,DB无此字段
	private Double barcodeQuantity;
	
	private Double trsferQuantity;
	
	
    
    private Integer stockboxnum;
    
    private Double stockscatternum;
    
    private Integer boxnum;
    
    private Double scatternum;
    
    /**
     * 箱转换率
     */
    private Double xtsQuantity;
    /**
     * 散转换率
     */
    private Double stsQuantity;
    
    /**
     * 最终确认的箱数
     */
    private Integer realboxnum;
    /**
     * 最终确认的散数
     */
    private Double realscatternum;
    
    private String productnccode;
	
	public Double getBarcodeQuantity() {
		return barcodeQuantity;
	}

	public void setBarcodeQuantity(Double barcodeQuantity) {
		this.barcodeQuantity = barcodeQuantity;
	}
	

	public Integer getRealboxnum() {
		return realboxnum;
	}

	public void setRealboxnum(Integer realboxnum) {
		this.realboxnum = realboxnum;
	}

	public Double getRealscatternum() {
		return realscatternum;
	}

	public void setRealscatternum(Double realscatternum) {
		this.realscatternum = realscatternum;
	}


	// 检货小票内数量调整时用的临时数量,DB无此字段
	private Double tempQuantity;

	/** nullable persistent field */
	// 先进先出原则初始化的应出数量
	private Double quantity;

	/** nullable persistent field */
	// 实出数量.复核小票时用
	private Double realQuantity;
	//用于显示箱数
	private Double xnum;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTtdid() {
		return ttdid;
	}

	public void setTtdid(Integer ttdid) {
		this.ttdid = ttdid;
	}

	public Double getXnum() {
		return xnum;
	}

	public Integer getStockboxnum() {
		return stockboxnum;
	}

	public void setStockboxnum(Integer stockboxnum) {
		this.stockboxnum = stockboxnum;
	}

	public Double getStockscatternum() {
		return stockscatternum;
	}

	public Double getXtsQuantity() {
		return xtsQuantity;
	}

	public void setXtsQuantity(Double xtsQuantity) {
		this.xtsQuantity = xtsQuantity;
	}

	public Double getStsQuantity() {
		return stsQuantity;
	}

	public void setStsQuantity(Double stsQuantity) {
		this.stsQuantity = stsQuantity;
	}

	public void setStockscatternum(Double stockscatternum) {
		this.stockscatternum = stockscatternum;
	}

	public void setXnum(Double xnum) {
		this.xnum = xnum;
	}

	public Integer getUnitid() {
		return unitid;
	}

	public void setUnitid(Integer unitid) {
		this.unitid = unitid;
	}

	public Double getTrsferQuantity() {
		return trsferQuantity;
	}

	public void setTrsferQuantity(Double trsferQuantity) {
		this.trsferQuantity = trsferQuantity;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public Integer getBoxnum() {
		return boxnum;
	}

	public void setBoxnum(Integer boxnum) {
		this.boxnum = boxnum;
	}

	public Double getScatternum() {
		return scatternum;
	}

	public void setScatternum(Double scatternum) {
		this.scatternum = scatternum;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getWarehouseBit() {
		return warehouseBit;
	}

	public void setWarehouseBit(String warehouseBit) throws Exception {
		this.warehouseBit = warehouseBit;
//		AppWarehouse appWarehouse = new AppWarehouse();
//		warehouseBitName = appWarehouse.getWarehouseBitBywbid(warehouseBit)	.getBitName();
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

	public Double getRealQuantity() {
		return realQuantity;
	}

	public void setRealQuantity(Double realQuantity) {
		this.realQuantity = realQuantity;
	}

	public String getTtid() {
		return ttid;
	}

	public void setTtid(String ttid) {
		this.ttid = ttid;
	}

	public String getWarehouseBitName() {
		return warehouseBitName;
	}

	public void setWarehouseBitName(String warehouseBitName) {
		this.warehouseBitName = warehouseBitName;
	}

	public Double getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Double stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public Double getTempQuantity() {
		return tempQuantity;
	}

	public void setTempQuantity(Double tempQuantity) {
		this.tempQuantity = tempQuantity;
	}

	public String getProductnccode() {
		return productnccode;
	}

	public void setProductnccode(String productnccode) {
		this.productnccode = productnccode;
	}
}
