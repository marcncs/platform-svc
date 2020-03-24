package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil; 
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppOtherShipmentBillAll {

	
	public List getOtherShipmentBill(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = " from OtherShipmentBillAll "
				+ pWhereClause + " order by makedate desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List getOtherShipmentBillList(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String sql = "select osba.* from Other_Shipment_Bill_All osba " +
				"join organ o on o.id = osba.ORGANID "
				+ pWhereClause;
		if(pagesize > 0) {
			return PageQuery.jdbcSqlserverQuery(request, "makedate desc", sql, pagesize);
		} else {
			sql+=" order by osba.makedate desc ";
			return EntityManager.jdbcquery(sql);
		}
		
	} 

	public void addOtherShipmentBill(Object shipmentbill) throws Exception {		
		EntityManager.save(shipmentbill);		
	}

	public OtherShipmentBillAll getOtherShipmentBillByID(String id)
			throws Exception {
		String sql = " from OtherShipmentBillAll as sb where sb.id='" + id + "'";
		return (OtherShipmentBillAll) EntityManager.find(sql);
	}
	
	public void updTakeStatus(String id, int takestatus) throws Exception {		
		String sql = "update other_shipment_ball_all set takestaus="+takestatus+" where id='" + id + "'";
		EntityManager.updateOrdelete(sql);		
	}

	public void updOtherShipmentBill(OtherShipmentBillAll sb) throws Exception {				
		EntityManager.update(sb);		
	}
	
	public void delOtherShipmentBill(String id)throws Exception{		
		String sql="delete from other_shipment_bill_all where id='"+id+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	

	public void updIsAudit(String ppid, int userid,int audit) throws Exception {		
		String sql = "update other_shipment_bill_all set isaudit="+audit+",auditid=" + userid
				+ ",auditdate=to_date('" + DateUtil.getCurrentDateTime()
				+ "','yyyy-mm-dd hh24:mi:ss') where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void updIsNotAudit(String ppid) throws Exception {
		String sql = "update other_shipment_bill_all set isaudit=0,auditid=null" +
				",auditdate=null where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);

	}
	
	
	public void blankout(String ppid, Long userid, Integer blankout) throws Exception {		
		String sql = "update other_shipment_bill_all set isblankout="+blankout+",blankoutid=" + userid
				+ ",blankoutdate='" + DateUtil.getCurrentDateTime()
				+ "' where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);		
	}
	
	
	public List getOtherShipementBillTotal(String pWhereClause)throws Exception{
		String sql = "from OtherShipmentBillAll as so "
			+ pWhereClause + " order by so.makedate desc";
		return EntityManager.getAllByHql(sql);
	}
	
	
	public void updIsEndCase(String id, Long userid, Integer audit)
			throws Exception {
		
		String sql = "update other_shipment_bill_all set isendcase=" + audit + ",endcaseid="
				+ userid + ",endcasedate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public double getTotalSum(String pWhereClause) throws Exception {
		String sql = "select sum(osb.totalsum) from OtherShipmentBillAll as osb "
				+ pWhereClause;
		return EntityManager.getdoubleSum(sql);
	}

	public List<OtherShipmentBillAll> getOtherShipmentBill(String whereSql) {
		String hql = " from OtherShipmentBillAll "
			+ whereSql + " order by makedate desc ";
		return EntityManager.getAllByHql(hql);
	}
}
