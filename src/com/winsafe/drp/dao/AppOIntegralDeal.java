package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppOIntegralDeal {

	public List getCIntegralDeal(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List wr = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = " from OIntegralDeal as o "
				+ pWhereClause + " order by o.id ";
		wr = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return wr;
	}
	
	public void updOIntegralDealByID(Long id,Double ci)throws Exception{
		String sql="update o_integral_deal set completeintegral=completeintegral + "+ci+" where id="+id;
		EntityManager.updateOrdelete(sql);
	}

	public void addOIntegralDeal(Object pp) throws Exception {
		
		EntityManager.save(pp);
		
	}
	
	public void delOIntegralDeal(String billno)throws Exception{
		String sql="delete from o_integral_deal where billno='"+billno+"' ";
		EntityManager.updateOrdelete(sql);
	}
	

	public List getAllOIntegralDeal() throws Exception {
		List aw = null;
		String sql = " from OIntegralDeal as c ";
		aw = EntityManager.getAllByHql(sql);
		return aw;
	}


	public OIntegralDeal getOIntegralDealByID(Long id) throws Exception {
		OIntegralDeal w = null;
		String sql = " from OIntegralDeal where id=" + id;
		w = (OIntegralDeal) EntityManager.find(sql);
		return w;
	}
	
	public List getOIntegralDealByBillno(String billno)throws Exception {
		String sql=" from OIntegralDeal where billno='"+billno+"' ";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getOIntegralDealByBillnoIsort(String billno, int isort)throws Exception {
		String sql=" from OIntegralDeal where billno='"+billno+"' and isort="+isort+" ";
		return EntityManager.getAllByHql(sql);
	}
	
	public OIntegralDeal getOIntegralDealByBillNo(String billno,int isort) throws Exception {
		OIntegralDeal w = null;
		String sql = " from OIntegralDeal where billno='" + billno+"' and isort ="+isort+" ";
		w = (OIntegralDeal) EntityManager.find(sql);
		return w;
	}
	

	public void updOIntegralDeal(OIntegralDeal w) throws Exception {
		
		EntityManager.update(w);
		
	}

}
