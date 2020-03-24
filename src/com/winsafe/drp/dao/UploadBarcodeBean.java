package com.winsafe.drp.dao;

public class UploadBarcodeBean {
	
	/**
	 * 条码
	 */
	private String idcode;
	/**
	 * 扫描类型
	 */
	private String scanType;
	/**
	 * 数量
	 */
	private int quantity;
	
	/**
	 * 入库仓库
	 */
	private String inwarehouseid;
	
	/**
	 * 产品物流码前缀
	 */
	private String lcode;
	
	/**
	 * 扫描标志位
	 */
	private String scanFlag;
	
	/**
	 * 文件行号
	 */
	private int fileLineNo;
	
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 出库仓库
	 */
	private String outwarehouseid;
	
	/**
	 * 单据号 
	 */
	private String billNo;
	
	/**
	 * 批次 
	 */
	private String batch;
	
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
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getInwarehouseid() {
		return inwarehouseid;
	}
	public void setInwarehouseid(String inwarehouseid) {
		this.inwarehouseid = inwarehouseid;
	}
	public String getLcode() {
		return lcode;
	}
	public void setLcode(String lcode) {
		this.lcode = lcode;
	}
	public String getScanFlag() {
		return scanFlag;
	}
	public void setScanFlag(String scanFlag) {
		this.scanFlag = scanFlag;
	}
	public int getFileLineNo() {
		return fileLineNo;
	}
	public void setFileLineNo(int fileLineNo) {
		this.fileLineNo = fileLineNo;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOutwarehouseid() {
		return outwarehouseid;
	}
	public void setOutwarehouseid(String outwarehouseid) {
		this.outwarehouseid = outwarehouseid;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getBatch() {
		return batch;
	}
	
	

}
