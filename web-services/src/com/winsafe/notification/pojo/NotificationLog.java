package com.winsafe.notification.pojo;

import java.io.Serializable;
import java.util.Date;

public class NotificationLog implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//送货单号
	private String deliveryNo;
	//编号
	private String code;
	//制单日期
	private Date makeDate;
	public String getDeliveryNo() {
		return deliveryNo;
	}
	public void setDeliveryNo(String deliveryNo) {
		this.deliveryNo = deliveryNo;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}
}
