package com.winsafe.drp.action.newreport;

import java.io.Serializable;
import java.util.Date;

public class SalesLnventoryReportForm implements Serializable {
	// 大区
	private String regionName;
	private String region;
	// 省份
	private String province;
	
	private String packSizeName;
	// 机构代码
	private String organId;
	// 机构名称
	private String organName;
	// 物料号
	private String mCode;
	// 物料中文
	private String matericalChDes;
	// 物料英文
	private String matericalEnDes;
	// 产品名称
	private String productNamecn;
	private String productId;
	// 产品英文           
	private String productNameen;
	
	// 规格英文
	private String packSizeNameEn;
	// 销售销售量
	private Double monthSalesVolume;
	private Double monthConsumptionVolume;
	private Double monthOtherConsumptionVolume;
	private Double salesVolume;
	private Double consumptionVolume;
	private Double otherConsumptionVolume;
	// 销售销售金额
	private Double monthSalesValue;
	private Double monthConsumptionValue;
	private Double monthOtherConsumptionValue;
	private Double salesValue;
	private Double consumptionValue;
	private Double otherConsumptionValue;
	//金额
	private Double  value;
	
	// 月初库存
	private Double monthBeginInventory;
	// 月初库存金额       
	private Double monthBeginInventoryValue;
	// 月末库存金额
	private Double monthEndInventory;
	private Double monthEndInventoryValue;
	
	// 年初库存
	private Double yearBeginInventory;
	// 年初库存金额       
	private Double yearBeginInventoryValue;
	// 年末库存v   
	private Double yearEndInventory;
	// 年末库存金额
	private Double yearEndInventoryValue;
	
	
	
	// 创建日期
	private Date makeDate;
	// 月
	private Double month;
	// 年
	private Double year;
	// 仓库代码
	private String warehouseId;
	// 仓库代码
	private String warehouseName;
	private Double boxquantity;
	
	private String productName;

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPackSizeName() {
		return packSizeName;
	}

	public void setPackSizeName(String packSizeName) {
		this.packSizeName = packSizeName;
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

	public String getmCode() {
		return mCode;
	}

	public void setmCode(String mCode) {
		this.mCode = mCode;
	}

	public String getMatericalChDes() {
		return matericalChDes;
	}

	public void setMatericalChDes(String matericalChDes) {
		this.matericalChDes = matericalChDes;
	}

	public String getMatericalEnDes() {
		return matericalEnDes;
	}

	public void setMatericalEnDes(String matericalEnDes) {
		this.matericalEnDes = matericalEnDes;
	}

	public String getProductNamecn() {
		return productNamecn;
	}

	public void setProductNamecn(String productNamecn) {
		this.productNamecn = productNamecn;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
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

	public Double getMonthSalesVolume() {
		return monthSalesVolume;
	}

	public void setMonthSalesVolume(Double monthSalesVolume) {
		this.monthSalesVolume = monthSalesVolume;
	}

	public Double getMonthConsumptionVolume() {
		return monthConsumptionVolume;
	}

	public void setMonthConsumptionVolume(Double monthConsumptionVolume) {
		this.monthConsumptionVolume = monthConsumptionVolume;
	}

	public Double getMonthOtherConsumptionVolume() {
		return monthOtherConsumptionVolume;
	}

	public void setMonthOtherConsumptionVolume(Double monthOtherConsumptionVolume) {
		this.monthOtherConsumptionVolume = monthOtherConsumptionVolume;
	}

	public Double getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(Double salesVolume) {
		this.salesVolume = salesVolume;
	}

	public Double getConsumptionVolume() {
		return consumptionVolume;
	}

	public void setConsumptionVolume(Double consumptionVolume) {
		this.consumptionVolume = consumptionVolume;
	}

	public Double getOtherConsumptionVolume() {
		return otherConsumptionVolume;
	}

	public void setOtherConsumptionVolume(Double otherConsumptionVolume) {
		this.otherConsumptionVolume = otherConsumptionVolume;
	}

	public Double getMonthSalesValue() {
		return monthSalesValue;
	}

	public void setMonthSalesValue(Double monthSalesValue) {
		this.monthSalesValue = monthSalesValue;
	}

	public Double getMonthConsumptionValue() {
		return monthConsumptionValue;
	}

	public void setMonthConsumptionValue(Double monthConsumptionValue) {
		this.monthConsumptionValue = monthConsumptionValue;
	}

	public Double getMonthOtherConsumptionValue() {
		return monthOtherConsumptionValue;
	}

	public void setMonthOtherConsumptionValue(Double monthOtherConsumptionValue) {
		this.monthOtherConsumptionValue = monthOtherConsumptionValue;
	}

	public Double getSalesValue() {
		return salesValue;
	}

	public void setSalesValue(Double salesValue) {
		this.salesValue = salesValue;
	}

	public Double getConsumptionValue() {
		return consumptionValue;
	}

	public void setConsumptionValue(Double consumptionValue) {
		this.consumptionValue = consumptionValue;
	}

	public Double getOtherConsumptionValue() {
		return otherConsumptionValue;
	}

	public void setOtherConsumptionValue(Double otherConsumptionValue) {
		this.otherConsumptionValue = otherConsumptionValue;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Double getMonthBeginInventory() {
		return monthBeginInventory;
	}

	public void setMonthBeginInventory(Double monthBeginInventory) {
		this.monthBeginInventory = monthBeginInventory;
	}

	public Double getMonthBeginInventoryValue() {
		return monthBeginInventoryValue;
	}

	public void setMonthBeginInventoryValue(Double monthBeginInventoryValue) {
		this.monthBeginInventoryValue = monthBeginInventoryValue;
	}

	public Double getMonthEndInventory() {
		return monthEndInventory;
	}

	public void setMonthEndInventory(Double monthEndInventory) {
		this.monthEndInventory = monthEndInventory;
	}

	public Double getMonthEndInventoryValue() {
		return monthEndInventoryValue;
	}

	public void setMonthEndInventoryValue(Double monthEndInventoryValue) {
		this.monthEndInventoryValue = monthEndInventoryValue;
	}

	public Double getYearBeginInventory() {
		return yearBeginInventory;
	}

	public void setYearBeginInventory(Double yearBeginInventory) {
		this.yearBeginInventory = yearBeginInventory;
	}

	public Double getYearBeginInventoryValue() {
		return yearBeginInventoryValue;
	}

	public void setYearBeginInventoryValue(Double yearBeginInventoryValue) {
		this.yearBeginInventoryValue = yearBeginInventoryValue;
	}

	public Double getYearEndInventory() {
		return yearEndInventory;
	}

	public void setYearEndInventory(Double yearEndInventory) {
		this.yearEndInventory = yearEndInventory;
	}

	public Double getYearEndInventoryValue() {
		return yearEndInventoryValue;
	}

	public void setYearEndInventoryValue(Double yearEndInventoryValue) {
		this.yearEndInventoryValue = yearEndInventoryValue;
	}

	public Date getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}

	public Double getMonth() {
		return month;
	}

	public void setMonth(Double month) {
		this.month = month;
	}

	public Double getYear() {
		return year;
	}

	public void setYear(Double year) {
		this.year = year;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Double getBoxquantity() {
		return boxquantity;
	}

	public void setBoxquantity(Double boxquantity) {
		this.boxquantity = boxquantity;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

}
