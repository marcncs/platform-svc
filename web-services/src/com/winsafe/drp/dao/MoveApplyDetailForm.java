package com.winsafe.drp.dao;

public class MoveApplyDetailForm {
	/** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String maid;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String productname;

    /** nullable persistent field */
    private String specmode;

    /** nullable persistent field */
    private Integer unitid;
    
    private String unitidname;

    /** nullable persistent field */
    private Double unitprice;

    /** nullable persistent field */
    private Double quantity;

    /** nullable persistent field */
    private Double canquantity;
    
    private Double alreadyquantity;

    /** nullable persistent field */
    private Double subsum;
    
    // 计量单位(用于页面显示)
    private Integer countunit;
    // 计量单位名称(用于页面显示);
    private String countUnitName;
    // 数量(计量单位)(用于页面显示)
    private Double cUnitQuantity;
    
    private String unitList;

	public Double getCanquantity() {
		return canquantity;
	}

	public void setCanquantity(Double canquantity) {
		this.canquantity = canquantity;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMaid() {
		return maid;
	}

	public void setMaid(String maid) {
		this.maid = maid;
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

	public Double getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(Double unitprice) {
		this.unitprice = unitprice;
	}

	public Double getAlreadyquantity() {
		return alreadyquantity;
	}

	public void setAlreadyquantity(Double alreadyquantity) {
		this.alreadyquantity = alreadyquantity;
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

	public String getUnitList() {
		return unitList;
	}

	public void setUnitList(String unitList) {
		this.unitList = unitList;
	}
	
}
