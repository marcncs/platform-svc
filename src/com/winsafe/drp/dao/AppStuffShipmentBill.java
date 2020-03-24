package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppStuffShipmentBill {


	public List getStuffShipmentBill(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List sb = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select ssb.id,ssb.warehouseid,ssb.shipmentsort,ssb.shipmentdept,ssb.requiredate,ssb.totalsum,ssb.remark,ssb.isrefer,ssb.isaudit,ssb.makeid,ssb.makedate from StuffShipmentBill as ssb,WarehouseVisit as wv "
				+ pWhereClause + " order by ssb.makedate desc ";
		sb = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return sb;
	}

	public void addStuffShipmentBill(Object shipmentbill) throws Exception {
		
		EntityManager.save(shipmentbill);
		
	}

	public StuffShipmentBill getStuffShipmentBillByID(String id)
			throws Exception {
		StuffShipmentBill sb = null;
		String sql = " from StuffShipmentBill as sb where sb.id='" + id + "'";
		sb = (StuffShipmentBill) EntityManager.find(sql);
		return sb;
	}

	
	public void updStuffShipmentBillByID(StuffShipmentBill sb,
			String requiredate) throws Exception {
		
		String sql = "update stuff_shipment_bill set warehouseid="
				+ sb.getWarehouseid() + ",shipmentsort=" + sb.getShipmentsort()
				+ ",shipmentdept=" + sb.getShipmentdept() + ",requiredate='"
				+ requiredate + "',totalsum="+sb.getTotalsum()+",remark='" + sb.getRemark() + "' where id='" + sb.getId() + "'";
		EntityManager.updateOrdelete(sql);
		
	}

	public void updIsRefer(String id) throws Exception {
		
		String sql = "update stuff_shipment_bill set isrefer=1 where id='" + id
				+ "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void delStuffShipmentBill(String id)throws Exception{
		
		String sql="delete from stuff_shipment_bill where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
		
	}

	
	public List waitStuffShipmentBill(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List sb = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select sb.id,sb.warehouseid,sb.receiveid,sb.receiveaddr,sb.shipmentdate from ShipmentBill as sb "
				+ pWhereClause + " order by sb.shipmentdate ";
		sb = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return sb;
	}

	

	public List waitApproveStuffShipmentBill(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List asb = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select ssb.id,ssb.warehouseid,ssb.shipmentsort,ssb.shipmentdept,ssb.requiredate,ssb.remark,ssb.makeid,ssba.actid,ssba.approve from StuffShipmentBill as ssb,StuffShipmentBillApprove as ssba "
				+ pWhereClause
				+ " order by ssba.approve, ssb.requiredate desc ";
		asb = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return asb;
	}

	

	public void updIsApprove(String id, int isapprove) throws Exception {
		String sql = "update stuff_shipment_bill set approvestatus="
				+ isapprove + " where id ='" + id + "'";
		EntityManager.updateOrdelete(sql);
	}

	
	public void updIsShipment(String id, Long shipmentid) throws Exception {
		
		String sql = "update stuff_shipment_bill set isshipment=1,shipmentid="
				+ shipmentid + ",shipmentdate='"
				+ DateUtil.getCurrentDateTime() + "' where id='" + id + "'";

		EntityManager.updateOrdelete(sql);

		
	}

	
	public List getSendGoodsBillDetail(Long sbid) throws Exception {
		List ls = null;
		String sql = "select spb.id,p.productname,spb.unitid,spb.quantity,spb.unitprice,spb.subsum from StuffShipmentProductBill as spb ,Product as p where p.id=spb.productid and spb.sbid="
				+ sbid + "";
		ls = EntityManager.getAllByHql(sql);
		return ls;
	}
	

	public void updIsAudit(String sbid, Long userid,Integer audit) throws Exception {
		
		String sql = "update stuff_shipment_bill set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + sbid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List getStuffShipementBillTotal(String pWhereClause)throws Exception{
		List pls = null;
		String sql = "from StuffShipmentBill as so "
			+ pWhereClause + " order by so.makedate desc";
		pls = EntityManager.getAllByHql(sql);
		return pls;
	}

}
