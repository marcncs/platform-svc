package com.winsafe.drp.dao;

public class OtherShipmentBillDetailForm {
	/** identifier field */
	private Long id;

	/** nullable persistent field */
	private String osid;

	/** persistent field */
	private String productid;

	/** nullable persistent field */
	private String productname;

	/** nullable persistent field */
	private String specmode;

	/** persistent field */
	private Integer unitid;

	private String unitidname;

	/** nullable persistent field */
	private String batch;

	/** nullable persistent field */
	private Double unitprice;

	/** nullable persistent field */
	private Double quantity;

	private Double takequantity;

	private Double cost;

	/** nullable persistent field */
	private Double subsum;

	private String nccode;

	private String unitList;

	// 计量单位(用于页面显示)
	private Integer countunit;
	// 计量单位名称(用于页面显示);
	private String countUnitName;
	// 数量(计量单位)(用于页面显示)
	private Double cUnitQuantity;

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOsid() {
		return osid;
	}

	public void setOsid(String osid) {
		this.osid = osid;
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

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getSpecmode() {
		return specmode;
	}

	public void setSpecmode(String specmode) {
		this.specmode = specmode;
	}

	public Double getSubsum() {
		return subsum;
	}

	public void setSubsum(Double subsum) {
		this.subsum = subsum;
	}


	public String getUnitidname() {
		return unitidname;
	}

	public void setUnitidname(String unitidname) {
		this.unitidname = unitidname;
	}

	public Double getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(Double unitprice) {
		this.unitprice = unitprice;
	}

	public Double getTakequantity() {
		return takequantity;
	}

	public void setTakequantity(Double takequantity) {
		this.takequantity = takequantity;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getUnitList() {
		return unitList;
	}

	public void setUnitList(String unitList) {
		this.unitList = unitList;
	}

	public Integer getCountunit() {
		return countunit;
	}

	public void setCountunit(Integer countunit) {
		this.countunit = countunit;
	}

	public String getCountUnitName() {
		return countUnitName;
	}

	public void setCountUnitName(String countUnitName) {
		this.countUnitName = countUnitName;
	}

	public Double getcUnitQuantity() {
		return cUnitQuantity;
	}

	public void setcUnitQuantity(Double cUnitQuantity) {
		this.cUnitQuantity = cUnitQuantity;
	}

	public String getNccode() {
		return nccode;
	}

	public void setNccode(String nccode) {
		this.nccode = nccode;
	}

	public Integer getUnitid() {
		return unitid;
	}

	public void setUnitid(Integer unitid) {
		this.unitid = unitid;
	}

	
}
