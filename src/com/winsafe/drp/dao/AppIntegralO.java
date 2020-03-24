package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppIntegralO {

	public List getIntegralO(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String sql = " from IntegralO as io "
				+ pWhereClause + " order by io.id ";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	
	public List<IntegralO> getIntegralO(String pWhereClause) throws Exception {
		String sql = " from IntegralO as io "
				+ pWhereClause + " order by io.id ";
		return EntityManager.getAllByHql(sql);
	}
	
	public void updIntegralOByID(String billno,Double ci)throws Exception{
		String sql="update Integral_O set aout=aout + "+ci+" where billno='"+billno+"' ";
		EntityManager.updateOrdelete(sql);
	}

	public void addIntegralO(Object pp) throws Exception {
		
		EntityManager.save(pp);
		
	}
	
	public void delIntegralO(String billno)throws Exception{
		String sql="delete from Integral_O where billno='"+billno+"' ";
		EntityManager.updateOrdelete(sql);
	}
	

	public List getAllIntegralO() throws Exception {

		String sql = " from IntegralO as c ";
		return  EntityManager.getAllByHql(sql);

	}


	public IntegralO getIntegralOByID(Long id) throws Exception {
		String sql = " from IntegralO where id=" + id;
		return (IntegralO) EntityManager.find(sql);
	}
	
	public List getIntegralOByBillno(String billno)throws Exception {
		String sql=" from IntegralO where billno='"+billno+"' ";
		return EntityManager.getAllByHql(sql);
	}
	
	public IntegralO getIntegralOByBillNo(String billno)throws Exception{
		String sql=" from IntegralO where billno='"+billno+"' ";
		return (IntegralO) EntityManager.find(sql);
	}
	
	public List getIntegralOByBillnoIsort(String billno, int isort)throws Exception {
		String sql=" from IntegralO where billno='"+billno+"' and isort="+isort+" ";
		return EntityManager.getAllByHql(sql);
	}
	
	public IntegralO getIntegralOByBillNo(String billno,int osort) throws Exception {
		IntegralO w = null;
		String sql = " from Integral_O where billno='" + billno+"' and osort ="+osort+" ";
		return (IntegralO) EntityManager.find(sql);

	}
	

	public void updIntegralO(IntegralO w) throws Exception {		
		EntityManager.update(w);		
	}
	
	public void dealAOut(String billno, double rate) throws Exception{
		List<IntegralO> iilist = getIntegralOByBillno(billno);
		for ( IntegralO ii : iilist ){
			if ( ii != null ){
				ii.setAout(ii.getAout()+ii.getRout()*rate);
				updIntegralO(ii);
			}
		}	
	}

}
