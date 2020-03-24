package com.winsafe.sms.dao;

import java.util.List;
import java.util.Map; 

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.sms.pojo.Sms;

public class AppSms {
	
	public List getSmsByWhere(String whereHql) {
		return EntityManager.getAllByHql(" from Sms " + whereHql);
	}

	public void updSms(Sms sms)  throws Exception{
		EntityManager.saveOrUpdate(sms);
	}

	public void addSms(Sms sms) {
		EntityManager.save(sms);
		
	}

	public Sms getSmsById(String id) {
		return (Sms)EntityManager.find("from Sms where id="+id.trim());
	}

	public List getSms(HttpServletRequest request, int pagesize, String whereSql) throws Exception {
		String hql=" from Sms as o "+whereSql +" order by o.id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List getSms(HttpServletRequest request, int pagesize, String whereSql, Map<String,Object> param) throws Exception {
		String hql=" from Sms as o "+whereSql +" order by o.id desc";
		return PageQuery.hbmQuery(request, hql, pagesize, param);
	}
	
	@SuppressWarnings("unchecked")
	public List<Sms> getExputSms(String whereSql) throws Exception {
		String hql =" from Sms as o "+whereSql +" order by o.id desc";
		return EntityManager.getAllByHql(hql);
	}
}
