package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppIntegralDetail {

	public List getIntegralDetail(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String sql = " from IntegralDetail as d "
				+ pWhereClause + " order by d.id ";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	
	public List<IntegralDetail> getIntegralDetail(String pWhereClause) throws Exception {
		String sql = " from IntegralDetail as d "
				+ pWhereClause + " order by d.id ";
		return EntityManager.getAllByHql(sql);
	}
	
	public void updIntegralDetailByID(Long id,Double ci)throws Exception{
		String sql="update Integral_Detail set aout=aout + "+ci+" where id="+id;
		EntityManager.updateOrdelete(sql);
	}
	
	public void updIidByBillno(String billno,int osort, int iid)throws Exception{
		String sql="update Integral_Detail set iid="+iid+" where billno='"+billno+
		"' and osort="+osort+" and iid is null";
		EntityManager.updateOrdelete(sql);
	}

	public void addIntegralDetail(Object pp) throws Exception {		
		EntityManager.save(pp);		
	}
	
	public void delIntegralDetail(String billno)throws Exception{
		String sql="delete from Integral_Detail where billno='"+billno+"' ";
		EntityManager.updateOrdelete(sql);
	}
	

	public List getAllIntegralDetail() throws Exception {
		String sql = " from IntegralDetail as c ";
		return  EntityManager.getAllByHql(sql);
	}


	public IntegralO getIntegralDetailByID(Long id) throws Exception {
		String sql = " from IntegralDetail where id=" + id;
		return (IntegralO) EntityManager.find(sql);
	}
	
	public List getIntegralDetailByBillno(String billno)throws Exception {
		String sql=" from Integral_Detail where billno='"+billno+"' ";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getIntegralDetailByBillnoIsort(String billno, int isort)throws Exception {
		String sql=" from IntegralDetail where billno='"+billno+"' and isort="+isort+" ";
		return EntityManager.getAllByHql(sql);
	}
	
	public IntegralO getIntegralDetailByBillNo(String billno,int osort) throws Exception {
		IntegralO w = null;
		String sql = " from Integral_Detail where billno='" + billno+"' and osort ="+osort+" ";
		return (IntegralO) EntityManager.find(sql);

	}
	

	public void updIntegralDetail(IntegralDetail w) throws Exception {
		
		EntityManager.update(w);
		
	}

}
