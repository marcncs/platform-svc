package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppWithdrawDetail {
	
	public void addWithdrawDetail(Object withdrawdetail)throws Exception{
		
		EntityManager.save(withdrawdetail);
		
	}
	
	public WithdrawDetail getWithdrawDetailByID(int id)throws Exception{
		String sql=" from WithdrawDetail as wd where wd.id="+id+"";
		return (WithdrawDetail)EntityManager.find(sql);
	}
	
	public List getWithdrawDetailByWID(String wid)throws Exception{
		String sql=" from WithdrawDetail as wd where wd.wid='"+wid+"'";
		return EntityManager.getAllByHql(sql);
	}

	public void delWithdrawDetailByWID(String id)throws Exception{		
		String sql="delete from withdraw_detail where wid='"+id+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public List getWithdrawDetailByPiidPid(String piid, String productid)
	throws Exception {
		String sql = " from WithdrawDetail where wid='" + piid
				+ "' and productid='" + productid + "'";
		return EntityManager.getAllByHql(sql);
	}
	
	
	public void updIsSettlement(String wid, Integer issettlement)
			throws Exception {
		
		String sql = "update withdraw_detail set issettlement=" + issettlement
				+ " where wid='" + wid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public void updIsSettlementByWidBatch(String wid, String productid, String batch, Integer issettlement)throws Exception{
		
		String sql="update purchase_withdraw_detail set issettlement="+issettlement+" where pwid='"+wid+"' and productid='"+productid+"' and batch='"+batch+"'";
		EntityManager.updateOrdelete(sql);
		
	}

	
	public List getWithdrawDetail(String pWhereClause) throws Exception {
		String sql = "select w.cname,wd from Withdraw as w ,WithdrawDetail as wd "
				+ pWhereClause + " order by wd.productid, w.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getWithdrawDetail2(String pWhereClause) throws Exception {
		String sql = "select w,wd from Withdraw as w ,WithdrawDetail as wd,Product as p "
				+ pWhereClause + " order by wd.productid, w.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}
	
	
	public List getWithdrawTotalReport(HttpServletRequest request, int pagesize, 
			String pWhereClause) throws Exception {
		String sql = "select wd.productid, wd.productname, wd.specmode, wd.unitid, sum(wd.quantity) as quantity, sum(wd.subsum) as subsum "+
		"from withdraw_detail wd, Withdraw w "+ pWhereClause +
		" group by wd.productid,wd.productname, wd.specmode, wd.unitid order by wd.productid";
		
		Object obj[] = DbUtil.setGroupByPager(request, sql, pagesize, "WithdrawTotalReport");
		SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
		int targetPage = tmpPgInfo.getCurrentPageNo();
		String hql = (String)obj[1];
		hql = hql.replace("withdraw_detail", "WithdrawDetail");
		
		return EntityManager.getAllByHql(hql, targetPage, pagesize);
	}
	
	public List getWithdrawTotalReport(String pWhereClause) throws Exception {
		String hql = "select wd.productid, wd.productname, wd.specmode, wd.unitid, sum(wd.quantity) as quantity, sum(wd.subsum) as subsum "+
		"from WithdrawDetail wd, Withdraw w "+ pWhereClause +
		" group by wd.productid,wd.productname, wd.specmode, wd.unitid order by wd.productid";
		
		return EntityManager.getAllByHql(hql);
	}
	
	public List getTotalSubum(String whereSql)throws Exception{		    
	    String sql="select sum(wd.quantity), sum(wd.subsum) from Withdraw as w ,WithdrawDetail as wd "+whereSql;
	    return EntityManager.getAllByHql(sql);		    
	}
	
	
	public List getWithdrawDetail(HttpServletRequest request, int pageSize,
			String pWhereClause) throws Exception {
		String sql = "select cid, cname,so.id,so.makedate,so.makeorganid,sod.productid,sod.productname,"
				+ "sod.specmode,sod.unitid,sod.unitprice,sod.quantity,sod.subsum "
				+ "from Withdraw so,Withdraw_Detail sod " + pWhereClause;
		return PageQuery.jdbcSqlserverQuery(request, sql, pageSize);
	}

	public List getWithdrawDetail1(String pWhereClause) throws Exception {
		String sql = "select cid,so.cname,so.id,so.makedate,so.makeorganid,sod.productid,sod.productname,"
			+ "sod.specmode,sod.unitid,sod.unitprice,sod.quantity,sod.subsum "
			+ "from Withdraw so,Withdraw_Detail sod " + pWhereClause;
		return EntityManager.jdbcquery(sql);
	}
	
	public double getDetailTotalSubum(String whereSql) throws Exception {
		String sql = "select sum(sod.subsum) from Withdraw as so ,WithdrawDetail as sod "
				+ whereSql;
		return EntityManager.getdoubleSum(sql);
	}


	
}
