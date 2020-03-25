package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppPurchaseBill {


	public List<PurchaseBill> getPurchaseBill(HttpServletRequest request,int pagesize, String pWhereClause) throws Exception {
		
		String hql = " from PurchaseBill as pb "
				+ pWhereClause + " order by pb.makedate desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	
	public List getPurchaseHistoryChen(String pid,String productid)throws Exception {

		String sql = "select pbd.unitprice,pb.makedate from PurchaseBill as pb,PurchaseBillDetail as pbd where pb.isratify=1 and pb.pid='"+pid+"' and pbd.productid='"+productid+"' and pb.id=pbd.pbid order by pb.makedate desc";
		return EntityManager.getAllByHql(sql,1,5);
	}

	
	public void addPurchaseBill(Object purchasebill) throws Exception {
		
		EntityManager.save(purchasebill);
		
	}
	
	public void updPurchaseBill(PurchaseBill purchasebill) throws Exception {
		
		EntityManager.update(purchasebill);
		
	}

	
	public PurchaseBill getPurchaseBillByID(String id) throws Exception {
		PurchaseBill pb = null;
		String sql = " from PurchaseBill as pb where pb.id='" + id + "'";
		pb = (PurchaseBill) EntityManager.find(sql);
		return pb;
	}

	
	public void updPurchaseBillByID(PurchaseBill pb)
			throws Exception {
		
		EntityManager.update(pb);
		
	}


	
	public List waitApprovePurchaseBill(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List wpa = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select pb.id,pb.pid,pb.makeid,pb.makedate,pb.totalsum,pba.actid,pba.approve,pba.id from PurchaseBill as pb,ApproveFlowLog as pba "
				+ pWhereClause
				+ " order by pb.approvestatus, pb.makedate desc";
		wpa = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return wpa;
	}


	public List getShouldPayment(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pb = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select pb.id,pb.pid,pb.totalsum,pb.createdate,pb.purchasebillstatus,pb.ispaymentall from PurchaseBill as pb "
				+ pWhereClause + " order by pb.createdate desc ";
		pb = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pb;
	}

	//
	public double getTotalPayment(String pWhereClause) throws Exception {
		double totalincome = Double.parseDouble("0.00");
		String sql = "select sum(pb.totalsum) from PurchaseBill as pb "
				+ pWhereClause;
		totalincome = EntityManager.getdoubleSum(sql);
		return totalincome;
	}

	
	public int waitIncomePurchaseBill(String organid) throws Exception {
		String sql = "select count(*) from PurchaseBill as pb where pb.isratify=1 and pb.istransferadsum=0 and pb.makeorganid='"+organid+"'";
		return EntityManager.getRecordCount(sql);
	}

	
	public void updIstransferAdsum(String pbid) throws Exception {
		
		String sql = "update purchase_bill set istransferadsum=1 where id='"
				+ pbid+"'";
		EntityManager.updateOrdelete(sql);
		
	}

	
	public void updIsPaymentAll(Long pbid) throws Exception {
		
		String sql = "update purchase_bill set ispaymentall=1 where id=" + pbid;
		EntityManager.updateOrdelete(sql);
		
	}

	
	public void updPurchaseBillStatus(String pbid,Integer status) throws Exception {
		
		String sql = "update purchase_bill set purchasebillstatus="+status+" where id = '"
				+ pbid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	

	public void delPurchaseBill(String id)throws Exception{
		
		String sql="delete from purchase_bill where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public void updIsAudit(String ppid, Long userid,Integer audit) throws Exception {
		
		String sql = "update purchase_bill set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	

	public void updIsRatify(String ppid, Long userid,Integer ratify) throws Exception {
		
		String sql = "update purchase_bill set isratify="+ratify+",ratifyid=" + userid
				+ ",ratifydate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	

	public List getPurchaseBillTotal(String pWhereClause)throws Exception{
		List pls = null;
		String sql = "from PurchaseBill as so "
			+ pWhereClause + " order by so.makedate desc";
		pls = EntityManager.getAllByHql(sql);
		return pls;
	}
	

	public void updIsMakeTicket(String poid,int ismake)throws Exception{
		String sql="update purchase_bill set ismaketicket="+ismake+" where id='"+poid+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	public List getPurchaseProviderTotal(HttpServletRequest request, int pagesize, 
			String pWhereClause)throws Exception{
		String hql = "select pb.pid,pb.pname,sum(pb.totalsum) as totalsum " +
		 " from Purchase_Bill pb " + pWhereClause + 
		" group by pb.pid,pb.pname";
		return PageQuery.jdbcSqlserverQuery(request,"pid", hql, pagesize);
	}
	
	public List getPurchaseProviderTotal(String pWhereClause)throws Exception{
		String hql = "select pb.pid,pb.pname,sum(pb.totalsum) as totalsum " +
		 " from Purchase_Bill pb " + pWhereClause + 
		" group by pb.pid,pb.pname";
		
		return EntityManager.jdbcquery(hql);
	}
	
	public List getPurchaseProductTotal(HttpServletRequest request, int pagesize, 
			String pWhereClause)throws Exception{
		String hql = "select pd.productid, p.makeorganid, pd.productname, pd.specmode, pd.unitid, sum(pd.quantity) as quantity, sum(pd.subsum) as subsum " +
		 " from Purchase_Bill_Detail pd, Purchase_Bill p " + pWhereClause + 
		" group by  pd.productid,p.makeorganid,pd.productname, pd.specmode, pd.unitid ";
		return PageQuery.jdbcSqlserverQuery(request,"productid", hql, pagesize);
	}
	
	public List getPurchaseProductTotal(String pWhereClause)throws Exception{
		String hql = "select  pd.productid,p.makeorganid, pd.productname, pd.specmode, pd.unitid, sum(pd.quantity) as quantity, sum(pd.subsum) as subsum " +
		 " from Purchase_Bill_Detail pd, Purchase_Bill p " + pWhereClause + 
		" group by  pd.productid,p.makeorganid,pd.productname, pd.specmode, pd.unitid ";
		return EntityManager.jdbcquery(hql);
	}
	
	public List getTotalSum(String pWhereClause) throws Exception {
		String sql = "select sum(pd.quantity) as quantity, sum(pd.subsum) as subsum " +
			" from Purchase_Bill_Detail pd, Purchase_Bill p  " + pWhereClause ;
		return EntityManager.jdbcquery(sql);
	}

	public List<PurchaseBill> getPurchaseBill(String whereSql) {
		String hql = " from PurchaseBill as pb "
			+ whereSql + " order by pb.makedate desc ";
		return EntityManager.getAllByHql(hql);
	}

}
