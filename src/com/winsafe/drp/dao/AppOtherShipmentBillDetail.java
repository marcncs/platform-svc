package com.winsafe.drp.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;


public class AppOtherShipmentBillDetail {
   
	public void addOtherShipmentBillDetail(Object spb)throws Exception{      
      EntityManager.save(spb);      
    }
    
    public void updOtherShipmentBillDetail(Object spb)throws Exception{      
        EntityManager.update(spb);      
      }


	public List getOtherShipmentBillDetailBySbID(String osid)throws Exception{
	  String sql=" from OtherShipmentBillDetail as sbd where sbd.osid='"+osid+"'";
	  return EntityManager.getAllByHql(sql);
	}
	
	public OtherShipmentBillDetail getOtherShipmentBillDetailByID(int id)throws Exception{
		  String sql=" from OtherShipmentBillDetail  where id="+id+"";
		  return (OtherShipmentBillDetail)EntityManager.find(sql);
	}
	
	public List getOtherShipmentBillDetailByTtidPid(String ttid, String productid)throws Exception{
		String sql=" from OtherShipmentBillDetail where osid='"+ttid+"' and productid='"+productid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	 public OtherShipmentBillDetail getOtherShipmentBillDetailByOsidPid(String osid, String productid, String batch)throws Exception{	  
		    String sql="from OtherShipmentBillDetail where osid='"+osid+"' and productid='"+productid+"' and batch='"+batch+"'";
		    return (OtherShipmentBillDetail)EntityManager.find(sql);	   
	  }
	 
	 public List getOtherShipmentBillDetail(String pWhereClause) throws Exception {
			String sql = "select osb, osbd from OtherShipmentBill as osb ,OtherShipmentBillDetail as osbd,Product as p "
					+ pWhereClause + " order by osbd.productid, osb.makedate desc ";
			return EntityManager.getAllByHql(sql);
		}

	 public List getOtherShipmentBillDetailBySbID(String whereSql,int pagesize, SimplePageInfo tmpPgInfo)throws Exception{
	  String sql="select spb.id,spb.osid,spb.productid,spb.unitid,spb.unitprice,spb.quantity,spb.subsum from OtherShipmentBillDetail" +
	  		" as spb ,OtherShipmentBill as sp "+whereSql+" order by spb.id";
	  return EntityManager.getAllByHql(sql, tmpPgInfo.getCurrentPageNo(), pagesize);
	}

public void delOtherShipmentBillDetailBySbID(String osid)throws Exception{  
  String sql="delete from other_shipment_bill_detail where osid='"+osid+"'";
  EntityManager.updateOrdelete(sql);  
  }



public List getPerDayOtherShipmentReport(String whereSql, int pagesize,
		SimplePageInfo tmpPgInfo) throws Exception {
	String sql = "select osbd.productid,sum(osbd.quantity),sum(osbd.subsum) from OtherShipmentBillDetail as osbd, OtherShipmentBill as osb "
			+ whereSql + " group by osbd.productid order by sum(osbd.quantity) desc ";

	return EntityManager.getAllByHql(sql,tmpPgInfo.getCurrentPageNo(),pagesize);
}


public List getOtherShipmentCountReport(String pWhereClause) throws Exception {
	List ls = new ArrayList();
	String hql = "select osbd.productid,sum(osbd.quantity) from Other_Shipment_Bill_Detail as osbd, Other_Shipment_Bill as osb "
			+ pWhereClause + " group by osbd.productid order by sum(osbd.quantity) desc";
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
		String sql="update other_shipment_bill_detail set takequantity=takequantity +"+takequantity+" where osid='"+soid+"' and batch='"+batch+"' and productid='"+productid+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public List getDetailReport(HttpServletRequest request,int pagesize, String pWhereClause) throws Exception {
		String sql = "select pw,pwd from OtherShipmentBill as pw ,OtherShipmentBillDetail as pwd "
				+ pWhereClause + " order by pwd.productid, pw.makedate desc ";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	
	public List getDetailReport(String pWhereClause) throws Exception {
		String sql = "select pw,pwd from OtherShipmentBill as pw ,OtherShipmentBillDetail as pwd "
				+ pWhereClause + " order by pwd.productid, pw.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}
	
	
	public List getTotalReport(HttpServletRequest request, int pagesize, 
			String pWhereClause) throws Exception {
		String hql = "select pw.makeorganid, pwd.productid, pwd.productname, pwd.specmode, pwd.unitid, sum(pwd.quantity) as quantity, sum(pwd.subsum) as subsum "+
		"from Other_Shipment_Bill_Detail pwd, Other_Shipment_Bill pw "+ pWhereClause +
		" group by pw.makeorganid,pwd.productid,pwd.productname, pwd.specmode, pwd.unitid ";
		return PageQuery.jdbcSqlserverQuery(request, "productid",hql, pagesize);
	}
	
	public List getTotalReport(
			String pWhereClause) throws Exception {
		String hql = "select pw.makeorganid, pwd.productid, pwd.productname, pwd.specmode, pwd.unitid, sum(pwd.quantity) as quantity, sum(pwd.subsum) as subsum "+
		"from OtherShipmentBillDetail pwd, OtherShipmentBill pw "+ pWhereClause +
		" group by pw.makeorganid, pwd.productid,pwd.productname, pwd.specmode, pwd.unitid order by pwd.productid";
		
		return EntityManager.getAllByHql(hql);
	}
	
	public double getTotalSubum(String whereSql)throws Exception{		    
	    String sql="select sum(pwd.quantity) from OtherShipmentBill as pw ,OtherShipmentBillDetail as pwd "+whereSql;
	    return EntityManager.getdoubleSum(sql);
	}





}
