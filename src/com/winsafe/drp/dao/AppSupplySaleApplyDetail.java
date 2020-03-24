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
public class AppSupplySaleApplyDetail {

	
	public List<SupplySaleApplyDetail> getSupplySaleApplyAll(HttpServletRequest request,Integer pageSize, String whereSql)throws Exception{
		String hql =" from SupplySaleApplyDetail as ssa" +whereSql+"order by ssa.id desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}
	
	public SupplySaleApplyDetail getSupplySaleApplyDetailByID(String id)throws Exception{
		String hql = " from SupplySaleApplyDetail ssa where ssa.id='" + id+"'";
		return (SupplySaleApplyDetail) EntityManager.find(hql);
	}
	
	public List<SupplySaleApplyDetail> getSupplySaleAplyBySSID(String ssid)throws Exception{
		String hql = " from SupplySaleApplyDetail ssa where ssa.ssid='" + ssid+"'";
		return EntityManager.getAllByHql(hql);
	}
	
	public List<SupplySaleApplyDetail> getSupplySaleAplyNoTransBySSID(String ssid)throws Exception{
		String hql = " from SupplySaleApplyDetail ssa where ssa.canquantity!=ssa.alreadyquantity and ssa.ssid='" + ssid+"'";
		return EntityManager.getAllByHql(hql);
	}
	
	public void save(SupplySaleApplyDetail supplySaleApplyDetail)throws Exception{
		EntityManager.save(supplySaleApplyDetail);
	}
	public void update(SupplySaleApplyDetail supplySaleApplyDetail)throws Exception{
		EntityManager.update(supplySaleApplyDetail);
	}
	
	public void deleteBySSID(String ssid)throws Exception{
		String hql ="delete from Supply_Sale_Apply_Detail where ssid ='"+ssid+"'";
		EntityManager.updateOrdelete(hql);
	}
	public void delete(SupplySaleApplyDetail supplySaleApplyDetail)throws Exception{
		EntityManager.delete(supplySaleApplyDetail);
	}
	
	
	public void updAlreadyQuantity(Integer id, Double quantity) throws Exception {

		String sql = "update  Supply_Sale_Apply_Detail set alreadyquantity=alreadyquantity+"
				+ quantity + " where id=" + id;
		EntityManager.updateOrdelete(sql);

	}
	
}
