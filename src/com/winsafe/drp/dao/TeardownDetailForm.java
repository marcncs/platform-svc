package com.winsafe.drp.dao;

public class TeardownDetailForm {
	 /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String tid;

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
    private Long warehouseinid;
    
    private String warehouseinidname;

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

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
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

	public Long getWarehouseinid() {
		return warehouseinid;
	}

	public void setWarehouseinid(Long warehouseinid) {
		this.warehouseinid = warehouseinid;
	}

	public String getWarehouseinidname() {
		return warehouseinidname;
	}

	public void setWarehouseinidname(String warehouseinidname) {
		this.warehouseinidname = warehouseinidname;
	}
    
    
}
