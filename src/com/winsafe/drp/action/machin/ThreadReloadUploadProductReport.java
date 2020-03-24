package com.winsafe.drp.action.machin;

import com.winsafe.drp.action.common.AppRecord;
import com.winsafe.drp.action.common.AppReloadRecord;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * @author : jerry
 * @version : 2009-10-15 下午03:58:21 www.winsafe.cn
 */
//public class ThreadUploadProductReport extends Thread {
public class ThreadReloadUploadProductReport{
	private Logger logger = Logger.getLogger(ThreadReloadUploadProductReport.class);
	protected String filePath;
	protected Integer logid;
	protected Integer userid;
	protected String warehouseId;
	
	public ThreadReloadUploadProductReport(String filePath,Integer logid,Integer userid,String warehouseId) {
		this.filePath = filePath;
		this.logid=logid;
		this.userid = userid;
		this.warehouseId=warehouseId;
	}

	public String  run() {
			AppReloadRecord app = new AppReloadRecord();
			String info=null;
			try {
				info = app.getRecord(filePath,logid,userid,warehouseId);
				if(info!=null){
					return info;
				}
			} catch (Exception e) {
				logger.error("", e);
			}
			return info;
	}

}
