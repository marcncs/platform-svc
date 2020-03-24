package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppBorrowIncome {

	
//	public List searchBorrowIncome(int pagesize, String pWhereClause,
//			SimplePageInfo pPgInfo) throws Exception {
//		List pls = null;
//		int targetPage = pPgInfo.getCurrentPageNo();
//		String sql = " from BorrowIncome  "
//				+ pWhereClause + " order by makedate desc";
//		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
//		return pls;
//	}
	
	public List searchBorrowIncome(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List sb = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select pi from BorrowIncome as pi,WarehouseVisit as wv "
				+ pWhereClause + " order by pi.makedate desc ";
		sb = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return sb;
	}
	

	public void addBorrowIncome(BorrowIncome lo) throws Exception {
		
		EntityManager.save(lo);
		
	}

	public BorrowIncome getBorrowIncomeByID(String id) throws Exception {
		BorrowIncome sl = null;
		String sql = " from BorrowIncome as so where so.id='" + id + "'";
		sl = (BorrowIncome) EntityManager.find(sql);
		return sl;
	}
	
	public void delBorrowIncome(String soid)throws Exception{
		
		String sql="delete from borrow_income where id='"+soid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void updBorrowIncome(BorrowIncome lo) throws Exception{
		
		EntityManager.update(lo);
		
	}


	
	public void updIsAudit(String soid, Long userid,Integer audit) throws Exception {
		
		String sql = "update borrow_income set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + soid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public void updIsBackTrack(String soid, Long userid,Integer backtrack) throws Exception {
		
		String sql = "update borrow_income set isbacktrack="+backtrack+",backtrackid=" + userid
				+ ",backtrackdate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + soid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	

	
	public void updIsTranssale(String id, Long userid, Integer transsale)throws Exception{
		
		String sql="update borrow_income set istranswithdraw="+transsale+",transwithdrawid="+userid
		+",transwithdrawdate='"+DateUtil.getCurrentDateString()+"' where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public int waitInputSaleOrderCount() throws Exception {
		int count = 0;
		String sql = "select count(*) from BorrowIncome where isaudit=1 and istranswithdraw=0";
		count = EntityManager.getRecordCount(sql);
		return count;
	}
	

}
