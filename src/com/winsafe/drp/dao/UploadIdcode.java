package com.winsafe.drp.dao;

import java.util.Date;


public class UploadIdcode {
	
	/**
	 * 上传属性
	 */
	//单据号
	private String billNo;
	//出库仓库编号
	private String inWarehouseId;
	//生产日期
	private String proDate;
	//批次
	private String batch;
	//产品编号
	private String productId;
	//扫描标志位
	private String scanFlag;
	//条码
	private String idcode;
	//扫描类型
	private String scanType;
	//扫描日期
	private Date scanDate;
	//数量
	private Double quantity;
	//采集器编号
	private String scannerNo;
	//发货仓库编号
	private String outWarehouseId;
	//用户名
	private String username;
	
	//最小包装数量
	private Double packQuantity;
	
	//外部mpin码(用于兼容旧码)
	private String outMpinCode;
	
	
	
	/**
	 * 附加属性
	 */
	private String lcode;
	private Integer unitid;
	
	
	
	
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getInWarehouseId() {
		return inWarehouseId;
	}
	public void setInWarehouseId(String inWarehouseId) {
		this.inWarehouseId = inWarehouseId;
	}
	public String getProDate() {
		return proDate;
	}
	public void setProDate(String proDate) {
		this.proDate = proDate;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getScanFlag() {
		return scanFlag;
	}
	public void setScanFlag(String scanFlag) {
		this.scanFlag = scanFlag;
	}
	public String getIdcode() {
		return idcode;
	}
	public void setIdcode(String idcode) {
		this.idcode = idcode;
	}
	public String getScanType() {
		return scanType;
	}
	public void setScanType(String scanType) {
		this.scanType = scanType;
	}
	public Date getScanDate() {
		return scanDate;
	}
	public void setScanDate(Date scanDate) {
		this.scanDate = scanDate;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public String getScannerNo() {
		return scannerNo;
	}
	public void setScannerNo(String scannerNo) {
		this.scannerNo = scannerNo;
	}
	public String getOutWarehouseId() {
		return outWarehouseId;
	}
	public void setOutWarehouseId(String outWarehouseId) {
		this.outWarehouseId = outWarehouseId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLcode() {
		return lcode;
	}
	public void setLcode(String lcode) {
		this.lcode = lcode;
	}
	public Integer getUnitid() {
		return unitid;
	}
	public void setUnitid(Integer unitid) {
		this.unitid = unitid;
	}
	public Double getPackQuantity() {
		return packQuantity;
	}
	public void setPackQuantity(Double packQuantity) {
		this.packQuantity = packQuantity;
	}
	public String getOutMpinCode() {
		return outMpinCode;
	}
	public void setOutMpinCode(String outMpinCode) {
		this.outMpinCode = outMpinCode;
	}
}
