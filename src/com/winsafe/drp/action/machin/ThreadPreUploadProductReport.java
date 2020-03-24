package com.winsafe.drp.action.machin;

import org.apache.log4j.Logger;

import com.winsafe.drp.action.common.AppPreRecord;
/**
 * 预生产上传线程处理
* @Title: ThreadPreUploadProductReport.java
* @author: wenping 
* @CreateTime: Apr 9, 2013 9:41:21 AM
* @version:
 */
public class ThreadPreUploadProductReport extends Thread{
	private Logger logger = Logger.getLogger(ThreadPreUploadProductReport.class);
	protected String filePath;
	protected Integer logid;
	protected String type;
	
	public ThreadPreUploadProductReport(String filePath ,Integer logid) {
		this.filePath = filePath;
		this.logid=logid;
	}

	public void  run() {
			AppPreRecord app = new AppPreRecord();
			try {
				app.getRecord(filePath,logid);
			} catch (Exception e) {
				logger.error("", e);
			}
	}

}
