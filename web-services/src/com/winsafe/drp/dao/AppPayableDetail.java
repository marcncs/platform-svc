package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppPayableDetail {
	
	public List searchPayableDetail(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pb = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "from PayableDetail as rd "
				+ pWhereClause + " order by rd.id ";
		pb = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pb;
	}
	
	public List getPayableDetail(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pb = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select rd from Payable as r,PayableDetail as rd "
				+ pWhereClause + " order by rd.id ";
		pb = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pb;
	}
	
	public void addPayableDetail(Object withdrawdetail)throws Exception{
		
		EntityManager.save(withdrawdetail);
		
	}
	
	public PayableDetail getPayableDetailByID(Long id)throws Exception{
		String sql=" from PayableDetail as wd where wd.id="+id+"";
		return (PayableDetail)EntityManager.find(sql);
	}
	
	public List getPayableDetailByBillno(String billno)throws Exception{
		List ls = null;
		String sql=" from PayableDetail as wd where wd.billno='"+billno+"'";
		ls=EntityManager.getAllByHql(sql);
		return ls;
	}
	
	public List getPayableDetailByPid(String pid)throws Exception{
		List ls = null;
		String sql=" from PayableDetail as wd where wd.pid='"+pid+"'";
		ls=EntityManager.getAllByHql(sql);
		return ls;
	}
	
	public List getTransPaymentLogByPid(String pids)throws Exception{
		List ls = null;
		String sql=" from PayableDetail as rd where rd.isclose=0 and rd.pid in("+pids+")";
		ls=EntityManager.getAllByHql(sql);
		return ls;
	}

	public void delPayableDetailByPid(String pid)throws Exception{
		
		String sql="delete from payable_detail where pid='"+pid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public void updAlreadyQuantityAndGoodsfund(String pid, String productid, String batch, Double quantity, Double goodsfund) throws Exception {
		
		String sql = "update payable_detail set alreadyquantity=alreadyquantity+"+quantity+",alreadysum=alreadysum+"+goodsfund +
		" where pid='" + pid+"' and productid='"+productid+"' and batch='"+batch+"' ";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void backAlreadyQuantityAndGoodsfund(String billno, String productid, String batch, Double quantity, Double goodsfund) throws Exception {
		
		String sql = "update payable_detail set isclose=0,alreadyquantity=alreadyquantity- "+quantity+",alreadysum=alreadysum- "+goodsfund +
		" where billno='" + billno+"' and productid='"+productid+"' and batch='"+batch+"' ";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public void updIsclose(String pid, String productid, String batch) throws Exception {
		
		String sql = "update payable_detail set isclose=1, closedate='" + DateUtil.getCurrentDateString() +
		"' where quantity=alreadyquantity and pid='" + pid+"' and productid='"+productid+"' and batch='"+batch+"' ";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public double getReceivableSum(String rid) throws Exception{	
		String sql = "select sum(rd.goodsfund*(rd.quantity- rd.alreadyquantity)) from PayableDetail as rd where rd.isclose=0 and rd.pid='"+rid+"'";			
		return EntityManager.getdoubleSum(sql);		
	}
}
