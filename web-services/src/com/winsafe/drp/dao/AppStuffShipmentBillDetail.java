package com.winsafe.drp.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;


public class AppStuffShipmentBillDetail {
    
    public void addStuffShipmentBillDetail(Object spb)throws Exception{
      
      EntityManager.save(spb);
      
  }

  

public List getStuffShipmentBillDetailBySbID(String ssid)throws Exception{
  List spb = null;
  String sql="select sbd.id,sbd.ssid,sbd.productid,sbd.productname,sbd.specmode,sbd.unitid,sbd.batch,sbd.unitprice,sbd.quantity,sbd.subsum from StuffShipmentBillDetail as sbd where sbd.ssid='"+ssid+"'";
  spb = EntityManager.getAllByHql(sql);
  return spb;
}

public List getStuffShipmentBillDetailBySbID(String whereSql,int pagesize, SimplePageInfo tmpPgInfo)throws Exception{
	  List spb = null;
	  String sql="select spb.id,spb.sbid,spb.productid,spb.unitid,spb.unitprice,spb.quantity,spb.subsum from StuffShipmentBillDetail" +
	  		" as spb ,StuffShipmentBill as sp "+whereSql+" order by spb.id";
	  spb = EntityManager.getAllByHql(sql, tmpPgInfo.getCurrentPageNo(), pagesize);
	  return spb;
	}


public void delStuffShipmentBillDetailBySbID(String ssid)throws Exception{
  
  String sql="delete from stuff_shipment_bill_detail where ssid='"+ssid+"'";
  EntityManager.updateOrdelete(sql);
  
  }



public List getPerDayStuffShipmentReport(String whereSql, int pagesize,
		SimplePageInfo tmpPgInfo) throws Exception {
	List ls = null;
	String sql = "select ssbd.productid,sum(ssbd.quantity),sum(ssbd.subsum) from StuffShipmentBillDetail as ssbd, StuffShipmentBill as ssb "
			+ whereSql + " group by ssbd.productid order by sum(ssbd.quantity) desc ";

	ls = EntityManager.getAllByHql(sql,tmpPgInfo.getCurrentPageNo(),pagesize);
	return ls;
}


public List getStuffShipmentCountReport(String pWhereClause) throws Exception {
	List ls = new ArrayList();
	String hql = "select ssbd.productid,sum(ssbd.quantity) from Stuff_Shipment_Bill_Detail as ssbd, Stuff_Shipment_Bill as ssb "
			+ pWhereClause + " group by ssbd.productid order by sum(ssbd.quantity) desc";
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
