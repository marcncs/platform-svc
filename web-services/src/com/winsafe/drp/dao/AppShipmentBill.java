package com.winsafe.drp.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppShipmentBill {



	public List getShipmentBill(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List sb = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select sb.id,sb.warehouseid,sb.receiveid,sb.linkman,sb.tel,sb.transportmode,sb.requiredate,sb.receiveaddr,sb.isaudit from ShipmentBill as sb,WarehouseVisit as wv "
				+ pWhereClause + " order by sb.makedate desc ";
		sb = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return sb;
	}
	
	
	
	public List searchShipmentBill(HttpServletRequest request,int pagesize, String pWhereClause) throws Exception {
		String hql = " from ShipmentBill as sb "
				+ pWhereClause + " order by sb.makedate desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	
	public List getShipmentBillNew(String ids) throws Exception {		 
		String sql = " from ShipmentBill as sb where id in ("
				+ ids +  ") and istrans=0 and isblankout=0 order by sb.makedate desc ";
		//System.out.println("the sql is:=================:" + sql);
		return EntityManager.getAllByHql(sql);
	}
	
	

	public List getShipmentBillTotal(String pWhereClause)throws Exception{
		String sql = "select sb.id,sb.cid,sb.makedate from ShipmentBill as sb "
			+ pWhereClause + " order by sb.makedate desc";
		return EntityManager.getAllByHql(sql);
	}

	public void addShipmentBill(Object shipmentbill) throws Exception {		
		EntityManager.save(shipmentbill);		
	}
	
	public void updShipmentBill(Object shipmentbill) throws Exception {		
		EntityManager.update(shipmentbill);		
	}
	
	public void delShipmentBill(String id)throws Exception{		
		String sql="delete from shipment_bill where id='"+id+"'";
		EntityManager.updateOrdelete(sql);		
	}

	public ShipmentBill getShipmentBillByID(String id) throws Exception {
		String sql = " from ShipmentBill as sb where sb.id='" + id + "'";
		return (ShipmentBill) EntityManager.find(sql);
	}

	
	public void updIsTrans(int istrans, String id) throws Exception {		
		String sql = "update shipment_bill set istrans="+istrans+" where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void updIsTransByBids(String bids) throws Exception {		
		String sql = "update shipment_bill set istrans=1 where id in(" + bids + ")";
		EntityManager.updateOrdelete(sql);		
	}

	
	public List waitShipmentBill(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select sb.id,sb.warehouseid,sb.receiveid,sb.receiveaddr,sb.shipmentdate from ShipmentBill as sb "
				+ pWhereClause + " order by sb.shipmentdate ";
		return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}

	
	
	public void updIsAudit(String sbid, int userid,Integer audit) throws Exception {		
		String sql = "update shipment_bill set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateTime()
				+ "' where id='" + sbid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	

	
	public void blankoutShipmentBill(String id, int userid, String blankoutreason) throws Exception {		
		String sql = "update shipment_bill set isblankout=1,blankoutid=" + userid
				+ ",blankoutdate='" + DateUtil.getCurrentDateTime()
				+ "',blankoutreason='"+blankoutreason+"' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void blankoutTakeBill(String id, int userid, String blankoutreason) throws Exception {		
		String sql = "update Take_Bill set isblankout=1,blankoutid=" + userid
				+ ",blankoutdate='" + DateUtil.getCurrentDateTime()
				+ "',blankoutreason='"+blankoutreason+"' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void blankoutTakeTicket(String id, int userid, String blankoutreason) throws Exception {		
		String sql = "update take_ticket set isblankout=1,blankoutid=" + userid
				+ ",blankoutdate='" + DateUtil.getCurrentDateTime()
				+ "',blankoutreason='"+blankoutreason+"' where billno='" + id + "'";
		EntityManager.updateOrdelete(sql);		
	}
	
	
	public void blankout(int bsort, String id, int userid, String blankoutreason) throws Exception {		
		blankoutShipmentBill(id, userid, blankoutreason);
		blankoutTakeBill(id, userid, blankoutreason);
		blankoutTakeTicket(id, userid, blankoutreason);
		String table = Constants.TT_MAIN_TABLE[bsort];		
		String sql = "update "+table+" set isblankout=1,blankoutid=" + userid
				+ ",blankoutdate='" + DateUtil.getCurrentDateTime()
				+ "',blankoutreason='"+blankoutreason+"' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public boolean isComplete(int bsort, String id) throws Exception {		
		String table = Constants.TT_MAIN_TABLE[bsort];		
		String sql = " select iscomplete from "+table+"  where id='" + id + "'";
		List list =EntityManager.jdbcquery(sql);
		if ( list != null && !list.isEmpty() ){
			Map map = (Map)list.get(0);
			if ( map != null ){
				if ( "1".equals(map.get("iscomplete").toString()) ){
					return true;
				}
			}
		}
		return false;
	}


	public List<ShipmentBill> searchShipmentBill(String whereSql) {
		String hql = " from ShipmentBill as sb "
			+ whereSql + " order by sb.makedate desc ";
		return EntityManager.getAllByHql(hql);
	}

}
