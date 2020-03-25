package com.winsafe.drp.dao;

import java.util.Date;

public class FIFOAlertReport {
	private Long id;
	private String regionName;
	private String province;
	private String organId;
	private String organName;
	private String sortName;
	private String productId;
	private String productName;
	private String packSizeName;
	private Date viewDate;
	private String stockBatch;
	private Double stockPile;
	private String stockProductionDate;
	private String stockExpiryDate;
	private Double shipQuantity;
	private String shipBatch;
	private String shipProductionDate;
	private String shipExpiryDate;
	private Double quantity;
	private String billNo;
	private Date beginDate;
	private Date endDate;
	private String warehouseId;
	private Integer unitId;
	private String mCode;
	private String warehouseName;
	private String viewDateStr;
	private Date billDate;
	private String oecode;
	//产品英文
	private String productNameen;
	//规格英文
	private String packSizeNameEn;
	//物料中文
	private String matericalChDes;
	//物料英文
	private String matericalEnDes;
	
	private Integer differentDays;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
	public String getSortName() {
		return sortName;
	}
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPackSizeName() {
		return packSizeName;
	}
	public void setPackSizeName(String packSizeName) {
		this.packSizeName = packSizeName;
	}
	public Date getViewDate() {
		return viewDate;
	}
	public void setViewDate(Date viewDate) {
		this.viewDate = viewDate;
	}
	public String getStockBatch() {
		return stockBatch;
	}
	public void setStockBatch(String stockBatch) {
		this.stockBatch = stockBatch;
	}
	public Double getStockPile() {
		return stockPile;
	}
	public void setStockPile(Double stockPile) {
		this.stockPile = stockPile;
	}
	public String getStockProductionDate() {
		return stockProductionDate;
	}
	public void setStockProductionDate(String stockProductionDate) {
		this.stockProductionDate = stockProductionDate;
	}
	public String getStockExpiryDate() {
		return stockExpiryDate;
	}
	public void setStockExpiryDate(String stockExpiryDate) {
		this.stockExpiryDate = stockExpiryDate;
	}
	public String getShipProductionDate() {
		return shipProductionDate;
	}
	public void setShipProductionDate(String shipProductionDate) {
		this.shipProductionDate = shipProductionDate;
	}
	public String getShipExpiryDate() {
		return shipExpiryDate;
	}
	public void setShipExpiryDate(String shipExpiryDate) {
		this.shipExpiryDate = shipExpiryDate;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getShipBatch() {
		return shipBatch;
	}
	public void setShipBatch(String shipBatch) {
		this.shipBatch = shipBatch;
	}
	public Double getShipQuantity() {
		return shipQuantity;
	}
	public void setShipQuantity(Double shipQuantity) {
		this.shipQuantity = shipQuantity;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	public Integer getUnitId() {
		return unitId;
	}
	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}
	public String getmCode() {
		return mCode;
	}
	public void setmCode(String mCode) {
		this.mCode = mCode;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getViewDateStr() {
		return viewDateStr;
	}
	public void setViewDateStr(String viewDateStr) {
		this.viewDateStr = viewDateStr;
	}
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public String getOecode() {
		return oecode;
	}
	public void setOecode(String oecode) {
		this.oecode = oecode;
	}
	public String getProductNameen() {
		return productNameen;
	}
	public void setProductNameen(String productNameen) {
		this.productNameen = productNameen;
	}
	public String getPackSizeNameEn() {
		return packSizeNameEn;
	}
	public void setPackSizeNameEn(String packSizeNameEn) {
		this.packSizeNameEn = packSizeNameEn;
	}
	public String getMatericalChDes() {
		return matericalChDes;
	}
	public void setMatericalChDes(String matericalChDes) {
		this.matericalChDes = matericalChDes;
	}
	public Integer getDifferentDays() {
		return differentDays;
	}
	public void setDifferentDays(Integer differentDays) {
		this.differentDays = differentDays;
	}
	public String getMatericalEnDes() {
		return matericalEnDes;
	}
	public void setMatericalEnDes(String matericalEnDes) {
		this.matericalEnDes = matericalEnDes;
	}
	
}
