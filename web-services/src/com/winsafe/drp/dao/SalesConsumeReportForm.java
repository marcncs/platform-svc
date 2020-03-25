package com.winsafe.drp.dao;

import java.io.Serializable;
import java.util.Date;

public class SalesConsumeReportForm implements Serializable{
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
	// 销售数量
	private Double salesQuantity;
	// 退回工厂数量
	private Double pwQuantity;
	// 消耗数量
	private Double consumeQuantity;
	// 下级经销商退货数量
	private Double owQuantity;
	
	private String beginDate;
	private String endDate;
	private String historyDate;
	//机构内部编码
	private String oecode;
	//产品规格  
	private String packSizeName;
	//是否按计量单位统计
	private String countByUnit;
	
	//产品英文
	private String productNameen;
	//规格英文
	private String packSizeNameEn;
	
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
	public Double getPwQuantity() {
		return pwQuantity;
	}
	public void setPwQuantity(Double pwQuantity) {
		this.pwQuantity = pwQuantity;
	}
	public Double getConsumeQuantity() {
		return consumeQuantity;
	}
	public void setConsumeQuantity(Double consumeQuantity) {
		this.consumeQuantity = consumeQuantity;
	}
	public Double getOwQuantity() {
		return owQuantity;
	}
	public void setOwQuantity(Double owQuantity) {
		this.owQuantity = owQuantity;
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
	public String getCountByUnit() {
		return countByUnit;
	}
	public void setCountByUnit(String countByUnit) {
		this.countByUnit = countByUnit;
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
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getHistoryDate() {
		return historyDate;
	}
	public void setHistoryDate(String historyDate) {
		this.historyDate = historyDate;
	}
	
	
}
