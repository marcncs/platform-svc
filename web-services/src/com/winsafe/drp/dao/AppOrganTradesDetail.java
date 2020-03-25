package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

/**
 * @author : jerry
 * @version : 2009-09-03 上午09:27:02
 * www.winsafe.cn
 */
public class AppOrganTradesDetail {

	
	public List<OrganTradesDetail> getOrganTradesDetailAll(HttpServletRequest request,Integer pageSize, String whereSql)throws Exception{
		String hql =" from OrganTradesDetail as otd " +whereSql+" order by otd.id desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}
	
	public OrganTradesDetail getOrganTradesDetailByID(int id)throws Exception{
		String hql = " from OrganTradesDetail otd where otd.id=" + id;
		return (OrganTradesDetail) EntityManager.find(hql);
	}
	
	public void save(OrganTradesDetail otd)throws Exception{
		EntityManager.save(otd);
	}
	
	public void update(OrganTradesDetail otd)throws Exception{
		EntityManager.update(otd);
	}
	public void deleteByPIID(String otid)throws Exception{
		String hql = "delete from Organ_Trades_Detail where otid = '"+otid+"'";
		EntityManager.updateOrdelete(hql);
	}
	public void delete(OrganTradesDetail otd)throws Exception{
		EntityManager.delete(otd);
	}
	public List<OrganTradesDetail> getOrganTradesDetailByotid(String otid) {
		String hql = " from OrganTradesDetail otd  where otd.otid='" + otid+"'";
		return EntityManager.getAllByHql(hql);
	}
	
	public void updCanQuantity(String otid) throws Exception {
		String sql = "update  Organ_Trades_Detail set canquantity=0  where otid='" + otid+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	public List getOrganTradesDetail(String pWhereClause) throws Exception {
		String sql = "select ot, otd from OrganTrades as ot ,OrganTradesDetail as otd,Product as p "
				+ pWhereClause + " order by otd.productid, ot.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getOrganTradesDetailByPiidPid(String piid, String batch, String productid)
	throws Exception {
		String sql = " from OrganTradesDetail where otid='" + piid
				+ "' and batch='"+batch+"' and productid='" + productid + "'";
		return EntityManager.getAllByHql(sql);
	}
}
