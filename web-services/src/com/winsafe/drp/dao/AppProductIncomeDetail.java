package com.winsafe.drp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.drp.dao.ProductIncomeDetail;

public class AppProductIncomeDetail {

	public void addProductIncomeDetail(Object pid) throws Exception {
		EntityManager.save3(pid);
	}

	public List<ProductIncomeDetail> getProductIncomeDetailByPbId(String piid)
			throws Exception {
		String sql = "from ProductIncomeDetail as d where d.piid='" + piid
				+ "'";
		return EntityManager.getAllByHql(sql);
	}

	public ProductIncomeDetail getProductIncomeDetailByID(int id)
			throws Exception {
		String sql = "from ProductIncomeDetail as d where d.id=" + id;
		return (ProductIncomeDetail) EntityManager.find(sql);
	}

	public List getProductIncomeDetail(String pWhereClause) throws Exception {
		String sql = "select pi, pid from ProductIncome as pi ,ProductIncomeDetail as pid,Product as p "
				+ pWhereClause + " order by pid.productid, pi.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}

	// 此处PbID和PiID无区别
	public void delProductIncomeDetailByPbID(String piid) throws Exception {
		String sql = "delete from product_income_detail where piid='" + piid
				+ "'";
		EntityManager.updateOrdelete(sql);
	}

	public void delProductIncomeDetailByNCcode(String nccode) throws Exception {
		String sql = "delete from product_income_detail where nccode='"
				+ nccode + "'";
		EntityManager.updateOrdelete(sql);
	}

	public List getProductIncomeDetailByPiidPid(String piid, String productid)
			throws Exception {
		String sql = " from ProductIncomeDetail where piid='" + piid
				+ "' and productid='" + productid + "'";
		return EntityManager.getAllByHql(sql);
	}
	
	public ProductIncomeDetail getPIDetailByPiidPid(String piid, String productid,String batch)
	throws Exception {
String sql = " from ProductIncomeDetail where piid='" + piid
		+ "' and productid='" + productid + "' and batch='"+batch+"'";
return (ProductIncomeDetail)EntityManager.find(sql);
}

	public List getPerDayIncomeReport(String whereSql, int pagesize,
			SimplePageInfo tmpPgInfo) throws Exception {
		String sql = "select pid.productid,sum(pid.quantity) from ProductIncomeDetail as pid, ProductIncome as pi "
				+ whereSql
				+ " group by pid.productid order by sum(pid.quantity) desc ";

		return EntityManager.getAllByHql(sql, tmpPgInfo.getCurrentPageNo(),
				pagesize);
	}

	public List getIncomeCountReport(String pWhereClause) throws Exception {
		List ls = new ArrayList();
		String hql = "select pid.productid,sum(pid.quantity) from Product_Income_Detail as pid, Product_Income as pi "
				+ pWhereClause
				+ " group by pid.productid order by sum(pid.quantity) desc";
		ResultSet rs = EntityManager.query(hql);
		SaleReportForm srf = null;
		do {
			srf = new SaleReportForm();
			srf.setProductid(rs.getString(1));
			srf.setCount(rs.getInt(2));
			ls.add(srf);
		} while (rs.next());
		rs.close();
		return ls;
	}

	public List getDetailReport(HttpServletRequest request, int pagesize,
			String whereSql) throws Exception {
		String sql = "select pw.id, pw.warehouseid,pw.incomedate,pwd.productid,pwd.productname,pwd.batch,pwd.nccode,specmode,unitid,"
				+ "quantity,factquantity,costprice,costsum from Product_Income as pw ,Product_Income_Detail as pwd "
				+ whereSql;
		return PageQuery.jdbcSqlserverQuery(request, sql, pagesize);
	}

	public List getTotalSubum(String whereSql) throws HibernateException,
			SQLException {
		String sql = "select sum(pwd.quantity) as quantity,sum(costsum) as costsum from Product_Income as pw ,Product_Income_Detail as pwd "
				+ whereSql;
		return EntityManager.jdbcquery(sql);
	}

	public List getDetailReport(String whereSql) throws HibernateException,
			SQLException {
		String sql = "select pw.id, pw.warehouseid,pw.incomedate,pwd.productid,pwd.productname,specmode,unitid,"
				+ "quantity,factquantity,costprice,costsum from Product_Income as pw ,Product_Income_Detail as pwd "
				+ whereSql;
		return EntityManager.jdbcquery(sql);
	}

	public List getTotalReport(HttpServletRequest request, int pagesize,
			String whereSql) throws Exception {
		String hql = "select pwd.productid, pwd.productname, pwd.specmode, pwd.nccode,pwd.unitid, sum(pwd.quantity) as quantity, sum(pwd.costsum) as costsum "
				+ "from Product_Income_Detail pwd, Product_Income pw "
				+ whereSql
				+ " group by pwd.productid,pwd.productname, pwd.specmode, pwd.unitid, pwd.nccode";
		return PageQuery
				.jdbcSqlserverQuery(request, "productid", hql, pagesize);
	}

	public List getTotalReport(String whereSql) throws Exception {
		String hql = "select pwd.productid, pwd.productname, pwd.specmode, pwd.unitid, sum(pwd.quantity) as quantity, sum(pwd.costsum) as costsum "
				+ "from Product_Income_Detail pwd, Product_Income pw "
				+ whereSql
				+ " group by pwd.productid,pwd.productname, pwd.specmode, pwd.unitid";
		return EntityManager.jdbcquery(hql);
	}

	public void updProductIncomeDetail(ProductIncomeDetail pid)
			throws Exception {
		EntityManager.update2(pid);
	}
}
