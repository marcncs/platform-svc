package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppAmountInventoryDetail {
	
	
	/**
	 * 根据id获得条码盘点细节
	 * @author jason.huang
	 * @param osid
	 * @return
	 * @throws Exception
	 */

	public List getAmountInventoryDetailByID(String osid) throws Exception {
		String sql = "  from AmountInventoryDetail  bid where bid.osid='"
				+ osid + "'";
		return  EntityManager.getAllByHql(sql);
	}
	
	public AmountInventoryDetail getAmountInventoryDetailById(String osid) throws Exception {
		String sql = " from AmountInventoryDetail bid where bid.osid='"+ osid + "'";
		return (AmountInventoryDetail) EntityManager.find(sql);
	}

	
	/**
	 * 统计各类产品的数量
	 * @author jason.huang
	 * @param osid
	 * @return
	 * @throws Exception
	 */

	public List groupAmountInventoryDetailByID(HttpServletRequest request,String osid) throws Exception {
		String sql = " select bid.productid ID,bid.productid,bid.productname,bid.specmode,bid.unitid,bid.\"BATCH\",sum(quantity) quantity from Amount_inventory_detail  bid where bid.osid='"
				+ osid + "'" + " group by productid,productname,specmode,unitid,\"BATCH\"  ";
		return PageQuery.jdbcSqlserverQuery(request, sql);
	}

	/**
	 * 新增Amountinventorydetail
	 * 
	 * @author jason.huang
	 * @param spb
	 * @throws Exception
	 */

	public void addAmountInventoryDetail(Object spb) throws Exception {
		EntityManager.save(spb);
	}
	
	
	/**
	 * 获取货物数量
	 * @author jason.huang
	 */

	public List getStockpile(HttpServletRequest request, String warehouseid,String productid,String batch) throws Exception{
		String sql = " select ps.id,stockpile from Product_Stockpile_All  ps, Product  p,Product_Struct pstr where  p.id=ps.productid and pstr.structcode=p.psid and productid = '"+productid+"' and   batch='"+batch+"' and warehouseid = '"+warehouseid+"' "; 
		return  PageQuery.jdbcSqlserverQuery(request, sql);
	}
	
	/**
	 * 根据warehouseid获取所有产品情况
	 * @author jason.huang
	 */
	public List getAllAmountDetailByW(HttpServletRequest request, String pWhereClause) throws Exception {
		String hql = "select ps, p,pstr.sortname from ProductStockpileAll as ps, Product as p,ProductStruct pstr  " + pWhereClause;
		return PageQuery.hbmQuery(request, hql);
	}
	
	
	/**
	 * 根据warehouseid获取产品数量
	 * @author jason.huang
	 * 
	 */
	public List getNumByProductid(HttpServletRequest request,String productid,String osid,String batch) throws Exception {
		String sql = " select bid.productid ID,sum(quantity) quantity from Amount_inventory_detail  bid where bid.productid='"+productid+"' and osid='"+osid+"' and batch='"+batch+"' group by productid ";
		return PageQuery.jdbcSqlserverQuery(request, sql);
	}

}
