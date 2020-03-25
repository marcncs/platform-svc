package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppIntegralI {

	public List getIntegralI(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String sql = " from IntegralI as ii "
				+ pWhereClause + " order by ii.id desc";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	public List<IntegralI> getIntegralI(String pWhereClause) throws Exception {
		String sql = " from IntegralI as ii "
				+ pWhereClause + " order by ii.id desc";
		return EntityManager.getAllByHql(sql);
	}
	
	
	public void updIntegralIByID(String billno,Double ci)throws Exception{
		String sql="update integral_i set aincome=aincome + "+ci+" where billno='"+billno+"' ";
		EntityManager.updateOrdelete(sql);
	}

	public void addIntegralI(Object pp) throws Exception {
		EntityManager.save(pp);
	}

	public List getAllIntegralI() throws Exception {
		List aw = null;
		String sql = " from IntegralI as c ";
		aw = EntityManager.getAllByHql(sql);
		return aw;
	}
	
	public void delIntegralI(String billno)throws Exception{
		String sql="delete from Integral_I where billno='"+billno+"' ";
		EntityManager.updateOrdelete(sql);
	}
	
	
	public IntegralI getIntegralIByID(Long id) throws Exception {
		String sql = " from IntegralI where id=" + id;
		return (IntegralI) EntityManager.find(sql);

	}
	
	public List getIntegralIByBillno(String billno)throws Exception{
		String sql=" from IntegralI where billno='"+billno+"' ";
		return EntityManager.getAllByHql(sql);
	}
	
//	public IntegralI getIntegralByBillNo(String billno)throws Exception{
//		String sql=" from IntegralI where billno='"+billno+"' ";
//		return (IntegralI) EntityManager.find(sql);
//	}
	
	
	public IntegralI getIntegralIByBillNo(String billno,int osort) throws Exception {
		String sql = " from IntegralI where billno='" + billno+"' and osort= "+osort+" ";
		return (IntegralI) EntityManager.find(sql);

	}
	
	public void updIntegralI(IntegralI w) throws Exception {		
		EntityManager.update(w);		
	}
	
	public void dealAIncome(String billno, double rate) throws Exception{
		List<IntegralI> iilist = getIntegralIByBillno(billno);
		for ( IntegralI ii : iilist ){
			if ( ii != null ){
				ii.setAincome(ii.getAincome()+ii.getRincome()*rate);
				updIntegralI(ii);
			}
		}	
	}

}
