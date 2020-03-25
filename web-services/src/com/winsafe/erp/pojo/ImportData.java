package com.winsafe.erp.pojo;

public class ImportData {
	//单据日期
	String moveDate;
	//单据内部编号
	String nccode;
	//收货机构名称
	String receiveorganidname;
	//收货机构编号
	String receiveorganid;
	//计量单位数
	Double countQuantity;
	//件数
	Double quantity;
	//产品代码
	String productCode;
	//是否退货
	String isReturn;
	//出货机构名称
	String outOrganName;
	//出货仓库名称
	String outWarehouseName;
	
	public String getMoveDate() {
		return moveDate;
	}
	public void setMoveDate(String moveDate) {
		this.moveDate = moveDate;
	}
	public String getNccode() {
		return nccode;
	}
	public void setNccode(String nccode) {
		this.nccode = nccode;
	}
	public String getReceiveorganidname() {
		return receiveorganidname;
	}
	public void setReceiveorganidname(String receiveorganidname) {
		this.receiveorganidname = receiveorganidname;
	}
	public String getReceiveorganid() {
		return receiveorganid;
	}
	public void setReceiveorganid(String receiveorganid) {
		this.receiveorganid = receiveorganid;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getIsReturn() {
		return isReturn;
	}
	public void setIsReturn(String isReturn) {
		this.isReturn = isReturn;
	}
	public Double getCountQuantity() {
		return countQuantity;
	}
	public void setCountQuantity(Double countQuantity) {
		this.countQuantity = countQuantity;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public String getOutOrganName() {
		return outOrganName;
	}
	public void setOutOrganName(String outOrganName) {
		this.outOrganName = outOrganName;
	}
	public String getOutWarehouseName() {
		return outWarehouseName;
	}
	public void setOutWarehouseName(String outWarehouseName) {
		this.outWarehouseName = outWarehouseName;
	}
	
}
