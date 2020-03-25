package com.winsafe.drp.action.machin;

import org.apache.log4j.Logger;

import com.winsafe.drp.action.common.AppRecord;
import com.winsafe.drp.action.common.AppRecordNew;

/**
 * @author : jerry
 * @version : 2009-10-15 下午03:58:21 www.winsafe.cn
 */
//public class ThreadUploadProductReport extends Thread {
public class ThreadUploadProductReport{
	private Logger logger = Logger.getLogger(ThreadUploadProductReport.class);
	protected String filePath;
	protected Integer logid;
	protected Integer userid;
	protected String warehouseId;
	protected String logFilePath;
	
	public ThreadUploadProductReport(String filePath,Integer logid,String warehouseId, String logFilePath) {
		this.filePath = filePath;
		this.logid=logid;
		this.warehouseId=warehouseId;
		this.logFilePath=logFilePath;
	}

	public String  run() {
			AppRecordNew appNew = new AppRecordNew();
//			AppRecord app = new AppRecord();
			String info=null;
			try {
//				info = app.getRecord(filePath,logid,warehouseId);
				info = appNew.getRecord(filePath,logid,warehouseId,logFilePath);
				
				if(info!=null){
					return info;
				}
			} catch (Exception e) {
				logger.error("", e);
			}
			return info;
	}

}
