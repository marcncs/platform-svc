package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppReceivable {
	
	public List getReceivable(HttpServletRequest request, int pagesize,
			String pWhereClause)throws Exception{
		
		 String sql=" from Receivable as r "
         +pWhereClause+" order by r.makedate desc";
		 return PageQuery.hbmQuery(request, sql, pagesize);
	}
	
	public List getReceivable(String pWhereClause)throws Exception{
		
		 String sql=" from Receivable as r "
         +pWhereClause+" order by r.makedate desc";
		 List ls = EntityManager.getAllByHql(sql);
		return ls;
	}
	
	public List searchReceivable(int pagesize, String pWhereClause,
            SimplePageInfo pPgInfo)throws Exception{
		 String sql="from Receivable as r "
         +pWhereClause+" order by r.makedate desc";
		 List ls = EntityManager.getAllByHql(sql, pPgInfo.getCurrentPageNo(),pagesize);
		return ls;
	}
	
	public List getTotalSum(String pWhereClause) throws Exception {
		String sql = "select sum(r.receivablesum), sum(r.alreadysum) from Receivable as r "
				+ pWhereClause;
		return EntityManager.getAllByHql(sql);
	}
	
	public List getReceivableByBillno(String billno)throws Exception{
		 String sql=" from Receivable where billno='"+billno+"'";
		 List ls = EntityManager.getAllByHql(sql);
		return ls;
	}
	
	
	public List getReceivableTotal(String pWhereClause)throws Exception{
		 String sql="select r.id,r.roid,r.receivablesum,r.alreadysum,r.billno,r.receivabledescribe,r.makeorganid,r.makeid,r.makedate from Receivable as r "
         +pWhereClause+" order by r.makedate desc";
		 return EntityManager.jdbcquery(sql);
	}
	

	public Double getReceivableSumByROID(String roid)throws Exception{
		Double s= 0.00;
		String sql = "select sum(r.receivablesum) from Receivable as r where r.roid='"+roid+"'";
		s = EntityManager.getdoubleSum(sql);
		return s;
	}
	
	public void addReceivable(Object receivable)throws Exception{
		
		EntityManager.save(receivable);
		
	}
	
	public void delReceivable(String rid)throws Exception {		
		String sql="delete from receivable where id='"+rid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void delReceivableByROIDBILLNO(String roid,String billno)throws Exception{
		
		String sql="delete receivable where roid='"+roid+"' and billno='"+billno+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	

	
	public void delReceivableByROID(String roid,String orgid)throws Exception {
		
		String sql="delete from receivable where roid='"+roid+"' and makeorganid='"+orgid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public Receivable getReceivableByID(String id)throws Exception{
		String sql="from Receivable where id='"+id+"'";
		Receivable w=(Receivable)EntityManager.find(sql);
		return w;
	}

	public void updReceivable(Receivable r)throws Exception {
		
		//String sql="update receivable set receivablesum="+r.getReceivablesum()+",receivabledescribe='"+r.getReceivabledescribe()+"' where id='"+r.getId()+"'";
		EntityManager.update(r);
		
	}

	

	public List waitApproveReceivable(int pagesize, String whereSql, SimplePageInfo tmpPgInfo) throws Exception{
		
		 String sql="select r.id,r.receivablesum,r.isaudit,r.makeid,r.makedate,ra.actid,ra.approve from Receivable as r ,ReceivableApprove as ra "
        +whereSql+" order by r.makedate desc";
		 List ls = EntityManager.getAllByHql(sql, tmpPgInfo.getCurrentPageNo(),pagesize);
		return ls;
	}

	public void updAlreadysumById(double alreadysum, String id) throws Exception {
    String sql = "update receivable set alreadysum=alreadysum+ " + alreadysum +
        " where id ='" + id+"'";
    EntityManager.updateOrdelete(sql);
	}
	
	public void updClose(String wid) throws Exception{
		
		String hql="update receivable set isclose=1,closedate='"+DateUtil.getCurrentDateString()+"' where receivablesum=alreadysum and id='"+wid+"'";
		EntityManager.updateOrdelete(hql);
		
	}
	
	public void backAlreadysumById(double alreadysum, String id) throws Exception {
	    String sql = "update receivable set isclose=0,alreadysum=alreadysum - " + alreadysum +
	        " where id ='" + id+"'";
	    EntityManager.updateOrdelete(sql);
		}
	

	public void updIsAudit(String oid, Integer userid,Integer audit) throws Exception {
		
		String sql = "update receivable set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + oid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List getTransIncomeLogById(String rids)throws Exception{
		
		String sql=" from Receivable as r where r.isclose=0 and r.id in("+rids+")";
		List ls=EntityManager.getAllByHql(sql);
		return ls;
	}

}
