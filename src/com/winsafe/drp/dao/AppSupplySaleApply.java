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
public class AppSupplySaleApply {

	
	@SuppressWarnings("unchecked")
	public List<SupplySaleApply> getSupplySaleApplyAll(HttpServletRequest request,Integer pageSize, String whereSql)throws Exception{
		String hql =" from SupplySaleApply as ssa " +whereSql+" order by ssa.id desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}
	
	public SupplySaleApply getSupplySaleApplyByID(String id)throws Exception{
		String hql = " from SupplySaleApply ssa where ssa.id='" + id+"'";
		return (SupplySaleApply) EntityManager.find(hql);
	}
	public Integer getIsTrans(String makeorganid)throws Exception{
		String hql = "select count(*) from SupplySaleApply as am where am.isratify=1 and am.istrans=0 and am.outorganid='"+makeorganid+"'";
		return EntityManager.getRecordCount(hql);
	}
	public void save(SupplySaleApply supplySaleApply)throws Exception{
		EntityManager.save(supplySaleApply);
	}
	public void update(SupplySaleApply supplySaleApply)throws Exception{
		EntityManager.update(supplySaleApply);
	}
	public void delete(SupplySaleApply supplySaleApply)throws Exception{
		EntityManager.delete(supplySaleApply);
	}
	
	public void IsTrans(String id,int stats)throws Exception{
		String sql = "update Supply_Sale_Apply set istrans="+stats+" where id= '" + id+"'";
		EntityManager.updateOrdelete(sql);
	}

	@SuppressWarnings("unchecked")
	public List<SupplySaleApply> getSupplySaleApplyAll(String whereSql) {
		String hql =" from SupplySaleApply as ssa " +whereSql+" order by ssa.id desc";
		return EntityManager.getAllByHql(hql);
	}
	
}
