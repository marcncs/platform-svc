package com.winsafe.drp.dao;

public class StockWasteBookObject {
	/** identifier field */
    private Long id;

    /** nullable persistent field */
    private String productid;

    /** nullable persistent field */
    private String batch;

    /** nullable persistent field */
    private Long warehouseid;

    /** nullable persistent field */
    private String billcode;
    
    private String memo;

    /** nullable persistent field */
    private Double cyclefirstquantity;

    /** nullable persistent field */
    private Double cycleinquantity;
    
    private Double cycleoutquantity;

    /** nullable persistent field */
    private Double cyclebalancequantity;

    /** nullable persistent field */
    private String recorddate;

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getBillcode() {
		return billcode;
	}

	public void setBillcode(String billcode) {
		this.billcode = billcode;
	}

	public Double getCyclebalancequantity() {
		return cyclebalancequantity;
	}

	public void setCyclebalancequantity(Double cyclebalancequantity) {
		this.cyclebalancequantity = cyclebalancequantity;
	}

	public Double getCyclefirstquantity() {
		return cyclefirstquantity;
	}

	public void setCyclefirstquantity(Double cyclefirstquantity) {
		this.cyclefirstquantity = cyclefirstquantity;
	}

	public Double getCycleinquantity() {
		return cycleinquantity;
	}

	public void setCycleinquantity(Double cycleinquantity) {
		this.cycleinquantity = cycleinquantity;
	}

	public Double getCycleoutquantity() {
		return cycleoutquantity;
	}

	public void setCycleoutquantity(Double cycleoutquantity) {
		this.cycleoutquantity = cycleoutquantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getRecorddate() {
		return recorddate;
	}

	public void setRecorddate(String recorddate) {
		this.recorddate = recorddate;
	}

	public Long getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(Long warehouseid) {
		this.warehouseid = warehouseid;
	}
}
