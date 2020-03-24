package com.winsafe.drp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppStockPileAgeing {
	

	
	public List getStockPileAgeing(HttpServletRequest request, String pWhereClause) throws Exception {
		String hql = " from StockPileAgeing  "+ pWhereClause + " order by id ";
		return PageQuery.hbmQuery(request, hql);
	}
	
	public void saveStockPileAgeing(StockPileAgeing spa) throws Exception{
		EntityManager.save(spa);
	}
	
	public void updStockPileAgeing(StockPileAgeing spa) throws Exception{
		EntityManager.update(spa);
	}
	
	public void delStockPileAgeing(StockPileAgeing spa) throws Exception{
		EntityManager.delete(spa);
	}
	
	public void delStockPileAgeingById(Integer id) throws Exception{
		String sql="delete from StockPile_Ageing where id = " +id;
		EntityManager.updateOrdelete(sql);
	}
	
	public StockPileAgeing getStockPileAgeingById(Integer id) throws Exception{
		String hql = " from StockPileAgeing where id = " + id;
		return (StockPileAgeing)EntityManager.find(hql);
	}
	
	public List getStockPileAgeing() throws Exception{
		String hql="from StockPileAgeing order by id ";
		return EntityManager.getAllByHql(hql);
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<String, StockPileAgeing> getAgeingMap() throws Exception{
		Map<String, StockPileAgeing> map = new HashMap<String, StockPileAgeing>();
		List<StockPileAgeing> list = getStockPileAgeing();
		for (StockPileAgeing spa : list) {
			map.put(spa.getTagColor(), spa);
		}
		return map;
	} 
	
}
