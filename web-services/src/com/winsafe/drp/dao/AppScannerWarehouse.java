package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;
@SuppressWarnings("unchecked")
public class AppScannerWarehouse {

	public Organ getOrganByScannerId(String scannerid) throws Exception {
		String hql = "from ScannerWarehouse where scanner ='" + scannerid + "'";
		List<ScannerWarehouse> scannerWarehouses = EntityManager.getAllByHql(hql);
		// 未配置采集器
		if (scannerWarehouses.size() == 0) {
			return null;
		} else {
			Warehouse warehouse = new AppWarehouse().getWarehouseByID(scannerWarehouses.get(0).getWarehouseid());
			Organ organ = new AppOrgan().getOrganByID(warehouse.getMakeorganid());
			return organ;
		}
	}

	public List getScannerWarehouse(HttpServletRequest request, String whereSql) throws Exception {
		String hql = " from ScannerWarehouse as sw " + whereSql + " order by sw.id asc";
		return PageQuery.hbmQuery(request, hql);
	}

	public List<ScannerWarehouse> getScannersByWh(String whids) throws Exception {
		String hql = "from ScannerWarehouse where warehouseid in ('" + whids + "')";
		return (List) EntityManager.getAllByHql(hql);
	}

	public List<ScannerWarehouse> getScannersByScannerid(String sid) throws Exception {
		String hql = "from ScannerWarehouse where scannerid ='" + sid + "'";
		return (List) EntityManager.getAllByHql(hql);
	}

	public List<ScannerWarehouse> getScannerWarehouse() throws Exception {
		String hql = "from ScannerWarehouse order by id";
		return (List) EntityManager.getAllByHql(hql);
	}

	/**
	 * 根据采集器编号和仓库编号进行查询
	 * 
	 * @param scannerid
	 * @param warehouseid
	 * @return
	 * @throws Exception
	 */
	public List<ScannerWarehouse> getScannerWarehouseByScanneridWarehouseid(String scannerid, String warehouseid)
			throws Exception {
		String hql = "from ScannerWarehouse where scanner ='" + scannerid + "' and warehouseid ='" + warehouseid + "'";
		return (List) EntityManager.getAllByHql(hql);
	}

	/**
	 * 新增采集器编号和仓库编号的匹配关系
	 * 
	 * @param scannerwarehouse
	 * @return
	 * @throws Exception
	 */
	public boolean InsertScannerWarehouse(ScannerWarehouse scannerwarehouse) throws Exception {
		try {
			EntityManager.save(scannerwarehouse);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 获取ScannerWarehouse表中最大ID+1
	 * 
	 * @author wei.li
	 * @return int
	 */
	public int getMaxScannerWarehouseId() {
		String sql = "SELECT MAX(id) + 1 FROM ScannerWarehouse";
		return EntityManager.getRecordCount(sql);
	}

	/**
	 * 删除ScannerWarehouse表某条记录
	 * 
	 * @author wei.li
	 * @param id
	 * @throws Exception
	 */
	public boolean delScannerWarehouseById(int id) throws Exception {
		try {
			EntityManager.updateOrdelete("delete from scanner_warehouse where id=" + id);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 通过仓库号和采集器号删除ScannerWarehouse表某条记录
	 * 
	 * @author jason.huang
	 * @param id
	 * @throws Exception
	 */
	public boolean delScannerWarehouseBySW(String scannerId,String warehouseId ) throws Exception {
		try {
			EntityManager.updateOrdelete("delete from Scanner_Warehouse where scannerId = '" + scannerId+ "' and warehouseId = '"+warehouseId+"' " );
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	
	
	
	
	/**
	 * 根据ID查询ScannerWarehouse
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ScannerWarehouse getScannerWarehouseById(int id) throws Exception {
		String hql = "from ScannerWarehouse where id = " + id;
		return (ScannerWarehouse) EntityManager.find(hql);
	}
	
	/**
	 * getSWBWAS--通过仓库编码和采集器编码获取信息
	 * @author jason.huang
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ScannerWarehouse getSWBWAS(String scannerId,String warehouseId) throws Exception {
		String hql = "from ScannerWarehouse where scannerId = '" + scannerId+ "' and warehouseId = '"+warehouseId+"' " ;
		return (ScannerWarehouse) EntityManager.find(hql);
	}
	
	

	/**
	 * 根据ID更改ScannerWarehouse
	 * 
	 * @param
	 * @param scanner
	 * @param warehouseid
	 * @param location
	 * @return
	 * @throws Exception
	 */
	public boolean updScannerWarehouse(int id, String scanner, String warehouseid, String location) throws Exception {
		try {
			String sql = "update scanner_warehouse set scanner = '" + scanner + "' , warehouseid = '" + warehouseid
					+ "' , location = '" + location + "' " + "where id = " + id;
			EntityManager.updateOrdelete(sql);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean updScannerWarehouse(int id, String scannerid, String warehouseid,String orgName,String wareHouseName) throws Exception {
		try {
			String sql = "update scanner_warehouse set scannerid = '" + scannerid + "' , warehouseid = '" + warehouseid
					+ "' , orgName = '"+orgName+"' , wareHouseName = '"+wareHouseName+"' where id = " + id;
			EntityManager.updateOrdelete(sql);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 根据ID模糊查询
	 * 
	 * @param sid
	 * @return
	 * @throws Exception
	 */
	public List<ScannerWarehouse> getScannersByScanneridByLike(String sid) throws Exception {
		String hql = "from ScannerWarehouse where scanner like '%" + sid + "%'";
		return (List) EntityManager.getAllByHql(hql);
	}
	
	/**
	 * @author jason.huang
	 * @param s whereSql
	 * 按照一定条件获取采集器仓库号信息
	 */
	public List selectWareHouse(String whereSql) throws Exception {
		String hql = " from ScannerWarehouse ";
		return EntityManager.getAllByHql(hql);
		}
	
	
	/**
	 * @author jason.huang
	 * @param request pageSize whereSql
	 * 按照一定条件获取采集器仓库信息
	 */
	public List selectWareHouse(HttpServletRequest request, int pageSize,
			String whereSql) throws Exception {
		String hql = " from ScannerWarehouse f " + whereSql + " order by f.id asc";
		return PageQuery.hbmQuery(request, hql, pageSize);
		}
	
	/**
	 * 根据ID查询ScannerWarehouse
	 * 
	 * @author jason.huang
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public ScannerWarehouse getByScannerId(String scannerid) throws Exception {
		String hql = "from ScannerWarehouse where scannerid = " + scannerid;
		return (ScannerWarehouse) EntityManager.find(hql);
	}
	
	
	
	
	
}
