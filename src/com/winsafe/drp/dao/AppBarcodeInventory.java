package com.winsafe.drp.dao;

import java.util.List;
import java.util.Map; 

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppBarcodeInventory {
	private static Logger logger = Logger.getLogger(AppBarcodeInventory.class);
	/**
	 * 新增barcodeinventory
	 * 
	 * @author jason.huang
	 * @param spb
	 * @throws Exception
	 */

	public void addBarcodeInventory(Object spb) throws Exception {
		EntityManager.save(spb);
	}
	
	/**
	 * 更新barcodeinventory
	 * 
	 * @author jason.huang
	 * @param spb
	 * @throws Exception
	 */

	public void updBarcodeInventory(Object spb) throws Exception {
		EntityManager.update(spb);
	}

	/**
	 * 获取barcodeinventory所有行
	 * 
	 * @author jason.huang
	 * @param request
	 * @param pagesize
	 * @param pWhereClause
	 * @return
	 * @throws Exception
	 */
	public List getBarcodeInventory(HttpServletRequest request, int pagesize, String pWhereClause)
			throws Exception {
		String hql = " from BarcodeInventory " + pWhereClause + " order by makedate desc ";
		logger.debug("hql:"+hql);
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List getAmountInventoryList(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String sql = "select bi.* from Barcode_Inventory bi " +
				"join organ o on o.id = bi.ORGANID "
				+ pWhereClause;
		return PageQuery.jdbcSqlserverQuery(request, "makedate desc", sql, pagesize);
	} 
	
	public List getAmountInventoryList(HttpServletRequest request, int pagesize, String pWhereClause, Map<String, Object> param) throws Exception {
		String sql = "select bi.* from Barcode_Inventory bi " +
				"join organ o on o.id = bi.ORGANID "
				+ pWhereClause;
		return PageQuery.jdbcSqlserverQuery(request, "makedate desc", sql, pagesize, param);
	} 

	/**
	 * getBarcodeInventoryByID
	 * 
	 * @author jason.huang
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BarcodeInventory getBarcodeInventoryByID(String id) throws Exception {
		String sql = " from BarcodeInventory  as bi where bi.id='" + id + "'";
		return (BarcodeInventory) EntityManager.find(sql);
	}

	/**
	 * 更新复核状态
	 * 
	 * @author jason.huang
	 * @param ppid
	 * @param userid
	 * @param audit
	 * @throws Exception
	 */
	public void updIsAudit(String ppid, int userid, int audit) throws Exception {
		String sql = "update barcode_inventory set isaudit=" + audit + ",auditid=" + userid
				+ ",auditdate=to_date('" + DateUtil.getCurrentDateTime()
				+ "','yyyy-mm-dd hh24:mi:ss') where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);

	}

	/**
	 * 取消复核
	 * 
	 * @author jason.huang
	 * @param ppid
	 * @throws Exception
	 */

	public void updIsNotAudit(String ppid) throws Exception {
		String sql = "update barcode_inventory set isaudit=0,auditid=null"
				+ ",auditdate=null where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);

	}

}
