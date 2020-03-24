package com.winsafe.drp.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;


public class AppShipmentBillDetail {
    public void addShipmentBillDetail(Object spb)throws Exception{      
      EntityManager.save(spb);      
    }
    
    public void delShipmentProductBillBySbID(String sbid)throws Exception{  
	    String sql="delete from shipment_bill_detail where sbid='"+sbid+"'";
	    EntityManager.updateOrdelete(sql);
    }

	public List<ShipmentBillDetail> getShipmentBillDetailBySbID(String sbid)throws Exception{
	  String sql=" from ShipmentBillDetail as sbd where sbd.sbid='"+sbid+"'";
	  return EntityManager.getAllByHql(sql);
	}


	public List getShipmentProductBillBySbID(String whereSql,int pagesize, SimplePageInfo tmpPgInfo)throws Exception{
	  String sql="select spb.id,spb.sbid,spb.productid,spb.unitid,spb.unitprice,spb.quantity,spb.subsum from ShipmentProductBill" +
	  		" as spb ,ShipmentBill as sp "+whereSql+" order by spb.id";
	  return EntityManager.getAllByHql(sql, tmpPgInfo.getCurrentPageNo(), pagesize);
	}

	
	public List getPerDayShipmentReport(String whereSql, int pagesize,
			SimplePageInfo tmpPgInfo) throws Exception {
		List ls = null;
		String sql = "select sbd.productid,sum(sbd.quantity),sum(sbd.subsum) from ShipmentBillDetail as sbd, ShipmentBill as sb "
				+ whereSql + " group by sbd.productid order by sum(sbd.quantity) desc ";
	
		ls = EntityManager.getAllByHql(sql,tmpPgInfo.getCurrentPageNo(),pagesize);
		return ls;
	}

	
	public List getShipmentCountReport(String pWhereClause) throws Exception {
		List ls = new ArrayList();
		String hql = "select sbd.productid,sum(sbd.quantity) from Shipment_Bill_Detail as sbd, Shipment_Bill as sb "
				+ pWhereClause + " group by sbd.productid order by sum(sbd.quantity) desc";
		ResultSet rs = EntityManager.query(hql);
		SaleReportForm srf = null;
		do {
			srf = new SaleReportForm();
			srf.setProductid(rs.getString(1));
			srf.setCount(rs.getInt(2));
			ls.add(srf);
		} while (rs.next());
		rs.close();
		// System.out.println("ls====="+ls.size());
		return ls;
	}

	
	public List getShipmentSumReport(String pWhereClause) throws Exception {
		List ls = new ArrayList();
		String hql = "select spb.productid,sum(spb.subsum) from Shipment_Product_Bill as spb, Shipment_Bill as sb "
				+ pWhereClause + " group by spb.productid order by sum(spb.subsum) desc";
		ResultSet rs = EntityManager.query(hql);
		SaleReportForm srf = null;
		do {
			srf = new SaleReportForm();
			srf.setProductid(rs.getString(1));
			srf.setCount(rs.getInt(2));
			ls.add(srf);
		} while (rs.next());
		rs.close();
		// System.out.println("ls====="+ls.size());
		return ls;
	}
}
