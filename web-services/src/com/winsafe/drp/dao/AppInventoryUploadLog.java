package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.sap.pojo.PrintJob;
import com.winsafe.sap.pojo.UploadSAPLog;

public class AppInventoryUploadLog {

	public List getInventoryUploadLog(HttpServletRequest request, int pagesize,
			String whereSql) throws Exception {
		String hql=" from InventoryUploadLog as o "+whereSql+" order by o.id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public int addInventoryUploadLogBySql(String sql) throws Exception {
		return EntityManager.executeUpdate(sql);
	}

	public void addInventoryUploadLog(InventoryUploadLog inventoryUploadLog) {
		EntityManager.save(inventoryUploadLog);
	}
	
	public List getInventoryUploadLogByWhere(String whereSql) {
		return EntityManager.getAllByHql(" from InventoryUploadLog as u " + whereSql);
	}

	public int updInventoryUploadLogBySql(String sql) throws Exception {
		return EntityManager.executeUpdate(sql);
	}
	
	public void updInventoryUploadLog(InventoryUploadLog inventoryUploadLog)throws Exception {		
		EntityManager.update(inventoryUploadLog);		
	}

	public void updCartonCode(String whereSql, PrintJob pj) throws Exception {
		String sql = "update CARTON_CODE set print_job_id = "+pj.getPrintJobId()+" where CARTON_CODE in (select CARTONCODE from INVENTORY_UPLOAD iu " + whereSql + ") and (print_job_id is null or print_job_id = -1 ) ";
		EntityManager.executeUpdate(sql);
	}
	
	public void addCartonCode(String whereSql, PrintJob pj) throws Exception {
		String sql = "insert into CARTON_CODE (CARTON_CODE, OUT_PIN_CODE, MATERIAL_CODE,PRODUCT_ID, PRINT_JOB_ID,PRIMARY_CODE_STATUS) "
			+ "select iu.CARTONCODE,'',iu.MATERIALCODE,'"+pj.getProductId()+"', "+pj.getPrintJobId()+" ,0 from INVENTORY_UPLOAD iu "
			+ whereSql + 
			" and not exists(select carton_code from CARTON_CODE cc where cc.carton_code=iu.CARTONCODE)";
		EntityManager.executeUpdate(sql);
	}

	public void addIdcode(String whereSql, PrintJob pj, FUnit funit) throws HibernateException, SQLException, Exception {
		String sql = "insert into idcode(IDCODE, PRODUCTID, PRODUCTNAME,BATCH,PRODUCEDATE, UNITID, QUANTITY,ISUSE,ISOUT,MAKEORGANID,CARTONCODE,WAREHOUSEID,PACKQUANTITY,WAREHOUSEBIT)" +
		   " select iu.CARTONCODE ,'"+pj.getProductId()+"', '"+pj.getMaterialName()+"', '"+pj.getBatchNumber()+"', '"+pj.getProductionDate()+"',"+funit.getFunitid()+",1,1,0,"+pj.getCreateUser()+",iu.CARTONCODE, iu.WAREHOUSEID,"+funit.getXquantity()+",'"+Constants.WAREHOUSE_BIT_DEFAULT +"'"+
		   " from INVENTORY_UPLOAD iu "+ whereSql +
		   " and not exists(select idcode from idcode a where a.idcode=iu.CARTONCODE)";
		EntityManager.executeUpdate(sql);
	}

	public void updIdcode(InventoryUploadLog inventoryUploadLog, PrintJob pj) {
		// TODO Auto-generated method stub
		
	}

}
