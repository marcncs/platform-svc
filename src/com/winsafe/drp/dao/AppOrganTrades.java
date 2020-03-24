package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

/**
 * @author : jerry
 * @version : 2009-09-03 上午09:26:02
 * www.winsafe.cn
 */
public class AppOrganTrades {

	
	public List<OrganTrades> getOrganTradesAll(HttpServletRequest request,Integer pageSize, String whereSql)throws Exception{
		String hql =" from OrganTrades as ot " +whereSql+" order by ot.id desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}
	
	public OrganTrades getOrganTradesByID(String id)throws Exception{
		String hql = " from OrganTrades ot where ot.id='" + id+"'";
		return (OrganTrades) EntityManager.find(hql);
	}
	public OrganTrades getOrganTradesByIDII(String idii)throws Exception{
		String hql = " from OrganTrades ot where ot.idii='" + idii+"'";
		return (OrganTrades) EntityManager.find(hql);
	}
	public void save(OrganTrades organTrades)throws Exception{
		EntityManager.save(organTrades);
	}
	public void update(OrganTrades organTrades)throws Exception{
		EntityManager.update(organTrades);
	}
	public void delete(OrganTrades organTrades)throws Exception{
		EntityManager.delete(organTrades);
	}

	public List getOrganTradesDetail(HttpServletRequest request, int pagesize,
			String whereSql) throws Exception {
		String sql = "select ot.id,ot.porganid,ot.porganname,ot.inwarehouseid,ot.makedate,ot.makeorganid,ot.outwarehouseid,otd.productid,otd.productname,otd.specmode,otd.unitid,otd.quantity from Organ_Trades as ot ,Organ_Trades_Detail as otd "
			+ whereSql + "";
		return PageQuery.jdbcSqlserverQuery(request, sql, pagesize);
	}

	public Double getTotalSubum(String whereSql) {
		String sql = "select sum(otd.quantity) as quantity from OrganTrades as ot ,OrganTradesDetail as otd "
			+ whereSql + "";
		return EntityManager.getdoubleSum(sql);
	}

	public List getOrganTradesDetail(String whereSql) throws HibernateException, SQLException {
		String sql = "select ot.id,ot.porganid,ot.porganname,ot.inwarehouseid,ot.makedate,ot.makeorganid,ot.outwarehouseid,otd.productid,otd.productname,otd.specmode,otd.unitid,otd.quantity from Organ_Trades as ot ,Organ_Trades_Detail as otd "
			+ whereSql + "";
		return EntityManager.jdbcquery(sql);
	}

	public List getOrganTradesOrganTotal(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String sql = "select ot.porganid,ot.makeorganid,otd.productid,otd.productname,otd.specmode,otd.unitid,sum(otd.quantity) as quantity from Organ_Trades as ot ,Organ_Trades_Detail as otd "
			+ whereSql + " group by ot.porganid,ot.makeorganid,otd.productid,otd.productname,otd.specmode,otd.unitid";
		return PageQuery.jdbcSqlserverQuery(request,"porganid", sql, pagesize);
	}
	
	public List getOrganTradesOrganTotal(String whereSql) throws Exception {
		String sql = "select ot.porganid,ot.makeorganid,otd.productid,otd.productname,otd.specmode,otd.unitid,sum(otd.quantity) as quantity from Organ_Trades as ot ,Organ_Trades_Detail as otd "
			+ whereSql + " group by ot.porganid,ot.makeorganid,otd.productid,otd.productname,otd.specmode,otd.unitid";
		return EntityManager.jdbcquery(sql);
	}

	public List getOrganTradesProductTotal(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String sql = "select ot.makeorganid,productid,productname,specmode,unitid,sum(quantity) as quantity from Organ_Trades as ot ,Organ_Trades_Detail as otd "
			+ whereSql + " group by ot.makeorganid,productid,productname,specmode,unitid";
		return PageQuery.jdbcSqlserverQuery(request,"productid", sql, pagesize);
	}
	
	public List getOrganTradesProductTotal(String whereSql) throws Exception {
		String sql = "select ot.makeorganid,productid,productname,specmode,unitid,sum(quantity) as quantity from Organ_Trades as ot ,Organ_Trades_Detail as otd "
			+ whereSql + " group by ot.makeorganid,productid,productname,specmode,unitid";
		return EntityManager.jdbcquery(sql);
	}

	public List getOrganTradesBillTotal(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String sql = "select ot.id,ot.porganid,ot.makeorganid,ot.makedate,sum(otd.quantity) as quantity from Organ_Trades as ot ,Organ_Trades_Detail as otd "
			+ whereSql + " group by ot.id,ot.porganid,ot.makeorganid,ot.makedate";
		return PageQuery.jdbcSqlserverQuery(request, sql, pagesize);
	}

	public List getOrganTradesBillTotal(String whereSql) throws HibernateException, SQLException {
		String sql = "select ot.id,ot.porganid,ot.makeorganid,ot.makedate,sum(otd.quantity) as quantity from Organ_Trades as ot ,Organ_Trades_Detail as otd "
			+ whereSql + " group by ot.id,ot.porganid,ot.makeorganid,ot.makedate";
		return EntityManager.jdbcquery(sql);
	}

	public List<OrganTrades> getOrganTradesAll(String whereSql) {
		String hql =" from OrganTrades as ot " +whereSql+" order by ot.id desc";
		return EntityManager.getAllByHql(hql);
	}
	
}
