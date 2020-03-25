package com.winsafe.drp.dao;


public class ProviderProductForm {
	/** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String providerid;
    
    private String providename;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String pvproductname;

    /** nullable persistent field */
    private String pvspecmode;

    /** nullable persistent field */
    private Integer countunit;
    
    private String unitname;

    /** nullable persistent field */
    private String barcode;

    /** nullable persistent field */
    private Double price;

    /** nullable persistent field */
    private String pricedate;
    
    private String brandname;

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Integer getCountunit() {
		return countunit;
	}

	public void setCountunit(Integer countunit) {
		this.countunit = countunit;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getPricedate() {
		return pricedate;
	}

	public void setPricedate(String pricedate) {
		this.pricedate = pricedate;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}


	public String getProvidename() {
		return providename;
	}

	public void setProvidename(String providename) {
		this.providename = providename;
	}

	public String getProviderid() {
		return providerid;
	}

	public void setProviderid(String providerid) {
		this.providerid = providerid;
	}

	public String getPvproductname() {
		return pvproductname;
	}

	public void setPvproductname(String pvproductname) {
		this.pvproductname = pvproductname;
	}

	public String getPvspecmode() {
		return pvspecmode;
	}

	public void setPvspecmode(String pvspecmode) {
		this.pvspecmode = pvspecmode;
	}

	public String getUnitname() {
		return unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}
	
	
}
