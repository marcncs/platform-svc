package com.winsafe.drp.server;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Scanner;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.dao.WarehouseBit;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.cache.WarehouseCache;
import com.winsafe.hbm.util.pager2.PageQuery;

public class WarehouseService {
	private WarehouseCache cache = new WarehouseCache();
	private AppWarehouse appo = new AppWarehouse();
	
	public List getWarehouse(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception{
		return appo.getWarehouse(request, pagesize, pWhereClause);
	}
	
	public void addWarehouse(Warehouse d) throws Exception {		
		appo.addWarehouse(d);		
		modifyCache();
	}

	public void updWarehouse(Warehouse o)throws Exception {		
		appo.updWarehouse(o);
		modifyCache();
	}
	
	public void delWarehouse(String id) throws Exception {
		appo.delWarehouse(id);
		modifyCache();
	}
	
	public List getAllWarehousebean()throws Exception{
		List list = cache.getAllWarehouse();
		if ( list == null ){
			list = appo.getAllWarehousebean();
			cache.putWarehouseList(list);
		}
		return list;
	}
	
	public List getEnableWarehouseByVisit(int userid) throws Exception {
		return appo.getEnableWarehouseByVisit(userid);
	}
	
	/**
	 * 获取指定用户和仓库属性的仓库集合
	 * @Time 2011-8-15 下午12:02:45 create
	 * @param userid 用户ID
	 * @param warehouseProperty 仓库属性
	 * @return List 指定用户和仓库属性的仓库集合列表
	 * @throws Exception
	 * @author dufazuo
	 */
	public List getEnableWarehouseByVisit(int userid, int warehouseProperty) throws Exception
	{
		return appo.getEnableWarehouseByVisit(userid, warehouseProperty);
	}
	
	public List getEnableWarehouseByVisit(int userid, String orgnaid) throws Exception {
		return appo.getEnableWarehouseByVisit(userid, orgnaid);
	}
	
	/**
	 * 获取指定用户、仓库属性和机构的仓库集合
	 * @Time 2011-8-15 下午12:05:17 create
	 * @param userid 用户ID
	 * @param warehouseProperty 仓库属性
	 * @param organid 机构ID
	 * @return List 指定用户、仓库属性和机构的仓库集合列表 
	 * @throws Exception
	 * @author dufazuo
	 */
	public List getEnableWarehouseByVisit(int userid, int warehouseProperty, String organid) throws Exception
	{
		return appo.getEnableWarehouseByvisit(userid, warehouseProperty, organid);
	}
	
	public List getCanUseWarehouse()throws Exception{
		List list = cache.getCanUseWarehouse();
		if ( list == null ){
			list = appo.getCanUseWarehouse();
			modifyCache();
		}
		return list;
	}
	
	public List getCanUseWarehouseByOid(String oid) throws Exception {
		List list = cache.getCanUseWarehouseByOid(oid);
		if ( list == null ){
			list = appo.getCanUseWarehouseByOid(oid);
			modifyCache();
		}
		return list;
	}
	
	public Warehouse getWarehouseByID(String id)throws Exception{
		if ( id == null || "".equals(id) ){
			return null;
		}
		Warehouse o = cache.getWarehouse(id);
		if ( o == null ){
			o = appo.getWarehouseByID(id);
			modifyCache();
		}
		return o;
	}
	
	public List getWarehouseListByOID(String oid)throws Exception{
		List list = cache.getWarehouseListByOID(oid);
		if ( list == null ){
			list = appo.getWarehouseListByOID(oid);
			modifyCache();
		}
		return list;
	}
	
	public Warehouse getWarehouseByOID(String oid)throws Exception{
		Warehouse w = cache.getWarehouseByOID(oid);
		if ( w == null ){
			w = appo.getWarehouseByOID(oid);
			modifyCache();
		}
		return w;
	}
	
	public String getWarehouseName(String id) throws Exception{
		if ( id == null || "".equals(id) ){
			return "";
		}
		Warehouse o = getWarehouseByID(id);
		if ( o != null ){
			return o.getWarehousename();
		}
		return "";
	}
	
	public String getWarehouseBitName(String bitName) throws Exception{
		if ( (bitName != null)&&(!("".equals(bitName))) ){
			return bitName;
		}
		return "默认仓位";
	}
	
	
	public void addWarehouseBit(WarehouseBit wb) throws Exception {		
		appo.addWarehouseBit(wb);
	}
	
	public List getWarehouseBit(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		return appo.getWarehouseBit(request, pagesize, pWhereClause);
	}
	
	public List getWarehouseBitByWid(String wid) throws Exception {
		return appo.getWarehouseBitByWid(wid);
	}
	
	public WarehouseBit getWarehouseBitByid(int id) throws Exception {
		return appo.getWarehouseBitByid(id);
	}
	
	public WarehouseBit getWarehouseBitByWidWbid(String wid, String wbid) throws Exception {
		return appo.getWarehouseBitByWidWbid(wid, wbid);
	}
	
	public void delWarehouseBitByid(int id) throws Exception {
		appo.delWarehouseBitByid(id);
	}

	
	 private void modifyCache() throws Exception {
		//cache.modifyWarehouse(appo.getAllWarehousebean());
	}
	
	/**
	* @author jason.huang
	* @param s whereSql
	* 
	*/
	public List<Warehouse> findByWhereSql(String whereSql) throws Exception {
		String sql = " from Warehouse as w " + whereSql + " order by w.id desc ";
		return EntityManager.findByWhereSql(sql);
	}				 
	 
	 
	
}

