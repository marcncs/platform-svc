package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;

public class AppInventoryUpload {

	public void addInventoryUpload(List<String> batchSqls) throws Exception {
		EntityManager.executeBatch(batchSqls);
	}
	
	public List getInventoryUpload(Long id) throws Exception {
		String hql="select billNo,produceDate,batch,expiryDate,materialCode from InventoryUpload where inventoryUploadLogId = "+id+" and flag = 3  group by billNo,produceDate,batch,expiryDate,materialCode";
		return EntityManager.getAllByHql(hql);
	}

	public void addInventoryUpload(String[] inventoryData, String[] ids,
			InventoryUploadLog inventoryUploadLog) throws HibernateException, SQLException, Exception {
		String sql = "insert into INVENTORY_UPLOAD (ID,BILLNO,MATERIALCODE,CARTONCODE,BATCH,PRODUCEDATE,EXPIRYDATE,FLAG,ORGANID,WAREHOUSEID,ORGANOECODE,WAREHOUSENCCODE,INVENTORYUPLOADID) values (seq_inventory_upload.nextval,'"
			+ inventoryData[1] + "','" + inventoryData[2] + "','" + inventoryData[4] + "','"
			+ inventoryData[7] + "','" + inventoryData[8] + "','" + inventoryData[9] + "','"
			+ inventoryData[10] + "','"	+ ids[ids.length - 5] + "','" + ids[ids.length - 4] + "','" + ids[ids.length - 3] + "','"
			+ ids[ids.length - 2] + "'," + inventoryUploadLog.getId() + ")";
		EntityManager.executeUpdate(sql);
	}
}
