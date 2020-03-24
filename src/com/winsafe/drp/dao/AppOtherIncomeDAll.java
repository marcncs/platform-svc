package com.winsafe.drp.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;


public class AppOtherIncomeDAll {

  public void addOtherIncomeDetail(Object pid)throws Exception{    
    EntityManager.save(pid);    
  }
  public void updOtherIncomeDetail(Object pid)throws Exception{    
	    EntityManager.update(pid);    
	  }

  public List getOtherIncomeDetailByOiid(String oiid)throws Exception{
    String sql=" from OtherIncomeDetail as d where d.oiid='"+oiid+"'";
    return EntityManager.getAllByHql(sql);
  }
  
  public OtherIncomeDetail getOtherIncomeDetailById(int id)throws Exception{	  
	    String sql="from OtherIncomeDetail where id="+id+"";
	    return (OtherIncomeDetail)EntityManager.find(sql);	   
	  }
  
  public OtherIncomeDetail getOtherIncomeDetailByOiidPid(String oiid, String productid, String batch)throws Exception{	  
	    String sql="from OtherIncomeDetail where oiid='"+oiid+"' and productid='"+productid+"' and batch='"+batch+"'";
	    return (OtherIncomeDetail)EntityManager.find(sql);	   
  }
  
  public void delOtherIncomeDetailByPbID(String oiid)throws Exception{    
    String sql="delete from other_income_detail where oiid='"+oiid+"'";
    EntityManager.updateOrdelete(sql);    
   }
  
  public void delOtherIncomeDetailByNCcode(String nccode)throws Exception{    
	    String sql="delete from other_income_detail where Nccode='"+nccode+"'";
	    EntityManager.updateOrdelete(sql);    
	   }
  
  public List getOtherIncomeDetail(String pWhereClause) throws Exception {
		String sql = "select oi, oid from OtherIncome as oi ,OtherIncomeDetail as oid,Product as p "
				+ pWhereClause + " order by oid.productid, oi.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}
  
  public List getOtherIncomeDetailByPiidPid(String piid, String batch, String productid)
	throws Exception {
		String sql = " from OtherIncomeDetail where oiid='" + piid
				+ "' and batch='"+batch+"' and productid='" + productid + "'";
		return EntityManager.getAllByHql(sql);
	}
  

	public List getPerDayIncomeReport(String whereSql, int pagesize,
			SimplePageInfo tmpPgInfo) throws Exception {
		List ls = null;
		String sql = "select pid.productid,pid.unitid,sum(pid.quantity),sum(pid.subsum) from ProductIncomeDetail as pid, ProductIncome as pi "
				+ whereSql + " group by pid.productid order by sum(pid.subsum) desc ";

		ls = EntityManager.getAllByHql(sql,tmpPgInfo.getCurrentPageNo(),pagesize);
		return ls;
	}
	

	public List getPerDayOtherIncomeReport(String whereSql, int pagesize,
			SimplePageInfo tmpPgInfo) throws Exception {
		List ls = null;
		String sql = "select oid.productid,sum(oid.quantity),sum(oid.subsum) from OtherIncomeDetail as oid, OtherIncome as oi "
				+ whereSql + " group by oid.productid order by sum(oid.quantity) desc ";

		ls = EntityManager.getAllByHql(sql,tmpPgInfo.getCurrentPageNo(),pagesize);
		return ls;
	}


	public List getOtherIncomeCountReport(String pWhereClause) throws Exception {
		List ls = new ArrayList();
		String hql = "select oid.productid,sum(oid.quantity) from Other_Income_Detail as oid, Other_Income as oi "
				+ pWhereClause + " group by oid.productid order by sum(oid.quantity) desc";
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
	
	public List getOtherIncomeDetail(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select oid from OtherIncome as oi ,OtherIncomeDetail as oid "
				+ pWhereClause + " order by oid.productid, oi.makedate desc ";
		return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}
	

	
	public List getDetailReport(HttpServletRequest request,int pagesize, String pWhereClause) throws Exception {
		String hql = "select pw,pwd from OtherIncome as pw ,OtherIncomeDetail as pwd "
				+ pWhereClause + " order by pwd.productid, pw.makedate desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List getDetailReport(String pWhereClause) throws Exception {
		String sql = "select pw,pwd from OtherIncome as pw ,OtherIncomeDetail as pwd "
				+ pWhereClause + " order by pwd.productid, pw.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}
	
	
	public List getTotalReport(HttpServletRequest request, int pagesize, 
			String pWhereClause) throws Exception {
		String hql = "select pw.makeorganid, pwd.productid, pwd.productname, pwd.specmode, pwd.unitid, sum(pwd.quantity) as quantity "+
		"from Other_Income_Detail pwd, Other_Income pw "+ pWhereClause +
		" group by pw.makeorganid, pwd.productid,pwd.productname, pwd.specmode, pwd.unitid ";
		return PageQuery.jdbcSqlserverQuery(request, "productid",hql, pagesize);
	}
	
	public List getTotalReport(
			String pWhereClause) throws Exception {
		String hql = "select pw.makeorganid,pwd.productid, pwd.productname, pwd.specmode, pwd.unitid, sum(pwd.quantity) as quantity "+
		"from OtherIncomeDetail pwd, OtherIncome pw "+ pWhereClause +
		" group by pw.makeorganid,pwd.productid,pwd.productname, pwd.specmode, pwd.unitid order by pwd.productid";
		
		return EntityManager.getAllByHql(hql);
	}
	
	public double getTotalSubum(String whereSql)throws Exception{		    
	    String sql="select sum(pwd.quantity) from OtherIncome as pw ,OtherIncomeDetail as pwd "+whereSql;
	    return EntityManager.getdoubleSum(sql);
	}



}
