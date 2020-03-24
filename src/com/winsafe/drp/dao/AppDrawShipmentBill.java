package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppDrawShipmentBill {


	public List getDrawShipmentBill(HttpServletRequest request,int pagesize, String pWhereClause) throws Exception {
		String sql = " select dsb from DrawShipmentBill as dsb,WarehouseVisit as wv "
				+ pWhereClause + " order by dsb.makedate desc ";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	
	public List getDrawShipmentBillObject(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = " from DrawShipmentBill as dsb "
				+ pWhereClause + " order by dsb.makedate desc ";
		return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}
	
	public double getTotalSum(String pWhereClause) throws Exception {
		String sql = "select sum(dsb.totalsum) from OtherShipmentBill as dsb "
				+ pWhereClause;
		return EntityManager.getdoubleSum(sql);
	}

	public void addDrawShipmentBill(Object shipmentbill) throws Exception {
		
		EntityManager.save(shipmentbill);
		
	}

	public void updDrawShipmentBill(Object shipmentbill) throws Exception {
		
		EntityManager.update(shipmentbill);
		
	}
	
	
	public void updTakeStatus(String id, int takestatus) throws Exception {
		
		String sql = "update draw_shipment_bill set takestatus="+takestatus+" where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public DrawShipmentBill getDrawShipmentBillByID(String id)
			throws Exception {
		DrawShipmentBill sb = null;
		String sql = " from DrawShipmentBill as sb where sb.id='" + id + "'";
		sb = (DrawShipmentBill) EntityManager.find(sql);
		return sb;
	}


	
	public void delDrawShipmentBill(String id)throws Exception{
		
		String sql="delete from draw_shipment_bill where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	

	public void updIsAudit(String ppid, int userid, Integer audit) throws Exception {		
		String sql = "update draw_shipment_bill set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public void blankout(String ppid, Long userid, Integer blankout) throws Exception {
		
		String sql = "update draw_shipment_bill set isblankout="+blankout+",blankoutid=" + userid
				+ ",blankoutdate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	
	public void updIsEndCase(String id, int userid, int audit)
			throws Exception {		
		String sql = "update draw_shipment_bill set isendcase=" + audit + ",endcaseid="
				+ userid + ",endcasedate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}

	public List<DrawShipmentBill> getDrawShipmentBill(String whereSql) {
		String sql = " select dsb from DrawShipmentBill as dsb,WarehouseVisit as wv "
			+ whereSql + " order by dsb.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}
	



}
