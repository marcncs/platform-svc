package com.winsafe.mail.dao;

import java.util.List;


import com.winsafe.drp.entity.EntityManager;
import com.winsafe.mail.pojo.BatchCompleteMail;
  

public class AppBatchCompleteMail {

	public List getMailList() {
		String hql = "from BatchCompleteMail as mail where mail.isSent = 0 and mail.wrongTimes < 3";
		return EntityManager.getAllByHql(hql);
	}
	public void update(BatchCompleteMail mail) throws Exception {
		EntityManager.update(mail);
	}
	
	public BatchCompleteMail getMailByDateType(String date_type) {
		String hql = "from BatchCompleteMail as mail where mail.mailDateType = '"+date_type+"' and mail.isSent = 0";
		return (BatchCompleteMail)EntityManager.find(hql);
		
	}
	
	public void add(BatchCompleteMail mail) {
		EntityManager.save(mail);
	}
}
