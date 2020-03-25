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
public class CopyOfAppSupplySaleDetailApply {

	
	public List<SupplySaleApply> getSupplySaleApplyAll(HttpServletRequest request,Integer pageSize, String whereSql)throws Exception{
		String hql =" from SupplySaleApply as ssa" +whereSql+"order by ssa.id desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}
	
	public SupplySaleApply getSupplySaleApplyByID(String id)throws Exception{
		String hql = " from SupplySaleApply ssa sm where ssa.id='" + id+"'";
		return (SupplySaleApply) EntityManager.find(hql);
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
	
}
