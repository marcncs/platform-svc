package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppPayable {
	
	public List getPayable(HttpServletRequest request, int pagesize,
			String pWhereClause)throws Exception{
		 String sql=" from Payable as pa "
         +pWhereClause+" order by pa.makedate desc";
		 return PageQuery.hbmQuery(request, sql, pagesize);
	}
	
	public List getPayable(String pWhereClause)throws Exception{
		 String sql=" from Payable as pa "
         +pWhereClause+" order by pa.makedate desc";
		 return EntityManager.getAllByHql(sql);

	}
	
	public List searchPayable(int pagesize, String pWhereClause,
            SimplePageInfo pPgInfo)throws Exception{
		
		 String sql="from Payable as pa "
         +pWhereClause+" order by pa.makedate desc";
		 List ls = EntityManager.getAllByHql(sql, pPgInfo.getCurrentPageNo(),pagesize);
		return ls;
	}
	
	public List getTotalSum(String pWhereClause) throws Exception {
		String sql = "select sum(pa.payablesum), sum(pa.alreadysum) from Payable as pa "
				+ pWhereClause;
		return EntityManager.getAllByHql(sql);
	}
	
	public List getPayableTotel( String pWhereClause)throws Exception{
		 String sql="select pa.id,pa.poid,pa.payablesum,pa.alreadysum,pa.billno,pa.payabledescribe,pa.makeorganid,pa.makeid,pa.makedate from Payable as pa "
         +pWhereClause+" order by pa.makedate desc";
		 return EntityManager.jdbcquery(sql);
	}
	
	public List getPayableByBillno(String billno)throws Exception{
		 String sql=" from Payable where billno='"+billno+"'";
		 return EntityManager.getAllByHql(sql);
	}
	
	public List getPayableByBillno(String billno, String oid)throws Exception{
		 String sql=" from Payable where billno='"+billno+"' and makeorganid='"+oid+"'";
		 return EntityManager.getAllByHql(sql);
	}
	
	
	public Double getPayableSumByPOID(String poid)throws Exception{
		Double s= 0.00;
		String sql = "select sum(pa.payablesum) from Payable as pa where pa.poid='"+poid+"'";
		s = EntityManager.getdoubleSum(sql);
		return s;
	}
	
	
	public void addPayable(Object payable)throws Exception{
		EntityManager.save(payable);

	}
	
	
	public void delPayable(String pid)throws Exception{
		String sql="delete from Payable where id='"+pid+"'";
		 EntityManager.updateOrdelete(sql);
	}
	

	public void delPayableByPOIDBILLNO(String poid,String billno)throws Exception{
		String sql="delete from Payable where poid='"+poid+"' and billno='"+billno+"'";
		 EntityManager.updateOrdelete(sql);

	}
	
	public void delPayableByPOID(String poid)throws Exception{
		String sql="delete from Payable where poid='"+poid+"'";
		EntityManager.updateOrdelete(sql);
	}
	

	
	public Payable getPayableByID(String id)throws Exception{
		String sql=" from Payable where id='"+id+"'";
		return (Payable)EntityManager.find(sql);
	}

	public void updPayable(Payable r)throws Exception {
		//String sql="update payable set payablesum="+r.getPayablesum()+",payabledescribe='"+r.getPayabledescribe()+"' where id='"+r.getId()+"'";
		EntityManager.update(r);

	}

	public void updIsRefer(String wid) throws Exception{
		String hql="update payable set isrefer=1 where id='"+wid+"'";
		EntityManager.updateOrdelete(hql);

	}

	public List waitApprovePayable(int pagesize, String whereSql, SimplePageInfo tmpPgInfo) throws Exception{
		
		 String sql="select pa.id,pa.payablesum,pa.isaudit,pa.makeid,pa.makedate,paa.actid,paa.approve from Payable as pa ,PayableApprove as paa "
        +whereSql+" order by pa.makedate desc";
		 return EntityManager.getAllByHql(sql, tmpPgInfo.getCurrentPageNo(),pagesize);
	}

	public void updIsApprove(String rid, int supperarrove) throws Exception {
    String sql = "update payable set approvestatus=" + supperarrove +
        " where id ='" + rid+"'";
    EntityManager.updateOrdelete(sql);
	}

	public void updIsComplete(String id,Long userid)throws Exception {

		String sql="update payable set paystatus=1,completeid="+userid+",completedate='"+DateUtil.getCurrentDateTime()+"' where id='"+id+"'" ;
		EntityManager.updateOrdelete(sql);

	}
	
public void updIsPayAll(String rid) throws Exception {
		String sql = "update payable set ispay=1 where id='" + rid+"'";
		EntityManager.updateOrdelete(sql);
	}
	

	public void updIsAudit(String oid, Long userid,Integer audit) throws Exception {
		
		String sql = "update payable set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + oid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void updAlreadysumById(double alreadysum, String id) throws Exception {
	    String sql = "update payable set alreadysum=alreadysum+ " + alreadysum +
	        " where id ='" + id+"'";
	    EntityManager.updateOrdelete(sql);
		}
		
		public void updClose(String wid) throws Exception{
			
			String hql="update payable set isclose=1,closedate='"+DateUtil.getCurrentDateString()+"' where payablesum=alreadysum and id='"+wid+"'";
			EntityManager.updateOrdelete(hql);
			
		}
		
		public void backAlreadysumById(double alreadysum, String id) throws Exception {
		    String sql = "update payable set isclose=0,alreadysum=alreadysum- " + alreadysum +
		        " where id ='" + id+"'";
		    EntityManager.updateOrdelete(sql);
			}
		
		public List getTransPaymentLogById(String rids)throws Exception{			
			String sql=" from Payable as r where r.isclose=0 and r.id in("+rids+")";
			return EntityManager.getAllByHql(sql);
		}
}
