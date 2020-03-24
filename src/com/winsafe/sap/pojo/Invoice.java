package com.winsafe.sap.pojo;

import java.io.Serializable;

public class Invoice implements Serializable {
	private Long id;
	private String invoiceNumber;
	private String invoiceType;
	private String invoiceDate;
	private String deliveryNumber;
	private String partnSold;
	private String partnShip;
	private String invoiceLineItem;
	private String materialCode;
	private String batchNumber;
	private Double invoiceQty;
	private String netVal;
	//客户名称
	private String partnName;
	private String packSize;
	private String proName;
	
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getDeliveryNumber() {
		return deliveryNumber;
	}
	public void setDeliveryNumber(String deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}
	public String getPartnSold() {
		return partnSold;
	}
	public void setPartnSold(String partnSold) {
		this.partnSold = partnSold;
	}
	public String getPartnShip() {
		return partnShip;
	}
	public void setPartnShip(String partnShip) {
		this.partnShip = partnShip;
	}
	public String getInvoiceLineItem() {
		return invoiceLineItem;
	}
	public void setInvoiceLineItem(String invoiceLineItem) {
		this.invoiceLineItem = invoiceLineItem;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public Double getInvoiceQty() {
		return invoiceQty;
	}
	public void setInvoiceQty(Double invoiceQty) {
		this.invoiceQty = invoiceQty;
	}
	public String getNetVal() {
		return netVal;
	}
	public void setNetVal(String netVal) {
		this.netVal = netVal;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPartnName() {
		return partnName;
	}
	public void setPartnName(String partnName) {
		this.partnName = partnName;
	}
	public String getPackSize() {
		return packSize;
	}
	public void setPackSize(String packSize) {
		this.packSize = packSize;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	
}
