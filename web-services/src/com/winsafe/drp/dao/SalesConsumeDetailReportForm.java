package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

public class SalesConsumeDetailReportForm implements Serializable{
	/** */
	private static final long serialVersionUID = 1L;
	// 月份
	private String yearMonth; 
	// 大区
	private String region; 
	private String regionName;
	// 省份
	private String province;
	// 市
	private String city;
	// 区 
	private String areas;
	// 机构代码
	private String organId;
	// 机构名称
	private String organName;
	// 仓库代码
	private String warehouseId;
	private String warehouseName;
	// 产品类别
	private String psName;
	// 产品id
	private String productId;
	// 产品名称
	private String productName;
	// 物料号
	private String mCode;
	// 物料中文
	private String matericalChDes;
	// 物料英文
	private String matericalEnDes;
	// 报表日期
	private Date reportDate;
	// 单位
	private Integer unitId;
	private String unitName;
	// 单据号
	private String billNo;
	// 批次
	private String batch;
	// 销售数量
	private Double salesQuantity;
	// 消耗数量
	private Double consumeQuantity;
	// 消耗数量(件)
	private Double consumeCount;
	// 扫码数量
	private Double scanedQuantity;
	// 扫码数量(件)
	private Double scanedCount;
	// 单据日期
	private Date makeDate;
	// 生产日期
	private String produceDate;
	// 过期日期
	private String expiryDate;
	
	private String beginDate;
	private String endDate;
	private String historyDate;
	//机构内部编码
	private String oecode;
	//规格
	private String packSizeName;
	//单据内部编码
	private String nccode;
	//入货机构名称
	private String outOrganName;
	//是否按计量单位统计
	private String countByUnit;
	//机构类型
	private Integer organType;
	private Integer organModel;
	//产品英文
	private String productNameen;
	//规格英文
	private String packSizeNameEn;
	// 省份Code
	private String provinceId;
	// 市Code
	private String cityId;
	// 区 Code
	private String areasId;
	
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
	public String getPsName() {
		return psName;
	}
	public void setPsName(String psName) {
		this.psName = psName;
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
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public Double getSalesQuantity() {
		return salesQuantity;
	}
	public void setSalesQuantity(Double salesQuantity) {
		this.salesQuantity = salesQuantity;
	}
	public Double getConsumeQuantity() {
		return consumeQuantity;
	}
	public void setConsumeQuantity(Double consumeQuantity) {
		this.consumeQuantity = consumeQuantity;
	}
	public Integer getUnitId() {
		return unitId;
	}
	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
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
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public Date getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getProduceDate() {
		return produceDate;
	}
	public void setProduceDate(String produceDate) {
		this.produceDate = produceDate;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
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
	public String getOecode() {
		return oecode;
	}
	public void setOecode(String oecode) {
		this.oecode = oecode;
	}
	public String getPackSizeName() {
		return packSizeName;
	}
	public void setPackSizeName(String packSizeName) {
		this.packSizeName = packSizeName;
	}
	public String getNccode() {
		return nccode;
	}
	public void setNccode(String nccode) {
		this.nccode = nccode;
	}
	public String getOutOrganName() {
		return outOrganName;
	}
	public void setOutOrganName(String outOrganName) {
		this.outOrganName = outOrganName;
	}
	public String getCountByUnit() {
		return countByUnit;
	}
	public void setCountByUnit(String countByUnit) {
		this.countByUnit = countByUnit;
	}
	public Integer getOrganType() {
		return organType;
	}
	public void setOrganType(Integer organType) {
		this.organType = organType;
	}
	public String getPackSizeNameEn() {
		return packSizeNameEn;
	}
	public void setPackSizeNameEn(String packSizeNameEn) {
		this.packSizeNameEn = packSizeNameEn;
	}
	public String getProductNameen() {
		return productNameen;
	}
	public void setProductNameen(String productNameen) {
		this.productNameen = productNameen;
	}
	public String getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
	public String getHistoryDate() {
		return historyDate;
	}
	public void setHistoryDate(String historyDate) {
		this.historyDate = historyDate;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAreas() {
		return areas;
	}
	public void setAreas(String areas) {
		this.areas = areas;
	}
	public Integer getOrganModel() {
		return organModel; 
	}
	public void setOrganModel(Integer organModel) {
		this.organModel = organModel;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getAreasId() {
		return areasId;
	}
	public void setAreasId(String areasId) {
		this.areasId = areasId;
	}
	public Double getConsumeCount() {
		return consumeCount;
	}
	public void setConsumeCount(Double consumeCount) {
		this.consumeCount = consumeCount;
	}
	public Double getScanedQuantity() {
		return scanedQuantity;
	}
	public void setScanedQuantity(Double scanedQuantity) {
		this.scanedQuantity = scanedQuantity;
	}
	public Double getScanedCount() {
		return scanedCount;
	}
	public void setScanedCount(Double scanedCount) {
		this.scanedCount = scanedCount;
	}
	
}
