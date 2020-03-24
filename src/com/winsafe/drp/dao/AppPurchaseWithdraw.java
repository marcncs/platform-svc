package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppPurchaseWithdraw {

	public List<PurchaseWithdraw> getPurchaseWithdraw(HttpServletRequest request,int pagesize, String pWhereClause) throws Exception {
		String hql = "from PurchaseWithdraw as pw " + pWhereClause
				+ " order by pw.makedate desc";	
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public void addPurchaseWithdraw(PurchaseWithdraw dpd) throws Exception {
		EntityManager.save(dpd);		
	}

	public void updPurchaseWithdraw(PurchaseWithdraw dpd) throws Exception {		
		EntityManager.update(dpd);		
	}

	public void delPurchaseWithdraw(String id) throws Exception {		
		String sql = "delete from purchase_withdraw where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public void updTakeStatus(String id, int takestatus) throws Exception {		
		String sql = "update purchase_withdraw set takestatus="+takestatus+" where id='" + id + "'";
		EntityManager.updateOrdelete(sql);		
	}

	public PurchaseWithdraw getPurchaseWithdrawByID(String id) throws Exception {
		String sql = " from PurchaseWithdraw where id='" + id + "'";
		return (PurchaseWithdraw) EntityManager.find(sql);
	}

	
	public void updIsAudit(String id, Integer userid, Integer audit)
			throws Exception {		
		String sql = "update purchase_withdraw set isaudit=" + audit
				+ ",auditid=" + userid + ",auditdate='"
				+ DateUtil.getCurrentDateTime() + "' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public void updIsEndCase(String id, Integer userid, Integer endcase)
			throws Exception {		
		String sql = "update purchase_withdraw set isendcase=" + endcase
				+ ",endcaseid=" + userid + ",endcasedate='"
				+ DateUtil.getCurrentDateTime() + "' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}
	

	public void blankout(String id, Integer userid, Integer blankout, String blankoutreason) throws Exception {
		
		String sql = "update purchase_withdraw set isblankout="+blankout+",blankoutid=" + userid
				+ ",blankoutdate='" + DateUtil.getCurrentDateTime()
				+ "',blankoutreason='"+blankoutreason+"' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public double getTotalSum(String pWhereClause) throws Exception {
		String sql = "select sum(pw.totalsum) from PurchaseWithdraw as pw "
				+ pWhereClause;
		return EntityManager.getdoubleSum(sql);
	}

	public List getPurchaseProviderTotal(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String hql = "select pb.pid,pb.pname,sum(pb.totalsum) as totalsum " +
		 " from Purchase_Withdraw pb " + whereSql + 
		" group by pb.pid,pb.pname";
		return PageQuery.jdbcSqlserverQuery(request,"pid", hql, pagesize);
	}

	public List getPurchaseProviderTotal(String whereSql) throws HibernateException, SQLException {
		String hql = "select pb.pid,pb.pname,sum(pb.totalsum) as totalsum " +
		 " from Purchase_Withdraw pb " + whereSql + 
		" group by pb.pid,pb.pname";
		return EntityManager.jdbcquery(hql);
	}


	public double getBillTotalSubsum(String whereSql) {
		String hql = "select sum(totalsum) from PurchaseWithdraw pw "+whereSql;
		return EntityManager.getdoubleSum(hql);
	}

	public List getPurchaseWithdrawBill(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String hql = " from PurchaseWithdraw as pw "
			+ whereSql + " order by pw.makedate desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public List getPurchaseWithdrawBill(String whereSql) {
		String hql = " from PurchaseWithdraw as pw "
			+ whereSql + " order by pw.makedate desc ";
		return EntityManager.getAllByHql(hql);
	}

	public List<PurchaseWithdraw> getPurchaseWithdraw(String whereSql) {
		String hql = "from PurchaseWithdraw as pw " + whereSql
		+ " order by pw.makedate desc";	
		return EntityManager.getAllByHql(hql);
	}


}
