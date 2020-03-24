package com.winsafe.drp.dao;

public class StockAlterMoveDetailFormBean {

	public StockAlterMoveDetailFormBean() {
	}
	
	
	public StockAlterMoveDetailFormBean(String productnccode,
			String productName, Integer unit, Double quantity, String idcode,
			String unitprice, String subsum, String tax, String remake,
			String vIPNo, String colour, String size, String allSize) {
		this.productnccode = productnccode;
		this.productName = productName;
		this.unit = unit;
		this.quantity = quantity;
		this.idcode = idcode;
		this.unitprice = unitprice;
		this.subsum = subsum;
		this.tax = tax;
		this.remake = remake;
		VIPNo = vIPNo;
		this.colour = colour;
		this.size = size;
		this.allSize = allSize;
	}
  
	private  String productnccode;  //产品编号
	private  String productName;    //商品全名
	private  Integer unit;          //单位
	private  Double quantity;       //数量
	private  String idcode;         //商品条码
	private  String unitprice;      //单价
	private  String subsum;         //金额
	private  String tax;             //扣率
	private  String remake;           //备注	
	private  String VIPNo;           //备注	
	private  String colour;           //颜色
	private  String  size;            //尺码
	private  String  allSize;          //全部尺码
	public String getProductnccode() {
		return productnccode;
	}
	public void setProductnccode(String productnccode) {
		this.productnccode = productnccode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getUnit() {
		return unit;
	}
	public void setUnit(Integer unit) {
		this.unit = unit;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public String getIdcode() {
		return idcode;
	}
	public void setIdcode(String idcode) {
		this.idcode = idcode;
	}
	public String getUnitprice() {
		return unitprice;
	}
	public void setUnitprice(String unitprice) {
		this.unitprice = unitprice;
	}
	public String getSubsum() {
		return subsum;
	}
	public void setSubsum(String subsum) {
		this.subsum = subsum;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public String getRemake() {
		return remake;
	}
	public void setRemake(String remake) {
		this.remake = remake;
	}
	public String getVIPNo() {
		return VIPNo;
	}
	public void setVIPNo(String vIPNo) {
		VIPNo = vIPNo;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getAllSize() {
		return allSize;
	}
	public void setAllSize(String allSize) {
		this.allSize = allSize;
	}


	

}
