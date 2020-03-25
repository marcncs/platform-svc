package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppHap {

	public List getHap(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List list = null;
		String sql = "select h.id,h.cid,h.hapcontent,h.hapkind,h.hapstatus,h.intend,h.hapbegin,h.hapend,h.memo,h.makeid,h.makedate from Hap as h "
				+ pWhereClause + " order by h.id desc";

		list = EntityManager.getAllByHql(sql, pPgInfo.getCurrentPageNo(),
				pagesize);
		return list;
	}
	
	public List searchHap(HttpServletRequest request, int pagesize,
			String pWhereClause)throws Exception{
		String hql = "select h.id,h.objsort,h.cname,h.hapcontent,h.hapkind,h.hapstatus,h.intend," +
				"h.hapbegin,h.hapend,h.makeorganid,h.makeid,h.makedate from Hap as h "
			+ pWhereClause + " order by h.id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public void addHap(Hap r) throws Exception {
		
		EntityManager.save(r);
		
	}
	
	public void delHap(Integer id)throws Exception{
		
		String sql="delete from Hap where id="+id;
		EntityManager.updateOrdelete(sql);
		
	}
	
	public Hap getHapByID(Integer id)throws Exception{

		String sql=" from Hap as h where h.id="+id;
		return (Hap) EntityManager.find(sql);
	}
	
	public void updHap(Hap h)throws Exception{
		
		EntityManager.update(h);
		
	}
}
