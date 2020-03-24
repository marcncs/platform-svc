package com.winsafe.drp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockAlterMoveIdcode;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.notification.dao.AppNotification;
import com.winsafe.notification.pojo.NotificationLog;

public class NotificationService {
	
	private AppNotification appNotification = new AppNotification();
	private AppStockAlterMove appStockAlterMove = new AppStockAlterMove();
	private AppStockAlterMoveIdcode appSami = new AppStockAlterMoveIdcode();

	public void getDeliveryInfo(String deliveryNo, String verifyCode, HttpServletRequest request) throws Exception {
		NotificationLog log = appNotification.getNotificationLog(deliveryNo, verifyCode);
		if(log == null) {
			request.setAttribute("isExpire", true);
			return;
		} else {
			request.setAttribute("isExpire", false);
		}
		Map<String,Object> data = new HashMap<>();
		StockAlterMove sam = appStockAlterMove.getStockAlterMoveByNCcode("0"+deliveryNo);
		if(sam==null) {
			request.setAttribute("isExpire", true);
			return;
		}
		data.put("sapNo", deliveryNo);
		data.put("billNo", sam.getId());
		data.put("customer", sam.getReceiveorganidname());
		List<Map<String,String>> samdList = appSami.getDeliveryDetailInfo(sam.getId());
		//data.put("details", samdList);
		request.setAttribute("data", data);
		request.setAttribute("details", samdList);
	}
}
