package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.winsafe.drp.action.newreport.MonthlyData;

public class SalesConsumMonthReportForm implements Serializable {
	/** */
	private static final long serialVersionUID = 1L;
	// 大区
	private String region;
	private String regionName;
	// 省份
	private String province;
	// 机构代码
	private String organId;
	// 机构名称
	private String organName;
	// 仓库代码
	private String warehouseId;
	// 仓库代码
	private String warehouseName;
	// 产品类别
	private String psName;
	// 产品id
	private String productId;
	// 产品名称
	private String productName;
	private String productNamecn;
	private String productNameen;
	// 物料号
	private String mCode;
	// 物料中文
	private String matericalChDes;
	// 物料英文
	private String matericalEnDes;
	private String beginDate;
	private String endDate;
	//产品规格
	private String packSizeName;
	private String packSizeNameEn;
	//编号
	private Double id;
	//产品编号
	private String productID;
	//机构编号
	private String organID;
	private String oecode;
	//月
	private Integer month;
	//年
	private Integer year;
	//销售量
	private Double salesVolume;
	//销售金额
	private Double salesValue;
	//消耗量
	private Double comsunptionVolume;
	//消耗金额
	private Double comsunptionValue;
	//月初库存
	private Double monthBeginInventory;
	//月末库存
	private double monthEndInventory;
	//是否有发票
	private Double hasinvoice;
	//仓库编号
	private String warehouseID;
	//创建日期
	private Date makeDate;
	//其他消耗
	private Double otherConsumVolume;
	private Double otherConsumValue;
	//版本控制
	private Double version;
	//小包装到计量单位装换率
	private Double boxQuantity;
	
	private String owQuantity;
	private Double stockpile;
	private Double value;
	private List<MonthlyData> monthlyDataList;
	
	
	public Double getBoxQuantity() {
		return boxQuantity;
	}
	public void setBoxQuantity(Double boxQuantity) {
		this.boxQuantity = boxQuantity;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public Double getStockpile() {
		return stockpile;
	}
	public void setStockpile(Double stockpile) {
		this.stockpile = stockpile;
	}
	public String getOwQuantity() {
		return owQuantity;
	}
	public void setOwQuantity(String owQuantity) {
		this.owQuantity = owQuantity;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
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
	public String getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getPsName() {
		return psName;
	}
	public void setPsName(String psName) {
		this.psName = psName;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
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
	public Double getId() {
		return id;
	}
	public void setId(Double id) {
		this.id = id;
	}
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public String getOrganID() {
		return organID;
	}
	public void setOrganID(String organID) {
		this.organID = organID;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Double getSalesVolume() {
		return salesVolume;
	}
	public void setSalesVolume(Double salesVolume) {
		this.salesVolume = salesVolume;
	}
	public Double getSalesValue() {
		return salesValue;
	}
	public void setSalesValue(Double salesValue) {
		this.salesValue = salesValue;
	}
	public Double getComsunptionVolume() {
		return comsunptionVolume;
	}
	public void setComsunptionVolume(Double comsunptionVolume) {
		this.comsunptionVolume = comsunptionVolume;
	}
	public Double getMonthBeginInventory() {
		return monthBeginInventory;
	}
	public void setMonthBeginInventory(Double monthBeginInventory) {
		this.monthBeginInventory = monthBeginInventory;
	}
	public double getMonthEndInventory() {
		return monthEndInventory;
	}
	public void setMonthEndInventory(double monthEndInventory) {
		this.monthEndInventory = monthEndInventory;
	}
	public Double getHasinvoice() {
		return hasinvoice;
	}
	public void setHasinvoice(Double hasinvoice) {
		this.hasinvoice = hasinvoice;
	}
	public String getWarehouseID() {
		return warehouseID;
	}
	public void setWarehouseID(String warehouseID) {
		this.warehouseID = warehouseID;
	}
	public Date getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}
	public Double getOtherConsumVolume() {
		return otherConsumVolume;
	}
	public void setOtherConsumVolume(Double otherConsumVolume) {
		this.otherConsumVolume = otherConsumVolume;
	}
	public Double getVersion() {
		return version;
	}
	public void setVersion(Double version) {
		this.version = version;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getPackSizeName() {
		return packSizeName;
	}
	public void setPackSizeName(String packSizeName) {
		this.packSizeName = packSizeName;
	}
	public SalesConsumMonthReportForm() {
	
	}
	public String getProductNamecn() {
		return productNamecn;
	}
	public void setProductNamecn(String productNamecn) {
		this.productNamecn = productNamecn;
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
	public Double getComsunptionValue() {
		return comsunptionValue;
	}
	public void setComsunptionValue(Double comsunptionValue) {
		this.comsunptionValue = comsunptionValue;
	}
	public Double getOtherConsumValue() {
		return otherConsumValue;
	}
	public void setOtherConsumValue(Double otherConsumValue) {
		this.otherConsumValue = otherConsumValue;
	}
	public List<MonthlyData> getMonthlyDataList() {
		return monthlyDataList;
	}
	public void setMonthlyDataList(List<MonthlyData> monthlyDataList) {
		this.monthlyDataList = monthlyDataList;
	}
	public String getOecode() {
		return oecode;
	}
	public void setOecode(String oecode) {
		this.oecode = oecode;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	
}
