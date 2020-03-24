package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppViewCIntegralDeal {

	public List getViewCIntegralDeal(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List wr = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = " from ViewCIntegralDeal as c "
				+ pWhereClause + " ";
		//System.out.println("-----sql==="+sql);
		wr = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return wr;
	}
	

	public List getAllViewCIntegralDeal() throws Exception {
		List aw = null;
		String sql = " from ViewCIntegralDeal as c ";
		aw = EntityManager.getAllByHql(sql);
		return aw;
	}
	

	
	public ViewCIntegralDeal getViewCIntegralDealByID(Long id) throws Exception {
		ViewCIntegralDeal w = null;
		String sql = " from ViewCIntegralDeal where id=" + id;
		w = (ViewCIntegralDeal) EntityManager.find(sql);
		return w;
	}
	
	public List getViewCIntegralDealByBillno(String billno)throws Exception{
		String sql=" from ViewCIntegralDeal where billno='"+billno+"' ";
		List ls = EntityManager.getAllByHql(sql);
		return ls;
	}
	
	public ViewCIntegralDeal getCIntegralDealByBillNo(String billno,int isort) throws Exception {
		ViewCIntegralDeal w = null;
		String sql = " from ViewCIntegralDeal where billno='" + billno+"' and isort= "+isort+" ";
		w = (ViewCIntegralDeal) EntityManager.find(sql);
		return w;
	}

	
	public List getViewCIntegralTotal(HttpServletRequest request, int pagesize, 
			String pWhereClause, String having)throws Exception{
		String sql = "select cid, cname, mobile, makeorganid, sum(dealintegral) as dealintegral, sum(completeintegral) as completeintegral, sum(duihuan) as duihuan, sum(tiaozheng) as tiaozheng, sum(jieyu) as jieyu " +
		 " from view_c_integral_total " + pWhereClause + 
		" group by cid, cname, mobile, makeorganid "+having+		
		" order by sum(dealintegral) desc ";
		Object obj[] = DbUtil.setGroupByPager(request, sql, pagesize, "ViewCIntegralTotalReport");
		SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
		int targetPage = tmpPgInfo.getCurrentPageNo();
		
		String hql = (String)obj[1];
		hql = hql.replace("view_c_integral_total", "ViewCIntegralTotal");
		return EntityManager.getAllByHql(hql, targetPage, pagesize);
	}
	
	public List getViewCIntegralTotal(String pWhereClause, String having)throws Exception{
		String sql = "select cid, cname, mobile, makeorganid, sum(dealintegral) as dealintegral, sum(completeintegral) as completeintegral, sum(duihuan) as duihuan, sum(tiaozheng) as tiaozheng, sum(jieyu) as jieyu " +
		 " from ViewCIntegralTotal " + pWhereClause + 
		" group by cid, cname, mobile, makeorganid "+having+		
		" order by sum(dealintegral) desc ";		
		return EntityManager.getAllByHql(sql);
	}
	
	public List getViewCIntegralDetail(HttpServletRequest request, int pagesize, 
			String pWhereClause, String having)throws Exception{
		String sql = "select cid, cname, mobile, organid, sum(xiaofei) as xiaofei, sum(jibei) as jibei, sum(tuijian) as tuijian, sum(dinghua) as dinghua, sum(fukuan) as fukuan, sum(songhua) as songhua, sum(dealintegral) as dealintegral " +
		 " from view_c_integral_detail " + pWhereClause + 
		" group by cid, cname, mobile, organid "+having+		
		" order by sum(dealintegral) desc ";
		
		Object obj[] = DbUtil.setGroupByPager(request, sql, pagesize, "ViewCIntegralDetailReport");
		SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
		int targetPage = tmpPgInfo.getCurrentPageNo();
		String hql = (String)obj[1];
		hql = hql.replace("view_c_integral_detail", "ViewCIntegralDetail");
		return EntityManager.getAllByHql(hql, targetPage, pagesize);
	}
	
	public List getViewCIntegralDetail(String pWhereClause, String having)throws Exception{
		String sql = "select cid, cname, mobile, organid, sum(xiaofei) as xiaofei, sum(jibei) as jibei, sum(tuijian) as tuijian, sum(dinghua) as dinghua, sum(fukuan) as fukuan, sum(songhua) as songhua, sum(dealintegral) as dealintegral " +
		 " from ViewCIntegralDetail " + pWhereClause + 
		" group by cid, cname, mobile, organid "+having+		
		" order by sum(dealintegral) desc ";		
		return EntityManager.getAllByHql(sql);
	}
	
	public List getViewCIntegralDuiHuanTotal(HttpServletRequest request, int pagesize, 
			String pWhereClause, String having)throws Exception{
		String sql = "select cid, cname, mobile, organid, sum(duihuan) as duihuan, sum(tiaozheng) as tiaozheng " +
		 " from view_c_integral_duihuan_total " + pWhereClause + 
		" group by cid, cname, mobile, organid "+having+		
		" order by sum(duihuan) desc ";
		Object obj[] = DbUtil.setGroupByPager(request, sql, pagesize, "ViewCIntegralDuiHuanTotalReport");
		SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
		int targetPage = tmpPgInfo.getCurrentPageNo();
		
		String hql = (String)obj[1];
		hql = hql.replace("view_c_integral_duihuan_total", "ViewCIntegralDuihuanTotal");
		return EntityManager.getAllByHql(hql, targetPage, pagesize);
	}
	
	public List getViewCIntegralDuiHuanTotal(String pWhereClause, String having)throws Exception{
		String sql = "select cid, cname, mobile, organid, sum(duihuan) as duihuan, sum(tiaozheng) as tiaozheng " +
		 " from ViewCIntegralDuihuanTotal " + pWhereClause + 
		" group by cid, cname, mobile, organid "+having+		
		" order by sum(duihuan) desc ";		
		return EntityManager.getAllByHql(sql);
	}
	
	public List getViewCIntegralDuihuanDetail(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = " from ViewCIntegralDuihuanDetail as c "
				+ pWhereClause + " ";
		return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}
	
	public List getViewCIntegralDuihuanDetail(String pWhereClause) throws Exception {
		String sql = " from ViewCIntegralDuihuanDetail as c "
				+ pWhereClause + " ";
		return EntityManager.getAllByHql(sql);
	}
	

}
