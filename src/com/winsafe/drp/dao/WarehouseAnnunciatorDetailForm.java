package com.winsafe.drp.dao;

public class WarehouseAnnunciatorDetailForm {
	/** identifier field */
    private Long id;

    /** nullable persistent field */
    private Long waid;

    /** nullable persistent field */
    private String productid;
    
    private String productidname;
    
    private String specmode;

    /** nullable persistent field */
    private Double quantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getProductidname() {
		return productidname;
	}

	public void setProductidname(String productidname) {
		this.productidname = productidname;
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

	public Long getWaid() {
		return waid;
	}

	public void setWaid(Long waid) {
		this.waid = waid;
	}
}
