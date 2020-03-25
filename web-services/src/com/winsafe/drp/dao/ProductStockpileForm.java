package com.winsafe.drp.dao;

import java.util.Date;

public class ProductStockpileForm {
	/** identifier field */
    private Long id;

    /** persistent field */
    private String productid;

    /** nullable persistent field */
    private String psproductname;

    /** nullable persistent field */
    private String psspecmode;

    /** persistent field */
    private Integer countunit;
    
    private String unitname;

    /** nullable persistent field */
    private String batch;

    /** nullable persistent field */
    private String barcode;

    /** persistent field */
    private String warehouseid;
    
    private String warehourseidname;
    
    private String warehousebit;

    /** nullable persistent field */
    private Double stockpile;

    /** nullable persistent field */
    private Double prepareout;

    /** nullable persistent field */
    private Integer islock;
    
    private String islockname;
    
    private Double cost;
    
    private Double totalcost;
    
    private String specmode;
    
    private Double strstockpile;
        
    private String strprepareout;
    
    private Double allstockpile;
    
    private Double squantity;
    
    private Integer intstockpile;
    
    private String nccode;
    //实际箱数量
    private Double xnum;
    private Double scatterNum;
    
    //检验状态
    private Integer verifyStatus;
    //备注
    private String remark;
    
    //检验日期
    private Date verifydate;

    private String sortName;
    private String date;
    private String billno;
    
    private Double repairstockpile;
    private Double totalstockpile;
    
    private Double assistBoxStockpile;
	private Double assistEAStockpile;
	
	//根据库龄显示的颜色 格式为：#FF0000
	private String tagColor;
	//库龄
	private Integer stockPileAgeing;
	
	//所属的库龄范围
	private Integer minValue;
	private Integer maxValue;
	
	//日均销量
	private Double salesAvg;
	
	//库存天数
	private Double stockPileDays;
	
	
	private String turnoverRate;
	//生产日期
	private String productionDate;
	// 到期日期
	private String expiryDate;
	
	//规格明细
	private String packSizeName;
	
	private Date makedate;
	
	
    public String getTurnoverRate() {
		return turnoverRate;
	}

	public void setTurnoverRate(String turnoverRate) {
		this.turnoverRate = turnoverRate;
	}

	public Double getStockPileDays() {
		return stockPileDays;
	}

	public void setStockPileDays(Double stockPileDays) {
		this.stockPileDays = stockPileDays;
	}

	public Double getSalesAvg() {
		return salesAvg;
	}

	public void setSalesAvg(Double salesAvg) {
		this.salesAvg = salesAvg;
	}

	public Integer getMinValue() {
		return minValue;
	}

	public void setMinValue(Integer minValue) {
		this.minValue = minValue;
	}

	public Integer getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}

	public Integer getStockPileAgeing() {
		return stockPileAgeing;
	}

	public void setStockPileAgeing(Integer stockPileAgeing) {
		this.stockPileAgeing = stockPileAgeing;
	}

	public String getTagColor() {
		return tagColor;
	}

	public void setTagColor(String tagColor) {
		this.tagColor = tagColor;
	}

	public Date getVerifydate() {
		return verifydate;
	}

	public void setVerifydate(Date verifydate) {
		this.verifydate = verifydate;
	}
	public String getSpecmode() {
		return specmode;
	}

	public void setSpecmode(String specmode) {
		this.specmode = specmode;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	
	public Integer getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getCountunit() {
		return countunit;
	}
	

	public String getNccode() {
		return nccode;
	}

	public void setNccode(String nccode) {
		this.nccode = nccode;
	}

	public void setCountunit(Integer countunit) {
		this.countunit = countunit;
	}

	public Double getXnum() {
		return xnum;
	}

	public void setXnum(Double xnum) {
		this.xnum = xnum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getIslock() {
		return islock;
	}

	public void setIslock(Integer islock) {
		this.islock = islock;
	}

	public String getIslockname() {
		return islockname;
	}

	public void setIslockname(String islockname) {
		this.islockname = islockname;
	}

	public Double getPrepareout() {
		return prepareout;
	}

	public void setPrepareout(Double prepareout) {
		this.prepareout = prepareout;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}


	public String getPsproductname() {
		return psproductname;
	}

	public void setPsproductname(String psproductname) {
		this.psproductname = psproductname;
	}

	public String getPsspecmode() {
		return psspecmode;
	}

	public void setPsspecmode(String psspecmode) {
		this.psspecmode = psspecmode;
	}

	public Double getStockpile() {
		return stockpile;
	}

	public void setStockpile(Double stockpile) {
		this.stockpile = stockpile;
	}

	public String getUnitname() {
		return unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	public String getWarehourseidname() {
		return warehourseidname;
	}

	public void setWarehourseidname(String warehourseidname) {
		this.warehourseidname = warehourseidname;
	}

	public String getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}
	
	

	public String getWarehousebit() {
		return warehousebit;
	}

	public void setWarehousebit(String warehousebit) {
		this.warehousebit = warehousebit;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getStrstockpile() {
		return strstockpile;
	}

	public void setStrstockpile(Double strstockpile) {
		this.strstockpile = strstockpile;
	}

	public String getStrprepareout() {
		return strprepareout;
	}

	public void setStrprepareout(String strprepareout) {
		this.strprepareout = strprepareout;
	}

	public Double getAllstockpile() {
		return allstockpile;
	}

	public void setAllstockpile(Double allstockpile) {
		this.allstockpile = allstockpile;
	}

	public Double getSquantity() {
		return squantity;
	}

	public void setSquantity(Double squantity) {
		this.squantity = squantity;
	}

	public Integer getIntstockpile() {
		return intstockpile;
	}

	public void setIntstockpile(Integer intstockpile) {
		this.intstockpile = intstockpile;
	}

	public Double getTotalcost() {
		return totalcost;
	}

	public void setTotalcost(Double totalcost) {
		this.totalcost = totalcost;
	}

	public Double getScatterNum() {
		return scatterNum;
	}

	public void setScatterNum(Double scatterNum) {
		this.scatterNum = scatterNum;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	public Double getRepairstockpile() {
		return repairstockpile;
	}

	public void setRepairstockpile(Double repairstockpile) {
		this.repairstockpile = repairstockpile;
	}

	public Double getTotalstockpile() {
		return totalstockpile;
	}

	public void setTotalstockpile(Double totalstockpile) {
		this.totalstockpile = totalstockpile;
	}

	public Double getAssistBoxStockpile() {
		return assistBoxStockpile;
	}

	public void setAssistBoxStockpile(Double assistBoxStockpile) {
		this.assistBoxStockpile = assistBoxStockpile;
	}

	public Double getAssistEAStockpile() {
		return assistEAStockpile;
	}

	public void setAssistEAStockpile(Double assistEAStockpile) {
		this.assistEAStockpile = assistEAStockpile;
	}

	public String getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getPackSizeName() {
		return packSizeName;
	}

	public void setPackSizeName(String packSizeName) {
		this.packSizeName = packSizeName;
	}

	public Date getMakedate() {
		return makedate;
	}

	public void setMakedate(Date makedate) {
		this.makedate = makedate;
	}
	
}
