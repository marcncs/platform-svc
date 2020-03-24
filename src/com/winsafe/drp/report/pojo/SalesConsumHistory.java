package com.winsafe.drp.report.pojo;

import java.io.Serializable;
import java.util.Date;

public class SalesConsumHistory implements Serializable{

	private Integer id;
	//产品编号
	private String productId;
	//机构编号
	private String organId;
	private String warehouseId;
	private Integer year;
	private Integer month;
	
	private Double salesVolume;
	private Double salesValue;
	
	private Double consumVolume;
	private Double consumValue;
	private Double otherConsumVolume;
	
	
	private Double monthBeginInventory;
	private Double monthEndInventory;
	
	private Integer hasInvoice;
	
	private Date makeDate;
	
	private Integer version;
	
	private Double price;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Double getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(Double salesVolume) {
		this.salesVolume = salesVolume;
	}

	public Double getSalesValue() {
		return salesValue;
	}

	public void setSalesValue(Double salesValue) {
		this.salesValue = salesValue;
	}

	public Double getConsumVolume() {
		return consumVolume;
	}

	public void setConsumVolume(Double consumVolume) {
		this.consumVolume = consumVolume;
	}

	public Double getConsumValue() {
		return consumValue;
	}

	public void setConsumValue(Double consumValue) {
		this.consumValue = consumValue;
	}

	public Double getMonthBeginInventory() {
		return monthBeginInventory;
	}

	public void setMonthBeginInventory(Double monthBeginInventory) {
		this.monthBeginInventory = monthBeginInventory;
	}

	public Double getMonthEndInventory() {
		return monthEndInventory;
	}

	public void setMonthEndInventory(Double monthEndInventory) {
		this.monthEndInventory = monthEndInventory;
	}

	public Integer isHasInvoice() {
		return hasInvoice;
	}

	public void setHasInvoice(Integer hasInvoice) {
		this.hasInvoice = hasInvoice;
	}

	public Date getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}

	public Integer getHasInvoice() {
		return hasInvoice;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Double getOtherConsumVolume() {
		return otherConsumVolume;
	}

	public void setOtherConsumVolume(Double otherConsumVolume) {
		this.otherConsumVolume = otherConsumVolume;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}
