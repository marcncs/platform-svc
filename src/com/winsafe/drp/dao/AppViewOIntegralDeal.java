package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppViewOIntegralDeal {

	public List getViewOIntegralDeal(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List wr = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = " from ViewOIntegralDeal as c "
				+ pWhereClause + " ";
		//System.out.println("-----sql==="+sql);
		wr = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return wr;
	}
	

	public List getAllViewOIntegralDeal() throws Exception {
		List aw = null;
		String sql = " from ViewOIntegralDeal as c ";
		aw = EntityManager.getAllByHql(sql);
		return aw;
	}
	

	
	public ViewOIntegralDeal getViewOIntegralDealByID(Long id) throws Exception {
		ViewOIntegralDeal w = null;
		String sql = " from ViewOIntegralDeal where id=" + id;
		w = (ViewOIntegralDeal) EntityManager.find(sql);
		return w;
	}
	
	public List getViewOIntegralDealByBillno(String billno)throws Exception{
		String sql=" from ViewOIntegralDeal where billno='"+billno+"' ";
		List ls = EntityManager.getAllByHql(sql);
		return ls;
	}
	
	public ViewOIntegralDeal getCIntegralDealByBillNo(String billno,int isort) throws Exception {
		ViewOIntegralDeal w = null;
		String sql = " from ViewOIntegralDeal where billno='" + billno+"' and isort= "+isort+" ";
		w = (ViewOIntegralDeal) EntityManager.find(sql);
		return w;
	}
	
	public List getViewOIntegralTotal(HttpServletRequest request, int pagesize, 
			String pWhereClause)throws Exception{
		String sql = "select oid, organname, sum(dealintegral) as dealintegral, sum(completeintegral) as completeintegral, sum(tiaozheng) as tiaozheng, sum(leiji) as leiji " +
		 " from view_o_integral_total " + pWhereClause + 
		" group by oid, organname "+
		" order by sum(dealintegral) desc ";
		Object obj[] = DbUtil.setGroupByPager(request, sql, pagesize, "ViewOIntegralTotalReport");
		SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
		int targetPage = tmpPgInfo.getCurrentPageNo();
		
		String hql = (String)obj[1];
		hql = hql.replace("view_o_integral_total", "ViewOIntegralTotal");
		return EntityManager.getAllByHql(hql, targetPage, pagesize);
	}
	
	public List getViewOIntegralTotal(String pWhereClause)throws Exception{
		String sql = "select oid, organname, sum(dealintegral) as dealintegral, sum(completeintegral) as completeintegral, sum(tiaozheng) as tiaozheng, sum(leiji) as leiji " +
		 " from ViewOIntegralTotal " + pWhereClause + 
		" group by oid, organname "+
		" order by sum(dealintegral) desc ";
		
		return EntityManager.getAllByHql(sql);
	}


}
