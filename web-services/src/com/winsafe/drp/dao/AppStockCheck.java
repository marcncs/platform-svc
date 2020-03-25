package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppStockCheck {

	public void addStockCheck(StockCheck sc) throws Exception {		
		EntityManager.save(sc);		
	}
	
	public void upStockCheck(StockCheck sc) throws Exception {		
		EntityManager.save(sc);		
	}

	public List getStockCheck(HttpServletRequest request, int pagesize, String whereSql) throws Exception {
		String hql = " from StockCheck  "
				+ whereSql + " order by makedate desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public StockCheck getStockCheckByID(String id) throws Exception {
		String sql = " from StockCheck as sc where sc.id='" + id+"'";
		return (StockCheck) EntityManager.find(sql);
	}
	
	public void delStockCheck(String id)throws Exception{		
		String sql="delete from stock_check where id='"+id+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	
	
	public void updStockCheckIsComplete(String id,int userid)throws Exception{		
		String sql="update stock_check set iscreate=1 where id='"+id+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	

	public void updIsAudit(String ppid, int userid,Integer audit) throws Exception {		
		String sql = "update stock_check set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateTime()
				+ "' where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);
		
	}

	public List<StockCheck> getStockCheck(String whereSql) {
		String hql = " from StockCheck  "
			+ whereSql + " order by makedate desc ";
		return EntityManager.getAllByHql(hql);
	}

	
}
