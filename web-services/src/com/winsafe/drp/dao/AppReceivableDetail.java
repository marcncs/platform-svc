package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppReceivableDetail {
	
	public List searchReceivableDetail(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pb = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "from ReceivableDetail as rd "
				+ pWhereClause + " order by rd.id ";
		pb = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pb;
	}
	
	public List getReceivableDetail(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pb = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select rd from Receivable as r,ReceivableDetail as rd "
				+ pWhereClause + " order by rd.id ";
		pb = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pb;
	}
	
	public void addReceivableDetail(Object withdrawdetail)throws Exception{
		
		EntityManager.save(withdrawdetail);
		
	}
	
	public ReceivableDetail getReceivableDetailByID(Long id)throws Exception{
		String sql=" from ReceivableDetail as wd where wd.id="+id+"";
		return (ReceivableDetail)EntityManager.find(sql);
	}
	
	public List getTransIncomeLogByRid(String rids)throws Exception{
		List ls = null;
		String sql=" from ReceivableDetail as rd where rd.isclose=0 and rd.rid in("+rids+")";
		ls=EntityManager.getAllByHql(sql);
		return ls;
	}
	
	public List getReceivableDetailByBillno(String billno)throws Exception{
		List ls = null;
		String sql=" from ReceivableDetail as wd where wd.billno='"+billno+"'";
		ls=EntityManager.getAllByHql(sql);
		return ls;
	}
	
	public List getReceivableDetailByRid(String rid)throws Exception{
		List ls = null;
		String sql=" from ReceivableDetail as wd where wd.rid='"+rid+"'";
		ls=EntityManager.getAllByHql(sql);
		return ls;
	}

	public void delReceivableDetailByRid(String rid)throws Exception{
		String sql="delete from receivable_detail where rid='"+rid+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	public void delReceivableDetailByBillNo(String billno)throws Exception{
		String sql="delete from receivable_detail where billno='"+billno+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	
	public void updAlreadyQuantityAndGoodsfund(String rid, String productid, String batch, Double quantity, Double goodsfund) throws Exception {
		
		String sql = "update receivable_detail set alreadyquantity=alreadyquantity+"+quantity+",alreadysum=alreadysum+"+goodsfund +
		" where rid='" + rid+"' and productid='"+productid+"' and batch='"+batch+"' ";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void backAlreadyQuantityAndGoodsfund(String billno, String productid, String batch, Double quantity, Double goodsfund) throws Exception {
		
		String sql = "update receivable_detail set isclose=0,alreadyquantity=alreadyquantity- "+quantity+",alreadysum=alreadysum- "+goodsfund +
		" where billno='" + billno+"' and productid='"+productid+"' and batch='"+batch+"' ";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public void updIsclose(String rid, String productid, String batch) throws Exception {
		
		String sql = "update receivable_detail set isclose=1, closedate='" + DateUtil.getCurrentDateString() +
		"' where quantity=alreadyquantity and rid='" + rid+"' and productid='"+productid+"' and batch='"+batch+"' ";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public double getReceivableSum(String rid) throws Exception{	
		String sql = "select sum(rd.goodsfund*(rd.quantity- rd.alreadyquantity)) from ReceivableDetail as rd where rd.isclose=0 and rd.rid='"+rid+"'";			
		return EntityManager.getdoubleSum(sql);		
	}
	
	public double getReceivableSumByDate(String roid,String date) throws Exception{	
		String sql = "select sum(rd.goodsfund*(rd.quantity- rd.alreadyquantity)) from ReceivableDetail as rd where rd.isclose=0 and rd.roid='"+roid+"' and rd.makedate<'"+date+"'";			
		return EntityManager.getdoubleSum(sql);		
	}
	
}
