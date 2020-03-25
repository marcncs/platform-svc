package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppCIntegralDeal {

	public List getCIntegralDeal(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List wr = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = " from CIntegralDeal as c "
				+ pWhereClause + " order by c.id desc";
		wr = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return wr;
	}
	
	public List getCIntegralDealCustomer(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List wr = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select c,ct from CIntegralDeal as c, Customer as ct "
				+ pWhereClause + " order by c.id desc";
		wr = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return wr;
	}
	
	public List getCIntegralDealCustomer(String pWhereClause) throws Exception {
		List wr = null;
		String sql = "select c,ct from CIntegralDeal as c, Customer as ct "
				+ pWhereClause + " order by c.id desc";
		wr = EntityManager.getAllByHql(sql);
		return wr;
	}
	
	public void updCIntegralDealByID(Long id,Double ci)throws Exception{
		String sql="update c_integral_deal set completeintegral=completeintegral + "+ci+" where id="+id;
		EntityManager.updateOrdelete(sql);
	}

	public void addCIntegralDeal(Object pp) throws Exception {
		EntityManager.save(pp);
	}

	public List getAllCIntegralDeal() throws Exception {
		List aw = null;
		String sql = " from CIntegralDeal as c ";
		aw = EntityManager.getAllByHql(sql);
		return aw;
	}
	
	public void delCIntegralDeal(String billno)throws Exception{
		String sql="delete from c_integral_deal where billno='"+billno+"' ";
		EntityManager.updateOrdelete(sql);
	}
	
	
	public CIntegralDeal getCIntegralDealByID(Long id) throws Exception {
		CIntegralDeal w = null;
		String sql = " from CIntegralDeal where id=" + id;
		w = (CIntegralDeal) EntityManager.find(sql);
		return w;
	}
	
	public List getCIntegralDealByBillno(String billno)throws Exception{
		String sql=" from CIntegralDeal where billno='"+billno+"' ";
		return EntityManager.getAllByHql(sql);
	}
	
	
	public CIntegralDeal getCIntegralDealByBillNo(String billno,int isort) throws Exception {
		CIntegralDeal w = null;
		String sql = " from CIntegralDeal where billno='" + billno+"' and isort= "+isort+" ";
		w = (CIntegralDeal) EntityManager.find(sql);
		return w;
	}
	

	public void updCIntegralDeal(CIntegralDeal w) throws Exception {
		
		EntityManager.update(w);
		
	}

}
