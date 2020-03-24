package com.winsafe.notification.pojo;

import java.io.Serializable;

public class NotificationDetail  implements Serializable{
	private Long id;
	
	private String lineItem;
	//送货单号
	private String deliveryNo;
	//物料号
	private String materialCode;
	//产品
	private String products;
	//规格
	private String packSize;
	//数量
	private String quantity;
	//箱数
	private Integer casesNo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDeliveryNo() {
		return deliveryNo;
	}
	public void setDeliveryNo(String deliveryNo) {
		this.deliveryNo = deliveryNo;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getProducts() {
		return products;
	}
	public void setProducts(String products) {
		this.products = products;
	}
	public String getPackSize() {
		return packSize;
	}
	public void setPackSize(String packSize) {
		this.packSize = packSize;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public Integer getCasesNo() {
		return casesNo;
	}
	public void setCasesNo(Integer casesNo) {
		this.casesNo = casesNo;
	}
	public String getLineItem() {
		return lineItem;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	
}
