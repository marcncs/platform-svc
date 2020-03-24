package com.winsafe.drp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TTIdcodePDFBean {

	private String productid;
	
	private String productnamespec;
	
	private String startno;
	
	private String endno;
	
	private Double quantity;
	
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public String getProductnamespec() {
		return productnamespec;
	}
	public void setProductnamespec(String productnamespec) {
		this.productnamespec = productnamespec;
	}
	public String getStartno() {
		return startno;
	}
	public void setStartno(String startno) {
		this.startno = startno;
	}
	public String getEndno() {
		return endno;
	}
	public void setEndno(String endno) {
		this.endno = endno;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
}
