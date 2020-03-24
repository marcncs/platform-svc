package com.winsafe.notification.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.notification.pojo.Notification;
import com.winsafe.notification.pojo.NotificationLog;

public class AppNotification {
	
	public void addNotification(Notification d) throws Exception {		
		EntityManager.save(d);		
	}

	public void updNotification(Notification notification)  throws HibernateException{
		EntityManager.saveOrUpdate(notification);
		
	}

	public List getNotifications(HttpServletRequest request, int pagesize,
			String whereSql) throws Exception {
		String hql=" from Notification as n "+whereSql +" order by n.id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public Notification getNotificationById(String id) {
		return (Notification)EntityManager.find("from Notification where id="+id);
	}

	public NotificationLog getNotificationLog(String deliveryNo) {
		return (NotificationLog)EntityManager.find("from NotificationLog where deliveryNo='"+deliveryNo+"'");
	}
	
	public void addNotificationLog(NotificationLog log) {
		EntityManager.save(log);
	}

	public NotificationLog getNotificationLog(String deliveryNo, String verifyCode) {
		return (NotificationLog)EntityManager.find("from NotificationLog where deliveryNo='"+deliveryNo+"' and code='"+verifyCode+"'");
	}
	
	public void delNotificationLog(String deliveryNo) throws Exception {
		String sql = "delete from NOTIFICATION_LOG where DELIVERYNO='"+deliveryNo+"'";
		EntityManager.executeUpdate(sql);
	}
}
