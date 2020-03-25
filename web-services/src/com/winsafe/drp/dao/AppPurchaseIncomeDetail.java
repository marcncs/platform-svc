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

public class AppPurchaseIncomeDetail {

	public void addPurchaseIncomeDetail(Object pid) throws Exception {
		EntityManager.save(pid);
	}

	public List getPurchaseIncomeDetailByPiid(String piid) throws Exception {
		String sql = " from PurchaseIncomeDetail as d where d.piid='" + piid
				+ "'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getPurchaseIncomeDetailByNccode(String nccode) throws Exception {
		String sql = " from PurchaseIncomeDetail as d where d.Nccode='" + nccode
				+ "'";
		return EntityManager.getAllByHql(sql);
	}

	public List getPurchaseIncomeDetailByPoid(String poid) throws Exception {
		String sql = "select d from PurchaseIncomeDetail as d,  PurchaseIncome as p where d.piid=p.id and p.poid='"
				+ poid + "' and p.incomesort=0 ";
		return EntityManager.getAllByHql(sql);
	}

	public List<PurchaseIncomeDetail> getPurchaseIncomeDetailByPbId2(String piid)
			throws Exception {
		String sql = "from PurchaseIncomeDetail where piid='" + piid + "'";
		return EntityManager.getAllByHql(sql);
	}

	public PurchaseIncomeDetail getPurchaseIncomeDetailByID(int id)
			throws Exception {
		String sql = " from PurchaseIncomeDetail where id =" + id;
		return (PurchaseIncomeDetail) EntityManager.find(sql);
	}

	public void delPurchaseIncomeDetailByPiID(String piid) throws Exception {
		String sql = "delete from purchase_income_detail where piid='" + piid
				+ "'";
		EntityManager.updateOrdelete(sql);
	}

	public void delPurchaseIncomeDetailByNCcode(String nccode) throws Exception {
		String sql = "delete from purchase_income_detail where Nccode='"
				+ nccode + "'";
		EntityManager.updateOrdelete(sql);
	}

	public List getPurchaseIncomeDetailByPiidPid(String piid, String productid)
			throws Exception {
		String sql = " from PurchaseIncomeDetail where piid='" + piid
				+ "' and productid='" + productid + "'";
		return EntityManager.getAllByHql(sql);
	}

	public List getPurchaseIncomeDetail(String pWhereClause) throws Exception {
		String sql = "select pi, pid from PurchaseIncome as pi ,PurchaseIncomeDetail as pid,Product as p "
				+ pWhereClause + " order by pid.productid, pi.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}

	public List getPerDayIncomeReport(String whereSql, int pagesize,
			SimplePageInfo tmpPgInfo) throws Exception {
		String sql = "select pid.productid,sum(pid.quantity),sum(pid.subsum) from PurchaseIncomeDetail as pid, PurchaseIncome as pi "
				+ whereSql
				+ " group by pid.productid order by sum(pid.subsum) desc ";
		return EntityManager.getAllByHql(sql, tmpPgInfo.getCurrentPageNo(),
				pagesize);
	}

	public void alreadySettlement(Long id, Long settlementid) throws Exception {
		String sql = "update purchase_income_detail set issettlement=1,settlementid="
				+ settlementid + " where id=" + id;
		EntityManager.updateOrdelete(sql);

	}

	public void delSettlementReturn(Long settlementid) throws Exception {

		String sql = "update purchase_income_detail set issettlement =0 where settlementid="
				+ settlementid;
		EntityManager.updateOrdelete(sql);

	}

	public List getPurchaseIncomeCountReport(String pWhereClause)
			throws Exception {
		List ls = new ArrayList();
		String hql = "select pid.productid,sum(pid.quantity) from Purchase_Income_Detail as pid, Purchase_Income as pi "
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
		// System.out.println("ls====="+ls.size());
		return ls;
	}

	public List getPurchaseIncomeSumReport(String pWhereClause)
			throws Exception {
		List ls = new ArrayList();
		String hql = "select pid.productid,sum(pid.subsum) from Purchase_Income_Detail as pid, Purchase_Income as pi "
				+ pWhereClause
				+ " group by pid.productid order by sum(pid.subsum) desc";
		System.out.println("----" + hql);
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

	public List getPurchaseIncomeDetail(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select pid from PurchaseIncome as pi ,PurchaseIncomeDetail as pid "
				+ pWhereClause + " order by pid.productid, pi.makedate desc ";
		return EntityManager.getAllByHql(sql, targetPage, pagesize);
	}

	public List getDetailReport(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String sql = "select pw.provideid, pw.providename,pw.warehouseid,pw.makedate,pwd from PurchaseIncome as pw ,PurchaseIncomeDetail as pwd "
				+ pWhereClause + " order by pwd.productid, pw.makedate desc ";
		System.out.println(sql);
		return PageQuery.hbmQuery(request, sql, pagesize);
	}

	public List getDetailReport(String pWhereClause) throws Exception {
		String sql = "select pw.provideid, pw.providename,pw.warehouseid,pw.makedate,pwd from PurchaseIncome as pw ,PurchaseIncomeDetail as pwd "
				+ pWhereClause + " order by pwd.productid, pw.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}

	public List getTotalProductReport(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String hql = "select pd.productid, pd.productname, pd.specmode, pd.unitid, sum(pd.quantity) as quantity,sum(factquantity) as factquantity, sum(pd.subsum) as subsum "
				+ " from Purchase_Income_Detail pd, Purchase_Income p "
				+ pWhereClause
				+ " group by pd.productid,pd.productname, pd.specmode, pd.unitid order by pd.productid";
		return PageQuery
				.jdbcSqlserverQuery(request, "productid", hql, pagesize);
	}

	public List getTotalProductReport(String pWhereClause) throws Exception {
		String hql = "select pd.productid, pd.productname, pd.specmode, pd.unitid, sum(pd.quantity) as quantity,sum(factquantity) as factquantity ,sum(pd.subsum) as subsum "
				+ " from Purchase_Income_Detail pd, Purchase_Income pw "
				+ pWhereClause
				+ " group by pd.productid,pd.productname, pd.specmode, pd.unitid order by pd.productid";
		return EntityManager.jdbcquery(hql);
	}

	public List getTotalProviderReport(HttpServletRequest request,
			int pagesize, String pWhereClause) throws Exception {
		String hql = "select provideid,providename,sum(totalsum) as totalsum from Purchase_Income "
				+ pWhereClause + " group by provideid,providename";
		return PageQuery
				.jdbcSqlserverQuery(request, "provideid", hql, pagesize);
	}

	public List getTotalProviderReport(String whereSql)
			throws HibernateException, SQLException {
		String hql = "select provideid,providename,sum(totalsum) as totalsum from Purchase_Income "
				+ whereSql + " group by provideid,providename";
		return EntityManager.jdbcquery(hql);
	}

	public double getTotalSum(String whereSql) throws Exception {
		String hql = "select sum(totalsum) as totalsum from PurchaseIncome "
				+ whereSql;
		return EntityManager.getdoubleSum(hql);
	}

	public List getTotalSubum(String whereSql) throws Exception {
		String sql = "select sum(pwd.quantity), sum(pwd.subsum) from PurchaseIncome as pw ,PurchaseIncomeDetail as pwd "
				+ whereSql;
		return EntityManager.getAllByHql(sql);
	}

	
	 
}
