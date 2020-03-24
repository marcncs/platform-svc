package com.winsafe.drp.dao;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppSuggestInspect {
	
	
	public List getSuggestInspectByPage(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String hql = "from SuggestInspect  " + pWhereClause+ " order by makeDate desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	
	public void addSuggestInspect(SuggestInspect si) throws Exception{
		EntityManager.save(si);
	}
	
	public void updateSi(SuggestInspect si) throws Exception{
		EntityManager.update(si);
	}
	
	public void deleteSi(SuggestInspect si) throws Exception{
		EntityManager.delete(si);
	}
	
	public SuggestInspect getSiBySiid(String siid) throws Exception{
		String hql="from SuggestInspect  where siid = '" + siid+"'";
		return (SuggestInspect) EntityManager.find(hql);
	}
	
	public void updateSuggestInspect(SuggestInspect si) throws Exception{
		EntityManager.update(si);
	}
	
	public void deleteSuggestInspectByIds(String ids) throws Exception{
		String sql="delete from suggestinspect where id in ("+ids+")";
		EntityManager.updateOrdelete(sql);
	}
	
	public void removeSuggestInspectByIds(String ids) throws Exception{
		String sql="update suggestinspect set isremove = 1 where id in ("+ids+")";
		EntityManager.updateOrdelete(sql);
	}
	
	public List getSiByIds(String whereSql) throws Exception {
		String sql="from SuggestInspect si " + whereSql + " order by id asc";
		Session session = HibernateUtil.currentSession();
		Query query = session.createQuery(sql);
		return query.list();
		
	}
	
	
	public SuggestInspect getSiByOther(String ccode, String diswh, Date makedate) throws Exception{
		String makeDate = DateUtil.formatDate(makedate, "yyyy/MM/dd");
		String hql="from SuggestInspect si where typeid='2000001' and  si.disWareHouseName = '"+diswh+"' and trunc(si.makeDate) = to_date('"+makeDate+"','yyyy/mm/dd') and si.id =(select max(id) from SuggestInspect) ";
		if(StringUtil.isEmpty(ccode)){
			hql += " and si.customerCode is null";
		}else{
			hql += " and si.customerCode = '"+ccode+"'";
		}
		return (SuggestInspect) EntityManager.find(hql);
		
	}
	
	public void updateSiByMerge(Long id, Long mergeid) throws Exception{
		String sql="update SuggestInspect set ismerge=1 , mergeid = "+mergeid+" where id = "+id;
		EntityManager.updateOrdelete(sql);
	}
	
	
	public void deleteAllMergeSi() throws Exception{
		String sql="delete from suggestinspect where typeid='2000001' and isOut = 0";
		EntityManager.updateOrdelete(sql);
	}
	
	
	public void updateSiByUnMerge(String andid) throws Exception{
		String sql="update SuggestInspect set ismerge=0, mergeid='' where isOut = 0 "+ andid;
		EntityManager.updateOrdelete(sql);
	}
	
	public void updateSiIsOut(Long id, Integer userid, String date) throws Exception{
		String sql="update SuggestInspect set isOut = 1 , outUserId = "+userid+" , outDate = to_date('"+date+"','yyyy/mm/dd hh24:mi:ss') where id = "+ id+" or mergeId = '"+id+"' ";
		EntityManager.updateOrdelete(sql);
	}
	
	
	
	
	
	
	
	
	
}
