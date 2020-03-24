package com.winsafe.drp.dao;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.dao.ExportAssign;

/**
 * @author : Qhonge
 * @version : 2010-1-27 下午12:06:34
 * www.winsafe.cn
 */


public class AppExportAssign {
	public void addAppExportAssign(AppExportAssign ea) throws Exception {		
		EntityManager.save(ea);		
	}
	
	public ExportAssign getExportAssignbyUid(Integer userid) throws Exception 
	{
		String sql = " from ExportAssign as ea where ea.userId=" + userid;
		return (ExportAssign) EntityManager.find(sql);
	}
	
	public String getExportAssignWid(Integer userid) throws Exception 
	{
		String sql = "select ea.warehouseId from ExportAssign as ea where ea.userId=" + userid;
		return EntityManager.getString(sql);
	}
	
	public void updExportAssign(ExportAssign ea) throws Exception {
		EntityManager.update(ea);
	}
	
	public void updateWarehouseID(Integer usrid,Integer warehouseId) throws Exception 
	{
		String sql = "update Export_Assign set warehouseid=" + warehouseId + " where userid="
		+ usrid;
             EntityManager.updateOrdelete(sql);
	}
	
	public void delExportAssign(Integer id) throws Exception {
		String sql = "delete from Export_Assign where userid=" + id;
		EntityManager.updateOrdelete(sql);
	}
}
