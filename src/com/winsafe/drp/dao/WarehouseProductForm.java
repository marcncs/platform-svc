package com.winsafe.drp.dao;

public class WarehouseProductForm {
	/** identifier field */
    private Integer id;

    /** persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;

    /** persistent field */
    private Integer unitid;
    
    private String batch;
    private String nccode;
    
    private String unitidname;

    /** nullable persistent field */
    private Double stockpile;
    
    private Double cost;
    
    private Double squantity;
    
    private Double price;
    
    private String unitList;
    
    private String countUnitName;
    
    private String packSizeName;
    
	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getSpecmode() {
		return specmode;
	}

	public void setSpecmode(String specmode) {
		this.specmode = specmode;
	}


	public Integer getUnitid() {
		return unitid;
	}

	public void setUnitid(Integer unitid) {
		this.unitid = unitid;
	}

	public String getUnitidname() {
		return unitidname;
	}

	public void setUnitidname(String unitidname) {
		this.unitidname = unitidname;
	}

	public Double getSquantity() {
		return squantity;
	}

	public void setSquantity(Double squantity) {
		this.squantity = squantity;
	}

	public void setStockpile(Double stockpile) {
		this.stockpile = stockpile;
	}

	public Double getStockpile() {
		return stockpile;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getNccode() {
		return nccode;
	}

	public void setNccode(String nccode) {
		this.nccode = nccode;
	}

	public String getUnitList() {
		return unitList;
	}

	public void setUnitList(String unitList) {
		this.unitList = unitList;
	}

	public String getCountUnitName() {
		return countUnitName;
	}

	public void setCountUnitName(String countUnitName) {
		this.countUnitName = countUnitName;
	}

	public String getPackSizeName() {
		return packSizeName;
	}

	public void setPackSizeName(String packSizeName) {
		this.packSizeName = packSizeName;
	}
	

}
