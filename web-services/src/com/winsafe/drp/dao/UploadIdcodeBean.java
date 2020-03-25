package com.winsafe.drp.dao;

/**
 * 上传条码bean
* @Title: UploadIdcodeBean.java
* @author: wenping 
* @CreateTime: Jan 10, 2013 8:48:27 AM
* @version:
 */
public class UploadIdcodeBean {
	
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
	// 产品单位
	private Integer pUnitId;
	// 产品总数量
	private Double sum;
	
	private String fromOrganId;
	private String toOrganId;
	private String userId;
	private String productId;
	/**
	 * 扫描类型
	 */
	private String scanDate;
	
	private String deliveryType; 
	
	//外部mpin码(用于兼容旧码)
	private String outMpinCode;
	
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
	public Integer getpUnitId() {
		return pUnitId;
	}
	public void setpUnitId(Integer pUnitId) {
		this.pUnitId = pUnitId;
	}
	public Double getSum() {
		return sum ;
	}
	public void setSum(Double sum) {
		this.sum = sum;
	}
	public String getOutMpinCode() {
		return outMpinCode;
	}
	public void setOutMpinCode(String outMpinCode) {
		this.outMpinCode = outMpinCode;
	}
	public String getFromOrganId() {
		return fromOrganId;
	}
	public void setFromOrganId(String fromOrganId) {
		this.fromOrganId = fromOrganId;
	}
	public String getToOrganId() {
		return toOrganId;
	}
	public void setToOrganId(String toOrganId) {
		this.toOrganId = toOrganId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getScanDate() {
		return scanDate;
	}
	public void setScanDate(String scanDate) {
		this.scanDate = scanDate;
	}
	public String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	
}
