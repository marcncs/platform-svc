package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppHarmShipmentBill {


	public List getHarmShipmentBill(HttpServletRequest request,int pagesize, String pWhereClause) throws Exception {
		String sql = "select hsb from HarmShipmentBill as hsb,WarehouseVisit as wv "
				+ pWhereClause + " order by hsb.makedate desc ";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	
	public List getHarmShipmentBillObject(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "from HarmShipmentBill as hsb "
				+ pWhereClause + " order by hsb.makedate desc ";
		return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}
	
	public double getTotalSum(String pWhereClause) throws Exception {
		String sql = "select sum(hsb.totalsum) from HarmShipmentBill as hsb "
				+ pWhereClause;
		return EntityManager.getdoubleSum(sql);
	}

	public void addHarmShipmentBill(HarmShipmentBill h) throws Exception {
		
		EntityManager.save(h);
		
	}
	
	public void updHarmShipmentBill(HarmShipmentBill h) throws Exception {
		
		EntityManager.update(h);
		
	}
	
	
	public void updTakeStatus(String id, int takestatus) throws Exception {
		
		String sql = "update harm_shipment_bill set takestatus="+takestatus+" where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}

	public HarmShipmentBill getHarmShipmentBillByID(String id)
			throws Exception {
		String sql = " from HarmShipmentBill as hsb where hsb.id='" + id + "'";
		return (HarmShipmentBill) EntityManager.find(sql);
	}

	
	public void delHarmShipmentBill(String id)throws Exception{
		
		String sql="delete from harm_shipment_bill where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	

	public void updIsAudit(String ppid, int userid,int audit) throws Exception {
		
		String sql = "update harm_shipment_bill set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public void blankout(String ppid, int userid,Integer blankout) throws Exception {
		
		String sql = "update harm_shipment_bill set isblankout="+blankout+",blankoutid=" + userid
				+ ",blankoutdate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public void updIsEndCase(String id, int userid, Integer audit)
			throws Exception {
		
		String sql = "update harm_shipment_bill set isendcase=" + audit + ",endcaseid="
				+ userid + ",endcasedate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}

	public List<HarmShipmentBill> getHarmShipmentBill(String whereSql) {
		String sql = "select hsb from HarmShipmentBill as hsb,WarehouseVisit as wv "
			+ whereSql + " order by hsb.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}




}
