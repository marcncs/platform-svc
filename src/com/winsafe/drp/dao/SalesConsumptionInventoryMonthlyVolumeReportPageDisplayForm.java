package com.winsafe.drp.dao;

import java.util.List;

public class SalesConsumptionInventoryMonthlyVolumeReportPageDisplayForm {
	private String regionCode;
	private String regionName;
	private String oProvinceId;
	private String oProvinceName;
	private String oecode;
	private String organId;
	private String organName;
	private String warehouseId;
	private String warehouseName;
	private String productId;
	private String mcode;
	private String matericalchdes;
	private String matericalendes;
	private String productName;
	private String productNameEn;
	private String packsizeNameEn;
	private String key;
	private List<SalesConsumptionInventory> salesConsumptionInventoryBeginList;
	private List<SalesConsumptionInventory> salesConsumptionInventoryList;
	
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getoProvinceId() {
		return oProvinceId;
	}
	public void setoProvinceId(String oProvinceId) {
		this.oProvinceId = oProvinceId;
	}
	public String getoProvinceName() {
		return oProvinceName;
	}
	public void setoProvinceName(String oProvinceName) {
		this.oProvinceName = oProvinceName;
	}
	public String getOecode() {
		return oecode;
	}
	public void setOecode(String oecode) {
		this.oecode = oecode;
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
	public String getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getMcode() {
		return mcode;
	}
	public void setMcode(String mcode) {
		this.mcode = mcode;
	}
	public String getMatericalchdes() {
		return matericalchdes;
	}
	public void setMatericalchdes(String matericalchdes) {
		this.matericalchdes = matericalchdes;
	}
	public String getMatericalendes() {
		return matericalendes;
	}
	public void setMatericalendes(String matericalendes) {
		this.matericalendes = matericalendes;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductNameEn() {
		return productNameEn;
	}
	public void setProductNameEn(String productNameEn) {
		this.productNameEn = productNameEn;
	}
	public String getPacksizeNameEn() {
		return packsizeNameEn;
	}
	public void setPacksizeNameEn(String packsizeNameEn) {
		this.packsizeNameEn = packsizeNameEn;
	}
	public List<SalesConsumptionInventory> getSalesConsumptionInventoryList() {
		return salesConsumptionInventoryList;
	}
	public void setSalesConsumptionInventoryList(
			List<SalesConsumptionInventory> salesConsumptionInventoryList) {
		this.salesConsumptionInventoryList = salesConsumptionInventoryList;
	}
	public List<SalesConsumptionInventory> getSalesConsumptionInventoryBeginList() {
		return salesConsumptionInventoryBeginList;
	}
	public void setSalesConsumptionInventoryBeginList(
			List<SalesConsumptionInventory> salesConsumptionInventoryBeginList) {
		this.salesConsumptionInventoryBeginList = salesConsumptionInventoryBeginList;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}