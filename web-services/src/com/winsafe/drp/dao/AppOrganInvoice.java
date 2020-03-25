package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

/**
 * @author : jerry
 * @version : 2009-8-26 下午03:49:02
 * www.winsafe.cn
 */
public class AppOrganInvoice {

	
	public List<OrganInvoice> getOrganInvoiceAll(HttpServletRequest request,Integer pageSize, String whereSql)throws Exception{
		String hql =" from OrganInvoice as oi " +whereSql+" order by oi.id desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}
	
	public List<OrganInvoice> getOrganInvoiceAll(String whereSql)throws Exception{
		String hql =" from OrganInvoice as oi " +whereSql+" order by oi.id desc";
		return EntityManager.getAllByHql(hql);
	}
	
	public OrganInvoice getOrganInvoiceByID(Integer id)throws Exception{
		String hql = " from OrganInvoice oi where oi.id=" + id;
		return (OrganInvoice) EntityManager.find(hql);
	}
	
	public int IsExistInvoiceCode(String code)throws Exception{
		String hql =" from OrganInvoice oi where oi.invoicecode = '"+code+"'";
		return EntityManager.getRecordCount(hql);
	}
	public void save(OrganInvoice organInvoice)throws Exception{
		EntityManager.save(organInvoice);
	}
	public void update(OrganInvoice organInvoice)throws Exception{
		EntityManager.update(organInvoice);
	}
	public void delete(OrganInvoice organInvoice)throws Exception{
		EntityManager.delete(organInvoice);
	}
	
}
