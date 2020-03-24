package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppContactLog {
	public List searchContactLog(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List list = null;
		String sql = "select c.id,c.cid,c.contactdate,c.contactmode,c.contactproperty,"
				+ "c.contactcontent,c.feedback,c.linkman,c.nextcontact,c.makeid "
				+ "from ContactLog as c  "
				+ pWhereClause
				+ " order by c.id desc";
		list = EntityManager.getAllByHql(sql, pPgInfo.getCurrentPageNo(),
				pagesize);
		return list;
	}

	public List searchContactLog(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String hql = "select c.id,c.objsort,c.cname,c.contactdate,c.contactmode,c.contactproperty,"
				+ "c.linkman,c.nextcontact,c.makeorganid,c.makeid "
				+ "from ContactLog as c  "
				+ pWhereClause
				+ " order by c.id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public List initContactLog(int pagesize, String pWhereClause)
			throws Exception {
		List list = null;
		String sql = "select c.id,c.cid,c.contactdate,c.contactmode,c.contactproperty,c.contactcontent,"
				+ "c.feedback,c.linkmanid,c.nextcontact,c.makeid from ContactLog as c  "
				+ pWhereClause + " order by c.contactdate desc";
		list = EntityManager.getAllByHql(sql, 1, pagesize);
		return list;
	}

	public void addContactLog(ContactLog cl) throws Exception {

		EntityManager.save(cl);
	}

	public void delContactLog(Integer id) throws Exception {

		String sql = "delete from Contact_Log where id=" + id;
		EntityManager.updateOrdelete(sql);

	}

	public ContactLog getContactLog(Integer id) throws Exception {
		String hql = " from ContactLog as c where c.id=" + id + " ";
		return (ContactLog) EntityManager.find(hql);

	}

	public void updateContactLog(ContactLog cl) throws Exception {
		EntityManager.update(cl);

	}
}
