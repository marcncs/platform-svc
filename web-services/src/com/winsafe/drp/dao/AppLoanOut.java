package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppLoanOut {

	
	public List searchLoanOut(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = " from LoanOut  "
				+ pWhereClause + " order by makedate desc";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	

	public void addLoanOut(LoanOut lo) throws Exception {
		
		EntityManager.save(lo);
		
	}

	public LoanOut getLoanOutByID(String id) throws Exception {
		LoanOut sl = null;
		String sql = " from LoanOut as so where so.id='" + id + "'";
		sl = (LoanOut) EntityManager.find(sql);
		return sl;
	}
	
	public void delLoanOut(String soid)throws Exception{
		
		String sql="delete from loan_out where id='"+soid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void updLoanOut(LoanOut lo) throws Exception{
		
		EntityManager.update(lo);
		
	}


	
	public void updIsAudit(String soid, Long userid,Integer audit) throws Exception {
		
		String sql = "update loan_out set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + soid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public void updIsReceive(String soid, Long userid,Integer receive) throws Exception {
		
		String sql = "update loan_out set isreceive="+receive+",receiveid=" + userid
				+ ",receivedate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + soid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	

	
	public void updIsTranssale(String id, Long userid, Integer transsale)throws Exception{
		
		String sql="update loan_out set istranssale="+transsale+",transsaleid="+userid
		+",transsaledate='"+DateUtil.getCurrentDateString()+"' where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public int waitInputSaleOrderCount() throws Exception {
		int count = 0;
		String sql = "select count(*) from LoanOut where isaudit=1 and istranssale=0";
		count = EntityManager.getRecordCount(sql);
		return count;
	}
	

}
