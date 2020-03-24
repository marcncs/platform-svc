package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppPurchaseWithdrawDetail {
	
	public void addPurchaseWithdrawDetail(PurchaseWithdrawDetail[] withdrawdetail)throws Exception{
		
		EntityManager.save(withdrawdetail);
		
	}
	
	public PurchaseWithdrawDetail getPurchaseWithdrawDetailByID(Long id)throws Exception{
		String sql=" from PurchaseWithdrawDetail as wd where wd.id="+id+"";
		return (PurchaseWithdrawDetail)EntityManager.find(sql);
	}
	
	public List<PurchaseWithdrawDetail> getPurchaseWithdrawDetailByPWID(String wid)throws Exception{
		String sql=" from PurchaseWithdrawDetail as wd where wd.pwid='"+wid+"'";
		return EntityManager.getAllByHql(sql);
	}

	public void delPurchaseWithdrawDetailByPWID(String id)throws Exception{
		
		String sql="delete from purchase_withdraw_detail where pwid='"+id+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public void updIsSettlementByPwidBatch(String pwid, String productid, String batch, Integer issettlement)throws Exception{
		
		String sql="update purchase_withdraw_detail set issettlement="+issettlement+" where pwid='"+pwid+"' and productid='"+productid+"' and batch='"+batch+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public void updIsSettlement(String pwid, Integer issettlement)throws Exception{
		
		String sql="update purchase_withdraw_detail set issettlement="+issettlement+" where pwid='"+pwid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public List getSettlementPurchaseWithdrawDetail(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select d from PurchaseWithdraw as s ,PurchaseWithdrawDetail as d "
				+ pWhereClause + " order by d.id ";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	public List getSettlementPurchaseWithdrawDetail2(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select d from PurchaseWithdraw as s ,PurchaseWithdrawDetail as d,Product as p "
				+ pWhereClause + " order by d.id ";
		//System.out.println("--------->sql="+sql);
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	
	
	public void updTakeQuantity(String soid,String productid, String batch, double takequantity)throws Exception{
		
		String sql="update purchase_withdraw_detail set takequantity=takequantity + "+takequantity+" where pwid='"+soid+"' and batch='"+batch+"' and productid='"+productid+"'";
		//System.out.println("------------------------------------------->"+sql);
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List getDetailReport(HttpServletRequest request,int pagesize, String pWhereClause) throws Exception {
		String sql = "select pw.pid, pw.pname,pw.makedate,pwd from PurchaseWithdraw as pw ,PurchaseWithdrawDetail as pwd "
				+ pWhereClause + " order by pwd.productid, pw.makedate desc ";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	
	public List getDetailReport(String pWhereClause) throws Exception {
		String sql = "select pw.pid, pw.pname,pw.makedate,pwd from PurchaseWithdraw as pw ,PurchaseWithdrawDetail as pwd "
			+ pWhereClause + " order by pwd.productid, pw.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}
	
	
	
	public List getTotalReport(HttpServletRequest request, int pagesize, 
			String pWhereClause) throws Exception {
		String hql = "select pwd.productid, pwd.productname, pwd.specmode, pwd.unitid, sum(pwd.quantity) as quantity, sum(pwd.subsum) as subsum "+
		"from PurchaseWithdrawDetail pwd, PurchaseWithdraw pw "+ pWhereClause +
		" group by pwd.productid,pwd.productname, pwd.specmode, pwd.unitid order by pwd.productid";
		String sql= hql.replace("PurchaseWithdrawDetail", "purchase_withdraw_detail");
		sql = sql.replace("PurchaseWithdraw", "Purchase_Withdraw");
		
		Object obj[] = DbUtil.setGroupByPager(request, sql, pagesize, "PurchaseWithdrawTotalReport");
		SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
		int targetPage = tmpPgInfo.getCurrentPageNo();
		return EntityManager.getAllByHql(hql, targetPage, pagesize);
	}
	
	public List getTotalReport(
			String pWhereClause) throws Exception {
		String hql = "select pwd.productid, pwd.productname, pwd.specmode, pwd.unitid, sum(pwd.quantity) as quantity, sum(pwd.subsum) as subsum "+
		"from PurchaseWithdrawDetail pwd, PurchaseWithdraw pw "+ pWhereClause +
		" group by pwd.productid,pwd.productname, pwd.specmode, pwd.unitid order by pwd.productid";
		
	
		return EntityManager.getAllByHql(hql);
	}
	
	public List getTotalSubum(String whereSql)throws Exception{		    
	    String sql="select sum(pwd.quantity) as quantity, sum(pwd.subsum) as subsum from Purchase_Withdraw as pw ,Purchase_Withdraw_Detail as pwd "+whereSql;
	    return EntityManager.jdbcquery(sql);
	}

	public List getPurchaseProductTotal(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String hql = "select pw.makeorganid,pwd.productid, pwd.productname, pwd.specmode, pwd.unitid, sum(pwd.quantity) as quantity, sum(pwd.subsum) as subsum " +
		 " from Purchase_Withdraw_Detail pwd, Purchase_Withdraw pw " + whereSql + 
		" group by pw.makeorganid,pwd.productid,pwd.productname, pwd.specmode, pwd.unitid ";
		return PageQuery.jdbcSqlserverQuery(request,"productid", hql, pagesize);
	}

	public List getPurchaseProductTotal(String whereSql) throws HibernateException, SQLException {
		String hql = "select pw.makeorganid,pwd.productid, pwd.productname, pwd.specmode, pwd.unitid, sum(pwd.quantity) as quantity, sum(pwd.subsum) as subsum " +
		 " from Purchase_Withdraw_Detail pwd, Purchase_Withdraw pw " + whereSql + 
		" group by pw.makeorganid,pwd.productid,pwd.productname, pwd.specmode, pwd.unitid ";
		return EntityManager.jdbcquery(hql);
	}

	public List getPurchaseWithdrawBill(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String hql = " from PurchaseWithdraw as pb "
			+ whereSql + " order by pb.makedate desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
}
