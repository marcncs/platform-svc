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
public class AppSupplySaleMoveDetail {

	
	public List<SupplySaleMoveDetail> getSupplySaleApplyAll(HttpServletRequest request,Integer pageSize, String whereSql)throws Exception{
		String hql =" from SupplySaleMoveDetail as ssa" +whereSql+"order by ssa.id desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}
	
	public SupplySaleMoveDetail getSupplySaleMoveDetailByID(int id)throws Exception{
		String hql = " from SupplySaleMoveDetail ssa where ssa.id=" + id;
		return (SupplySaleMoveDetail) EntityManager.find(hql);
	}
	public void save(SupplySaleMoveDetail supplySaleMoveDetail)throws Exception{
		EntityManager.save(supplySaleMoveDetail);
	}
	public void update(SupplySaleMoveDetail supplySaleMoveDetail)throws Exception{
		EntityManager.update(supplySaleMoveDetail);
	}
	
	public void deleteBySSMID(String ssmid)throws Exception{
		String hql ="delete from Supply_Sale_Move_Detail  where ssmid ='"+ssmid+"'";
		EntityManager.updateOrdelete(hql);
	}
	public void delete(SupplySaleMoveDetail supplySaleMoveDetail)throws Exception{
		EntityManager.delete(supplySaleMoveDetail);
	}

	public List<SupplySaleMoveDetail> getSupplySaleMoveBySSMID(String id) {
		String hql = " from SupplySaleMoveDetail ssa  where ssa.ssmid='" + id+"'";
		return EntityManager.getAllByHql(hql);
	}
	
	public List getSupplySaleMoveDetail(String pWhereClause) throws Exception {
		String sql = "select ssa, ssad from SupplySaleMove as ssa ,SupplySaleMoveDetail as ssad,Product as p "
				+ pWhereClause + " order by ssad.productid, ssa.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getSupplySaleMoveDetailByPiidPid(String piid, String productid)
	throws Exception {
		String sql = " from SupplySaleMoveDetail where ssmid='" + piid
				+ "' and productid='" + productid + "'";
		return EntityManager.getAllByHql(sql);
	}
	
}
