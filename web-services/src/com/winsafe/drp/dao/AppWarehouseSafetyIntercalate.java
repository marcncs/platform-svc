package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppWarehouseSafetyIntercalate {
	public List getWarehouseSafetyIntercalate(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select ws.id,ws.warehouseid,ws.productid,ws.safetyh,ws.safetyl from WarehouseSafetyIntercalate as ws "
				+ pWhereClause + " order by ws.id desc ";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	
	
	public List getWarehouseSafetyByWID(Long wid)throws Exception{
		List sls = null;
		String sql="select ws.id,ws.productid,ws.safetyh,ws.safetyl from WarehouseSafetyIntercalate as ws where ws.warehouseid="+wid;
		sls = EntityManager.getAllByHql(sql);
		return sls;
	}
	
	public void addWarehouseSafetyIntercalate(Object ws)throws Exception{
		
		EntityManager.save(ws);
		
	}
	
	public WarehouseSafetyIntercalate getWarehouseSafetyIntercalateByID(Long id)throws Exception{
		WarehouseSafetyIntercalate ws = new WarehouseSafetyIntercalate();
		String sql=" from WarehouseSafetyIntercalate as ws where ws.id ="+id;
		ws = (WarehouseSafetyIntercalate)EntityManager.find(sql);
		return ws;
	}
	
	public void updWarehouseSafetyIntercalate(WarehouseSafetyIntercalate ws)throws Exception{

		String sql = "update warehouse_safety_intercalate set productid='"+ws.getProductid()+"',safetyh="+ws.getSafetyh()+",safetyl="+ws.getSafetyl()+" where id="+ws.getId();
		EntityManager.updateOrdelete(sql);

	}
	
	public void delWarehouseSafetyIntercalate(Long id)throws Exception{
		
		String sql="delete from warehouse_safety_intercalate where id="+id;
		EntityManager.updateOrdelete(sql);
		
	}
}
