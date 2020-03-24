package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppLoan {
	
	public List getLoan(HttpServletRequest request, int pagesize,
			String pWhereClause)throws Exception{
		 String sql=" from Loan as l "
         +pWhereClause+" order by l.makedate desc";
		 return PageQuery.hbmQuery(request, sql, pagesize);
	}
	
	public List getLoanTotel( String pWhereClause)throws Exception{
		 String sql="select pa.id,pa.poid,pa.payablesum,pa.billno,pa.payabledescribe,pa.makeid,pa.makedate from Payable as pa "
         +pWhereClause+" order by pa.makedate desc";
		 List ls = EntityManager.getAllByHql(sql);   
		return ls;
	}
	
	
	public Double getLoanSumByUID(Integer uid)throws Exception{
		String sql = "select sum(l.loansum) from Loan as l where l.uid="+uid+" and l.isaudit=1 ";
		return EntityManager.getdoubleSum(sql);
	}
	
	
	public void addLoan(Object payable)throws Exception{
		EntityManager.save(payable);

	}
	
	
	public void delLoan(String lid)throws Exception{
		String sql="delete from Loan where id='"+lid+"' and isaudit=0";
		EntityManager.updateOrdelete(sql);

	}
	
	public Loan getLoanByID(String id)throws Exception{
		String sql="from Loan where id='"+id+"'";
		Loan w=(Loan)EntityManager.find(sql);
		return w;
	}

	public void updLoan(Loan r)throws Exception {
		//String sql="update payable set payablesum="+r.getPayablesum()+",payabledescribe='"+r.getPayabledescribe()+"' where id='"+r.getId()+"'";
		EntityManager.update(r);

	}
	
	public void updIsAudit(String oid, Integer userid,Integer audit) throws Exception {

		String sql = "update Loan set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + oid + "'";
		EntityManager.updateOrdelete(sql);

	}
	
	
	public void updIsEndcase(String oid, Integer userid,Integer audit) throws Exception {
		String sql = "update loan set isendcase="+audit+",endcaseid=" + userid
				+ ",endcasedate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + oid + "'";
		EntityManager.updateOrdelete(sql);

	}
	

}
