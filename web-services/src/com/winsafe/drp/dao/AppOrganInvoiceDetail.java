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
public class AppOrganInvoiceDetail {

	
	public List<OrganInvoiceDetail> getOrganInvoiceDetailAll(HttpServletRequest request,Integer pageSize, String whereSql)throws Exception{
		String hql =" from OrganInvoiceDetail as oid " +whereSql+" order by oi.id desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}
	
	public OrganInvoiceDetail getOrganInvoiceDetailByID(String id)throws Exception{
		String hql = " from OrganInvoiceDetail oid where oid.id='" + id+"'";
		return (OrganInvoiceDetail) EntityManager.find(hql);
	}
	public void save(OrganInvoiceDetail organInvoice)throws Exception{
		EntityManager.save(organInvoice);
	}
	
	public void deleteByPIID(Integer piid)throws Exception{
		String hql = "delete from Organ_Invoice_Detail where piid = "+piid;
		EntityManager.updateOrdelete(hql);
	}
	public void delete(OrganInvoiceDetail organInvoice)throws Exception{
		EntityManager.delete(organInvoice);
	}
	public List<OrganInvoiceDetail> getOrganInvoiceDetailByPIID(String piid) {
		String hql = " from OrganInvoiceDetail oid  where oid.piid='" + piid+"'";
		return EntityManager.getAllByHql(hql);
	}
	
}
