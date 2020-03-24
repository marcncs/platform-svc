package com.winsafe.drp.dao;


public class StockWasteBookForm {
	/** identifier field */
    private Long id;

    /** nullable persistent field */
    private String productid;
    
    private String productidname;
    private String productname;
    private Integer countunit;

    /** nullable persistent field */
    private String batch;

    /** nullable persistent field */
    private String warehouseid;
    
    private String warehouseidname;
    
    private String warehousebit;
    
    private String bc;

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
    
    private Integer unitid;
    
    private String unitidname;
    
    private String nccode;
    
    private Double xnum;
    private Double scatterNum;
    private String specmode;
    private String sortname;

	public Integer getCountunit() {
		return countunit;
	}

	public void setCountunit(Integer countunit) {
		this.countunit = countunit;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}
	

	public String getNccode() {
		return nccode;
	}

	public void setNccode(String nccode) {
		this.nccode = nccode;
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

	public String getProductidname() {
		return productidname;
	}

	public void setProductidname(String productidname) {
		this.productidname = productidname;
	}

	public String getRecorddate() {
		return recorddate;
	}

	public void setRecorddate(String recorddate) {
		this.recorddate = recorddate;
	}

	public String getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

	public String getWarehouseidname() {
		return warehouseidname;
	}

	public void setWarehouseidname(String warehouseidname) {
		this.warehouseidname = warehouseidname;
	}

	public String getBc() {
		return bc;
	}

	public void setBc(String bc) {
		this.bc = bc;
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

	public String getWarehousebit() {
		return warehousebit;
	}

	public void setWarehousebit(String warehousebit) {
		this.warehousebit = warehousebit;
	}

	public Double getXnum() {
		return xnum;
	}

	public void setXnum(Double xnum) {
		this.xnum = xnum;
	}

	public Double getScatterNum() {
		return scatterNum;
	}

	public void setScatterNum(Double scatterNum) {
		this.scatterNum = scatterNum;
	}

	public String getSpecmode() {
		return specmode;
	}

	public void setSpecmode(String specmode) {
		this.specmode = specmode;
	}

	public String getSortname() {
		return sortname;
	}

	public void setSortname(String sortname) {
		this.sortname = sortname;
	}
	
	
	
}
