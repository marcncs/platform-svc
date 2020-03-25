package com.winsafe.sap.pojo;

import java.io.Serializable;

public class CovertCodeBean  implements Serializable {
	//产品编号
	private String productId;
	// 物料名
	private String productName;
	// 生产时间
	private String printDate;
	// 批次号
	private String batchNumber;
	//小包装码
	private String primaryCode;
	//箱码
	private String cartonCode;
	//暗码
	private String covertCode;
	//产线
	private String productionLine;
	//二维码
	private String tdCode;
	//错误类型
	private Integer errorType;
	//上传日志编号
	private Integer uploadPrId;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getPrimaryCode() {
		return primaryCode;
	}
	public void setPrimaryCode(String primaryCode) {
		this.primaryCode = primaryCode;
	}
	public String getCartonCode() {
		return cartonCode;
	}
	public void setCartonCode(String cartonCode) {
		this.cartonCode = cartonCode;
	}
	public String getCovertCode() {
		return covertCode;
	}
	public void setCovertCode(String covertCode) {
		this.covertCode = covertCode;
	}
	public String getProductionLine() {
		return productionLine;
	}
	public void setProductionLine(String productionLine) {
		this.productionLine = productionLine;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPrintDate() {
		return printDate;
	}
	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}
	public String getTdCode() {
		return tdCode;
	}
	public void setTdCode(String tdCode) {
		this.tdCode = tdCode;
	}
	public Integer getErrorType() {
		return errorType;
	}
	public void setErrorType(Integer errorType) {
		this.errorType = errorType;
	}
	public Integer getUploadPrId() {
		return uploadPrId;
	}
	public void setUploadPrId(Integer uploadPrId) {
		this.uploadPrId = uploadPrId;
	}
	
}
