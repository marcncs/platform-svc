package com.winsafe.notification.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Notification implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	//送货单号
	private String deliveryNo;
	//订单类型(S出库,T退货)
	private String deliveryOrderType;
	//送货公司
	private String logisticCompany;
	//送货出发地
	private String deliveryPlace;
	//送货时间
	private Date deliveryDate;
	private String deliveryDateStr;  //tommy 增加 ，导入时不防止 不同格式的日期 。
	//估计到达时间
	private Date estimateDate;
	private String estimateDateStr;//tommy 增加 ，导入时不防止 不同格式的日期 。
	//数量
	private String quantity;
	//箱数
	private Integer casesNo;
	//收货人代码
	private String shipToCode;
	//收货人的名称
	private String shipToCompany;
	//送货目的地
	private String shipToAddress;
	//联系人名
	private String consigneeName;
	//联系人手机
	private String consigneeMobile;

	//备份文件路径
	private String filePath;
	//文件名称
	private String fileName;
	//日志文件路径
	private String logFilePath;
	//短信ID
	private Long smsId;
	//验证码
	private String deliveryVerifyCode; 
	//URL
	private String url; 
	private String serviceCall; 
	
	private List<NotificationDetail> notificationDetails = new ArrayList<NotificationDetail>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDeliveryNo() {
		return deliveryNo;
	}
	public void setDeliveryNo(String deliveryNo) {
		this.deliveryNo = deliveryNo;
	}
	public String getDeliveryOrderType() {
		return deliveryOrderType;
	}
	public void setDeliveryOrderType(String deliveryOrderType) {
		this.deliveryOrderType = deliveryOrderType;
	}
	public String getLogisticCompany() {
		return logisticCompany;
	}
	public void setLogisticCompany(String logisticCompany) {
		this.logisticCompany = logisticCompany;
	}
	public String getDeliveryPlace() {
		return deliveryPlace;
	}
	public void setDeliveryPlace(String deliveryPlace) {
		this.deliveryPlace = deliveryPlace;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public Date getEstimateDate() {
		return estimateDate;
	}
	public void setEstimateDate(Date estimateDate) {
		this.estimateDate = estimateDate;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public Integer getCasesNo() {
		return casesNo;
	}
	public void setCasesNo(Integer casesNo) {
		this.casesNo = casesNo;
	}
	public String getShipToCode() {
		return shipToCode;
	}
	public void setShipToCode(String shipToCode) {
		this.shipToCode = shipToCode;
	}
	public String getShipToCompany() {
		return shipToCompany;
	}
	public void setShipToCompany(String shipToCompany) {
		this.shipToCompany = shipToCompany;
	}
	public String getShipToAddress() {
		return shipToAddress;
	}
	public void setShipToAddress(String shipToAddress) {
		this.shipToAddress = shipToAddress;
	}
	public String getConsigneeName() {
		return consigneeName;
	}
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	public String getConsigneeMobile() {
		return consigneeMobile;
	}
	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getLogFilePath() {
		return logFilePath;
	}
	public void setLogFilePath(String logFilePath) {
		this.logFilePath = logFilePath;
	}
	
	public Long getSmsId() {
		return smsId;
	}
	public void setSmsId(Long smsId) {
		this.smsId = smsId;
	}
	public List<NotificationDetail> getNotificationDetails() {
		return notificationDetails;
	}
	public void addNotificationDetails(NotificationDetail notificationDetail) throws Exception {
		notificationDetail.setDeliveryNo(deliveryNo);
		notificationDetails.add(notificationDetail);
	}
	public String getDeliveryDateStr() {
		return deliveryDateStr;
	}
	public void setDeliveryDateStr(String deliveryDateStr) {
		this.deliveryDateStr = deliveryDateStr;
	}
	public String getEstimateDateStr() {
		return estimateDateStr;
	}
	public void setEstimateDateStr(String estimateDateStr) {
		this.estimateDateStr = estimateDateStr;
	}
	public String getDeliveryVerifyCode() {
		return deliveryVerifyCode;
	}
	public void setDeliveryVerifyCode(String deliveryVerifyCode) {
		this.deliveryVerifyCode = deliveryVerifyCode;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getServiceCall() {
		return serviceCall;
	}
	public void setServiceCall(String serviceCall) {
		this.serviceCall = serviceCall;
	}
	
}
