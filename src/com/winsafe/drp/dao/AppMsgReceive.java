package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.sms.pojo.Sms;

public class AppMsgReceive {
	
	public List getMsgReceiveByWhere(String whereHql) {
		return EntityManager.getAllByHql(" from MsgReceive " + whereHql);
	}

	public void updMsgReceive(MsgReceive MsgReceive)  throws Exception{
		EntityManager.saveOrUpdate(MsgReceive);
	}

	public void addMsgReceive(Object o) {
		EntityManager.save(o);
		
	}

	public MsgReceive getMsgReceiveById(String id) {
		return (MsgReceive)EntityManager.find("from MsgReceive where id="+id.trim());
	}

	public List getMsgReceive(HttpServletRequest request, int pagesize, String whereSql) throws Exception {
		String hql=" from MsgReceive as o "+whereSql +" order by o.id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
}

