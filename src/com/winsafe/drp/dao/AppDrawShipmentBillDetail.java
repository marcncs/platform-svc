package com.winsafe.drp.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppDrawShipmentBillDetail {
	
	public void addDrawShipmentBillDetail(Object spb)throws Exception{      
	      EntityManager.save(spb);      
	    }
	    
	    public void updDrawShipmentBillDetail(Object spb)throws Exception{      
	        EntityManager.update(spb);      
	      }


		public List getDrawShipmentBillDetailByDsid(String dsid)throws Exception{
		  String sql=" from DrawShipmentBillDetail as sbd where sbd.dsid='"+dsid+"'";
		  return EntityManager.getAllByHql(sql);
		}
		
		public DrawShipmentBillDetail getDrawShipmentBillDetailByID(int id)throws Exception{
			  String sql=" from DrawShipmentBillDetail  where id="+id+"";
			  return (DrawShipmentBillDetail)EntityManager.find(sql);
		}
		
		public List getDrawShipmentBillDetailByTtidPid(String ttid, String productid)throws Exception{
			String sql=" from DrawShipmentBillDetail where dsid='"+ttid+"' and productid='"+productid+"'";
			return EntityManager.getAllByHql(sql);
		}
		
		 public DrawShipmentBillDetail getDrawShipmentBillDetailByDsidPid(String dsid, String productid, String batch)throws Exception{	  
			    String sql="from DrawShipmentBillDetail where dsid='"+dsid+"' and productid='"+productid+"' and batch='"+batch+"'";
			    return (DrawShipmentBillDetail)EntityManager.find(sql);	   
		  }
		 
		 public List getDrawShipmentBillDetail(String pWhereClause) throws Exception {
				String sql = "select dsb, dsbd from DrawShipmentBill as dsb ,DrawShipmentBillDetail as dsbd,Product as p "
						+ pWhereClause + " order by dsbd.productid, dsb.makedate desc ";
				return EntityManager.getAllByHql(sql);
			}

		 public List getDrawShipmentBillDetailBySbID(String whereSql,int pagesize, SimplePageInfo tmpPgInfo)throws Exception{
		  String sql="select spb.id,spb.dsid,spb.productid,spb.unitid,spb.unitprice,spb.quantity,spb.subsum from DrawShipmentBillDetail" +
		  		" as spb ,DrawShipmentBill as sp "+whereSql+" order by spb.id";
		  return EntityManager.getAllByHql(sql, tmpPgInfo.getCurrentPageNo(), pagesize);
		}

	public void delDrawShipmentBillDetailByDsid(String dsid)throws Exception{  
	  String sql="from DrawShipmentBillDetail where dsid='"+dsid+"'";
	  
	  EntityManager.deleteByHql(sql);  
	  }



	public List getPerDayOtherShipmentReport(String whereSql, int pagesize,
			SimplePageInfo tmpPgInfo) throws Exception {
		String sql = "select dsbd.productid,sum(dsbd.quantity),sum(dsbd.subsum) from DrawShipmentBillDetail as dsbd, DrawShipmentBill as dsb "
				+ whereSql + " group by dsbd.productid order by sum(dsbd.quantity) desc ";

		return EntityManager.getAllByHql(sql,tmpPgInfo.getCurrentPageNo(),pagesize);
	}


	public List getOtherShipmentCountReport(String pWhereClause) throws Exception {
		List ls = new ArrayList();
		String hql = "select dsbd.productid,sum(dsbd.quantity) from DrawShipmentBill_Detail as dsbd, DrawShipmentBill as dsb "
				+ pWhereClause + " group by odsbd.productid order by sum(dsbd.quantity) desc";
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
		
		
		public void updTakeQuantity(String dsid,String productid, String batch, double takequantity)throws Exception{		
			String sql="update Draw_ShipmentBill_detail set takequantity=takequantity +"+takequantity+" where dsid='"+dsid+"' and batch='"+batch+"' and productid='"+productid+"'";
			EntityManager.updateOrdelete(sql);		
		}
		
		public List getDetailReport(HttpServletRequest request,int pagesize, String pWhereClause) throws Exception {
			String sql = "select pw,pwd from DrawShipmentBill as pw ,DrawShipmentBillDetail as pwd "
					+ pWhereClause + " order by pwd.productid, pw.makedate desc ";
			return PageQuery.hbmQuery(request, sql, pagesize);
		}
		
		public List getDetailReport(String pWhereClause) throws Exception {
			String sql = "select pw,pwd from DrawShipmentBill as pw ,DrawShipmentBillDetail as pwd "
					+ pWhereClause + " order by pwd.productid, pw.makedate desc ";
			return EntityManager.getAllByHql(sql);
		}
		
		
		public List getTotalReport(HttpServletRequest request, int pagesize, 
				String pWhereClause) throws Exception {
			String hql = "select pw.makeorganid, pwd.productid, pwd.productname, pwd.specmode, pwd.unitid, sum(pwd.quantity) as quantity, sum(pwd.subsum) as subsum "+
			"from DrawShipmentBill_Detail pwd, DrawShipmentBill pw "+ pWhereClause +
			" group by pw.makeorganid,pwd.productid,pwd.productname, pwd.specmode, pwd.unitid ";
			return PageQuery.jdbcSqlserverQuery(request, "productid",hql, pagesize);
		}
		
		public List getTotalReport(
				String pWhereClause) throws Exception {
			String hql = "select pw.makeorganid, pwd.productid, pwd.productname, pwd.specmode, pwd.unitid, sum(pwd.quantity) as quantity, sum(pwd.subsum) as subsum "+
			"from DrawShipmentBillDetail pwd, DrawShipmentBill pw "+ pWhereClause +
			" group by pw.makeorganid, pwd.productid,pwd.productname, pwd.specmode, pwd.unitid order by pwd.productid";
			
			return EntityManager.getAllByHql(hql);
		}
		
		public double getTotalSubum(String whereSql)throws Exception{		    
		    String sql="select sum(pwd.quantity) from DrawShipmentBill as pw ,DrawShipmentBillDetail as pwd "+whereSql;
		    return EntityManager.getdoubleSum(sql);
		}
}
