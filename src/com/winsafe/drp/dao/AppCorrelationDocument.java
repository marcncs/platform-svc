package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppCorrelationDocument {

	public List searchCorrelationDocument(HttpServletRequest request,
			int pagesize, String pWhereClause) throws Exception {
		String hql = "select c.id,c.objsort,c.cid,c.cname,c.documentname,c.realpathname,c.makeorganid,c.makedate,c.makeid ,c.description from CorrelationDocument as c  "
				+ pWhereClause + " order by c.makedate desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public void addCorrelationDocument(CorrelationDocument cd) throws Exception {
		EntityManager.save(cd);
	}

	public void Del(CorrelationDocument cd) {
		EntityManager.delete(cd);
	}

	public CorrelationDocument getCorrelationDocument(Integer id) {
		String sql = " from  CorrelationDocument as c where id = " + id;
		return (CorrelationDocument) EntityManager.find(sql);
	}
}
