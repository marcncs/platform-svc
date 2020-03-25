package com.winsafe.drp.dao;

public class InventoryUpload {
	private Long id;
	private String billNo;
	private String materialCode;
	private String cartonCode;
	private String batch;
	
	private String produceDate;
	private String expiryDate;
	private Integer falg;
	
	private String organId;
	private String warehouseId;
	private String oecode;
	private String nccode;
	private Integer inventoryUploadLogId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getCartonCode() {
		return cartonCode;
	}
	public void setCartonCode(String cartonCode) {
		this.cartonCode = cartonCode;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
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
	public Integer getFalg() {
		return falg;
	}
	public void setFalg(Integer falg) {
		this.falg = falg;
	}
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public String getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getOecode() {
		return oecode;
	}
	public void setOecode(String oecode) {
		this.oecode = oecode;
	}
	public String getNccode() {
		return nccode;
	}
	public void setNccode(String nccode) {
		this.nccode = nccode;
	}
	public Integer getInventoryUploadLogId() {
		return inventoryUploadLogId;
	}
	public void setInventoryUploadLogId(Integer inventoryUploadLogId) {
		this.inventoryUploadLogId = inventoryUploadLogId;
	}
	
}
