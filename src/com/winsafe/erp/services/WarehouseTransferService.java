package com.winsafe.erp.services;

import java.util.Arrays;
import java.util.List; 
import java.util.Map;

import com.winsafe.erp.metadata.FileType;

public class WarehouseTransferService extends FileTransferService {
	
	private String TAG_NAME = "warehouseTime";
	
	/**
	 * 初始化数据
	 */
	public void init() {
		title="机构编号,仓库编号,仓库企业内部编号（TD的shipto code）,仓库名称,是否可用";
		columnNames = Arrays.asList("makeorganid","id","nccode","warehousename","useflag");
	}

	public void createTransferFile() throws Exception {
		init();
		String startTime = getStartTime(TAG_NAME);
		String endTime = getEndTime();
		
		List<Map<String,String>> dataList = appTl.getWarehousesToTransfer(startTime, endTime);
		
		createFileAndAddLog(dataList, FileType.WAREHOUSE, endTime, TAG_NAME);
	}
	
	public static void main(String[] args) throws Exception {
//		new WarehouseTransferService().createTransferFile();
//		HibernateUtil.commitTransaction();
		StringBuffer sql = new StringBuffer();
		sql.append("select sam.id,tt.oid outoid,outo.organname outoname,tt.warehouseid outwid,outw.warehousename outwname,tt.inoid,ino.organname inoname,tt.inwarehouseid inwid,inw.warehousename inwname,sam.makedate,sam.auditdate,tt.auditdate shipmentdate,sam.receivedate from TAKE_TICKET tt");
//		sql.append("pca.areaname province,cca.areaname city,aca.areaname,bsba.name_loc bigarea, ");
//		sql.append("msba.name_loc middlearea, sba.name_loc smallarea,");
//		sql.append("case when o.isrepeal = 1 then '是' else '否' END isrepeal,");
//		sql.append("o.validate_status status ");
		System.out.println(sql.toString().toLowerCase());
	}
	
}
