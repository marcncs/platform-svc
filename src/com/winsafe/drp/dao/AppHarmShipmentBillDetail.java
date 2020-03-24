package com.winsafe.drp.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;


public class AppHarmShipmentBillDetail {
    
    public void addHarmShipmentBillDetail(Object spb)throws Exception{
      
      EntityManager.save(spb);
      
  }

  

public List getHarmShipmentBillDetailBySbID(String hsid)throws Exception{
  List spb = null;
  String sql=" from HarmShipmentBillDetail as hsbd where hsbd.hsid='"+hsid+"'";
  spb = EntityManager.getAllByHql(sql);
  return spb;
}

public List getHarmShipmentBillDetailBySbID(String whereSql,int pagesize, SimplePageInfo tmpPgInfo)throws Exception{
	  List spb = null;
	  String sql="select hsbd.id,hsbd.hsid,hsbd.productid,hsbd.unitid,hsbd.unitprice,hsbd.quantity,hsbd.subsum from HarmShipmentBillDetail" +
	  		" as hsbd ,HarmShipmentBill as hsb "+whereSql+" order by hsbd.id";
	  spb = EntityManager.getAllByHql(sql, tmpPgInfo.getCurrentPageNo(), pagesize);
	  return spb;
	}


public void delHarmShipmentBillDetailBySbID(String hsid)throws Exception{
  
  String sql="delete from harm_shipment_bill_detail where hsid='"+hsid+"'";
  EntityManager.updateOrdelete(sql);
  
  }



public List getPerDayHarmShipmentReport(String whereSql, int pagesize,
		SimplePageInfo tmpPgInfo) throws Exception {
	List ls = null;
	String sql = "select hsbd.productid,sum(hsbd.quantity),sum(hsbd.subsum) from HarmShipmentBillDetail as hsbd, HarmShipmentBill as hsb "
			+ whereSql + " group by hsbd.productid order by sum(hsbd.quantity) desc ";

	ls = EntityManager.getAllByHql(sql,tmpPgInfo.getCurrentPageNo(),pagesize);
	return ls;
}


public List getHarmShipmentCountReport(String pWhereClause) throws Exception {
	List ls = new ArrayList();
	String hql = "select hsbd.productid,sum(hsbd.quantity) from Harm_Shipment_Bill_Detail as hsbd, Harm_Shipment_Bill as hsb "
			+ pWhereClause + " group by hsbd.productid order by sum(hsbd.quantity) desc";
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


public void updTakeQuantity(String soid,String productid, String batch, double takequantity)throws Exception{
	
	String sql="update Harm_Shipment_Bill_Detail set takequantity=takequantity +"+takequantity+" where hsid='"+soid+"' and batch='"+batch+"' and productid='"+productid+"'";
	EntityManager.updateOrdelete(sql);
	
}
	
	public List getHarmShipmentBillDetail(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select hsbd from HarmShipmentBill as hsb ,HarmShipmentBillDetail as hsbd "
				+ pWhereClause + " order by hsbd.productid, hsb.makedate desc ";
		return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}
	
	
	public List getDetailReport(HttpServletRequest request,int pagesize, String pWhereClause) throws Exception {
		String sql = "select pw.warehouseid,pw.makedate,pwd from HarmShipmentBill as pw ,HarmShipmentBillDetail as pwd "
				+ pWhereClause + " order by pwd.productid, pw.makedate desc ";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	
	public List getDetailReport(String pWhereClause) throws Exception {
		String sql = "select pw.warehouseid,pw.makedate,pwd from HarmShipmentBill as pw ,HarmShipmentBillDetail as pwd "
				+ pWhereClause + " order by pwd.productid, pw.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}
	
	
	
	public List getTotalReport(HttpServletRequest request, int pagesize, 
			String pWhereClause) throws Exception {
		String hql = "select pwd.productid, pwd.productname, pwd.specmode, pwd.unitid, sum(pwd.quantity) as quantity, sum(pwd.subsum) as subsum "+
		"from HarmShipmentBillDetail pwd, HarmShipmentBill pw "+ pWhereClause +
		" group by pwd.productid,pwd.productname, pwd.specmode, pwd.unitid order by pwd.productid";
		String sql= hql.replace("HarmShipmentBillDetail", "harm_shipment_bill_detail");
		sql = sql.replace("HarmShipmentBill", "harm_shipment_bill");
		
		Object obj[] = DbUtil.setGroupByPager(request, sql, pagesize, "HarmShipmentBillTotalReport");
		SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
		int targetPage = tmpPgInfo.getCurrentPageNo();
		return EntityManager.getAllByHql(hql, targetPage, pagesize);
	}
	
	public List getTotalReport(String pWhereClause) throws Exception {
		String hql = "select pwd.productid, pwd.productname, pwd.specmode, pwd.unitid, sum(pwd.quantity) as quantity, sum(pwd.subsum) as subsum "+
		"from HarmShipmentBillDetail pwd, HarmShipmentBill pw "+ pWhereClause +
		" group by pwd.productid,pwd.productname, pwd.specmode, pwd.unitid order by pwd.productid";
		
		return EntityManager.getAllByHql(hql);
	}
	
	public double getTotalSubum(String whereSql)throws Exception{		    
	    String sql="select sum(pwd.quantity) from HarmShipmentBill as pw ,HarmShipmentBillDetail as pwd "+whereSql;
	    return EntityManager.getdoubleSum(sql); 
	}





}
