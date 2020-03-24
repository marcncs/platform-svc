package com.winsafe.notification.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.notification.pojo.NotificationDetail;

public class AppNotificationDetail {
	
	public void addNotificationDetail(NotificationDetail d) throws Exception {		
		EntityManager.save(d);		
	}
	
	public void addNotificationDetails(List<NotificationDetail> list) throws Exception {		
		EntityManager.batchSave(list);	
	}

	public List getNDetailsByDeliveryNo(String deliveryNo) {
		return EntityManager.getAllByHql("from NotificationDetail where deliveryNo="+deliveryNo);
	}

}
