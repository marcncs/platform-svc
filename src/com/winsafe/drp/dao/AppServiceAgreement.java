package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppServiceAgreement {

	public List getServiceAgreement(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List list = null;
		String sql = "select sa.id,sa.cid,sa.linkman,sa.tel,sa.scontent,sa.sfee,sa.sstatus,sa.rank,sa.sdate,sa.question,sa.memo,sa.isallot,sa.makeid,sa.makedate from ServiceAgreement as sa "
				+ pWhereClause + " order by sa.id desc";

		list = EntityManager.getAllByHql(sql, pPgInfo.getCurrentPageNo(),
				pagesize);
		return list;
	}

	public List searchServiceAgreement(HttpServletRequest request,
			int pagesize, String pWhereClause) throws Exception {
		String hql = "select sa.id,sa.objsort,sa.cid,sa.cname,sa.scontent,sa.sstatus,sa.rank,sa.sdate,sa.makeorganid,sa.makeid,se.isaffirm from ServiceAgreement as sa , ServiceExecute as se "
				+ pWhereClause + " order by sa.id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	
	public List listReceiptService(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List rt = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select sa.id,sa.cid,sa.linkman,sa.tel,sa.scontent,sa.sfee,sa.sstatus,sa.rank,sa.sdate,sa.makeid,sa.makedate,se.isaffirm from ServiceAgreement as sa,ServiceExecute as se "
				+ pWhereClause + " order by se.isaffirm,sa.makedate ";
		rt = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return rt;
	}

	public void addServiceAgreement(Object r) throws Exception {

		EntityManager.save(r);

	}

	public void delServiceAgreement(Integer id) throws Exception {

		String sql = "delete from Service_Agreement where id=" + id;
		EntityManager.updateOrdelete(sql);

	}

	public ServiceAgreement getServiceAgreementByID(Integer id)
			throws Exception {
		String sql = " from ServiceAgreement as sa where sa.id=" + id;
		return (ServiceAgreement) EntityManager.find(sql);
	}

	public void updIsAllot(Integer id) throws Exception {

		String sql = "update Service_Agreement set isallot = 1 where id=" + id;
		EntityManager.updateOrdelete(sql);

	}

	public void updServiceAgreement(ServiceAgreement sa) throws Exception {
		EntityManager.update(sa);

	}
}
