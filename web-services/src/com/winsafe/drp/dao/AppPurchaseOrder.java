package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppPurchaseOrder {


	public List searchPurchaseOrder(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pb = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "from PurchaseOrder as po "
				+ pWhereClause + " order by po.makedate desc ";
		pb = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pb;
	}
	

	
	public void addPurchaseOrder(PurchaseOrder po) throws Exception {
		
		EntityManager.save(po);
		
	}
	
	public void updPurchaseOrder(PurchaseOrder po) throws Exception {
		
		EntityManager.update(po);
		
	}

	
	public PurchaseOrder getPurchaseOrderByID(String id) throws Exception {
		PurchaseOrder pb = null;
		String sql = " from PurchaseOrder as po where po.id='" + id + "'";
		pb = (PurchaseOrder) EntityManager.find(sql);
		return pb;
	}

	
	public void updIsRefer(String id) throws Exception {
		
		String sql = "update purchase_order set isrefer=1 where id='" + id+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public void updIsChange(String id) throws Exception {
		
		String sql = "update purchase_order set ischange=1 where id='" + id+"'";
		EntityManager.updateOrdelete(sql);
		
	}

	
	public List waitApprovePurchaseOrder(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List wpa = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select po.id,po.pid,po.makeid,po.makedate,po.totalsum,po.receivedate,poa.actid,poa.approve,poa.id from PurchaseOrder as po,ApproveFlowLog as poa "
				+ pWhereClause
				+ " order by po.approvestatus, po.makedate desc";
		wpa = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return wpa;
	}

	
	public void updIsApprove(String id, int isapprove) throws Exception {
		String sql = "update purchase_order set approvestatus=" + isapprove
				+ ",approvedate='"+DateUtil.getCurrentDateString()+"' where id ='" + id+"'";
		EntityManager.updateOrdelete(sql);
	}

	public List getShouldPayment(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pb = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select po.id,po.pid,po.totalsum,po.createdate,po.PurchaseOrderstatus,po.ispaymentall from PurchaseOrder as po "
				+ pWhereClause + " order by po.createdate desc ";
		pb = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pb;
	}

	//
	public double getTotalPayment(String pWhereClause) throws Exception {
		double totalincome = Double.parseDouble("0.00");
		String sql = "select sum(po.totalsum) from PurchaseOrder as po "
				+ pWhereClause + " po.approvestatus =1";
		totalincome = EntityManager.getdoubleSum(sql);
		return totalincome;
	}

	

	public int waitInputPurchaseBillCount() throws Exception {
		int count = 0;
		String sql = "select count(*) from PurchaseOrder where approvestatus=2 and isendcase=0 and isblankout=0";
		count = EntityManager.getRecordCount(sql);
		return count;
	}
	
	public int waitInputPurchaseIncomeCount() throws Exception {
		int count = 0;
		String sql = "select count(*) from PurchaseOrder where approvestatus=2 and isendcase=0 and isblankout=0 and ischange=0";
		count = EntityManager.getRecordCount(sql);
		return count;
	}



	
	public void updIsPaymentAll(Long pbid) throws Exception {
		
		String sql = "update purchase_order set ispaymentall=1 where id=" + pbid;
		EntityManager.updateOrdelete(sql);
		
	}

	

	public void BlankoutPurchaseOrder(String id, Long userid)throws Exception{
		
		String sql="update purchase_order set isblankout=1,blankoutid="+userid
		+",blankoutdate='"+DateUtil.getCurrentDateString()+"' where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	
	public void delPurchaseOrder(String id) throws Exception{
		
		String sql="delete from purchase_order where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	

	

	public void updIsEndCase(String id, Integer userid,int endcase) throws Exception {
		
		String sql = "update purchase_order set isendcase="+endcase+",endcaseid=" + userid
				+ ",endcasedate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	

	public List getPurchaseOrderTotal(String pWhereClause)throws Exception{
		List pls = null;
		String sql = "from PurchaseOrder as po "
			+ pWhereClause + " order by po.makedate desc";
		pls = EntityManager.getAllByHql(sql);
		return pls;
	}

}
