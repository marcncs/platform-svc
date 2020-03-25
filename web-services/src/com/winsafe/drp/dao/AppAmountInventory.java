package com.winsafe.drp.dao;

import java.util.List;
import java.util.Map; 

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppAmountInventory { 
	/**
	 * 新增AmountInventory
	 * @author jason.huang
	 * @param spb
	 * @throws Exception
	 */

	public void addAmountInventory(Object spb) throws Exception {
		EntityManager.save(spb);
	}
	
	/**
	 * 更新AmountInventory
	 * @author jason.huang
	 * @param spb
	 * @throws Exception
	 */

	public void updAmountInventory(Object spb) throws Exception {
		EntityManager.update(spb);
	}

	/**
	 * 获取AmountInventory所有行
	 * @author jason.huang
	 * @param request
	 * @param pagesize
	 * @param pWhereClause
	 * @return
	 * @throws Exception
	 */
	public List getAmountInventory(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String hql = " from AmountInventory " + pWhereClause
				+ " order by makedate desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List getAmountInventoryList(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String sql = "select ai.* from Amount_Inventory ai " +
				"join organ o on o.id = ai.ORGANID "
				+ pWhereClause;
		return PageQuery.jdbcSqlserverQuery(request, "makedate desc", sql, pagesize);
	} 
	
	public List getAmountInventoryList(HttpServletRequest request, int pagesize, String pWhereClause, Map<String, Object> param) throws Exception {
		String sql = "select ai.* from Amount_Inventory ai " +
				"join organ o on o.id = ai.ORGANID "
				+ pWhereClause;
		return PageQuery.jdbcSqlserverQuery(request, "makedate desc", sql, pagesize, param);
	} 

	/**
	 * getAmountInventoryByID
	 * @author jason.huang
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public AmountInventory getAmountInventoryByID(String id)
			throws Exception {
		String sql = " from AmountInventory  as bi where bi.id='" + id
				+ "'";
		return (AmountInventory) EntityManager.find(sql);
	}
	
	/**
	 * 更新复核状态
	 * @author jason.huang
	 * @param ppid
	 * @param userid
	 * @param audit
	 * @throws Exception
	 */
	public void updIsAudit(String ppid, int userid,int audit) throws Exception {		
		String sql = "update barcode_inventory set isaudit="+audit+",auditid=" + userid
				+ ",auditdate=to_date('" + DateUtil.getCurrentDateTime()
				+ "','yyyy-mm-dd hh24:mi:ss') where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	/**
	 * 取消复核
	 * @author jason.huang
	 * @param ppid
	 * @throws Exception
	 */
	
	public void updIsNotAudit(String ppid) throws Exception {
		String sql = "update barcode_inventory set isaudit=0,auditid=null" +
				",auditdate=null where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);

	}
	public void updateIsAudit(String id, int userid,int audit) throws Exception {		
		String sql = "update Amount_Inventory set isaudit="+audit+",auditid=" + userid
				+ ",auditdate=to_date('" + DateUtil.getCurrentDateTime()
				+ "','yyyy-mm-dd hh24:mi:ss') where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}
}
