package com.winsafe.drp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppSuggestInspectDetail {

	public void addSuggestInspectDetail(SuggestInspectDetail sid)
			throws Exception {
		EntityManager.save(sid);
	}
	
	public List getSuggestInspectDetailByWhereSql(String whereSql) throws Exception{
		String sql="from SuggestInspectDetail sid "+whereSql;
		Session session = HibernateUtil.currentSession();
		Query query = session.createQuery(sql);
		return query.list();
	}
	
	public int getCountByWhereSql(String whereSql) throws Exception{
		String hql="select count(*) from SuggestInspectDetail sid "+ whereSql;
		return EntityManager.getCount(hql);
	}
	
	
	

	public void deleteBySiid(String siid) throws Exception {
		String sql = "delete from SUGGESTINSPECT_DETAIL where siid = '" + siid
				+ "'";
		EntityManager.updateOrdelete(sql);
	}

	public List getSuggestInspectDetailByPage(HttpServletRequest request,
			int pagesize, String pWhereClause) throws Exception {
		String hql = "from SuggestInspectDetail  " + pWhereClause
				+ " order by seqNumber asc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public void deleteSuggestInspectByids(String ids) throws Exception {
		String sql = "delete from SuggestInspect_Detail where siid in (select siid from suggestinspect where id in ("
				+ ids + "))";
		EntityManager.updateOrdelete(sql);
	}
	
	public void deleteAllMergeDetail() throws Exception{
		String sql="delete from suggestinspect_detail where siid in(select siid from suggestinspect where isout= 0 and typeid='2000001')";
		EntityManager.updateOrdelete(sql);
	}
	

	public void deleteSi(SuggestInspectDetail sid) throws Exception {
		EntityManager.delete(sid);
	}

	public void addMergeDetailBySiid(Long id, String siid) throws Exception {
		SuggestInspectDetail sid = null;
		String sql = "select product_id,max(product_name) product_name,max(PRODUCT_CODE) PRODUCT_CODE,max(UNIT) UNIT,sum(quantity) quantity, max(isgift) isgift,max(isout) isout from suggestinspect_detail where siid in(select siid from suggestinspect where mergeid='"+id+"') and isGift=0 group by product_id" +
				" union" +
				" select product_id,product_name,PRODUCT_CODE,UNIT,quantity,isgift,isout from suggestinspect_detail where siid in(select siid from suggestinspect where mergeid='"+id+"') and isGift=1";
		ResultSet rs = EntityManager.query2(sql);
		int i = 1;
		while(rs.next()){
			sid = new SuggestInspectDetail();
			sid.setSeqNumber(i);
			sid.setProductId(rs.getString("product_id"));
			sid.setSiid(siid);
			sid.setProductName(rs.getString("product_name"));
			sid.setProductCode(rs.getString("PRODUCT_CODE"));
			sid.setQuantity(rs.getInt("quantity"));
			sid.setUnit(rs.getString("UNIT"));
			sid.setIsGift(rs.getInt("isgift"));
			sid.setIsOut(rs.getInt("isout"));
			EntityManager.save(sid);
			i++;
		}
		rs.close();
	}
	
	public void updateSidIsOut(String siid) throws Exception{
		String sql="update SuggestInspect_detail set isOut = 1 where siid = '"+ siid+"'";
		EntityManager.updateOrdelete(sql);
	}

}
