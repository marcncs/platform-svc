package com.winsafe.drp.task;

import java.io.File;
import java.util.Date;
import java.util.List;


import org.apache.log4j.Logger;

import com.winsafe.drp.service.HZProduceFileHandler;
import com.winsafe.drp.service.ReplcaeCodeHandler;
import com.winsafe.erp.metadata.ProduceFileType;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil; 
import com.winsafe.sap.dao.AppPrimaryCode;
import com.winsafe.sap.dao.AppUploadProduceLog;
import com.winsafe.sap.pojo.UploadProduceLog;

public class ProduceUploadTask {

	// 同步锁 
	private static Object lock = new Object();
	private static boolean isRunning = false;

	private static Logger logger = Logger.getLogger(ProduceUploadTask.class);

	private static List<UploadProduceLog> uploadPrLogs;
	AppPrimaryCode appPrimaryCode = new AppPrimaryCode();
	
	private AppUploadProduceLog appLog = new AppUploadProduceLog();
	
	/**
	 * 初始化要处理的任务
	 */
	public void init() throws Exception {
		uploadPrLogs = appLog.getAllUnProcessedLog(ProduceFileType.HZ_PLANT.getValue());
	}

	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					logger.debug("start processing sap upload file.");
					this.init();
					isRunning = true;
					logger.info(DateUtil.getCurrentDate()
							+ " 自动处理生产数据上传文件任务---开始---");
					if (uploadPrLogs != null && uploadPrLogs.size() > 0) {
						for (int i = 0; i < uploadPrLogs.size(); i++) {
							if(uploadPrLogs.get(i) == null) {
								continue;
							}
							logger.debug("开始时间：" + uploadPrLogs.get(i).getId()
									+ new Date());
							long startTime = System.currentTimeMillis();
							execute(uploadPrLogs.get(i));
							long endTime = System.currentTimeMillis();
							logger.debug("编号为" + uploadPrLogs.get(i).getId()
									+ "的生产数据上传任务处理时间为" + (endTime - startTime)
									+ "ms " + (endTime - startTime) / 1000
									+ "s");
						}
					}
				} catch (Exception e) {
					logger.error(e);
					logger.info(DateUtil.getCurrentDate() + " 自动处理生产数据上传文件任务发生异常"
							+ e.getMessage());
				} finally {
					HibernateUtil.closeSession();
					isRunning = false;
					logger.info(DateUtil.getCurrentDate()
							+ " 自动处理生产数据上传文件任务---结束---");
				}
			}
		}
	}

	/**
	 * 
	 * @param fileName
	 * @throws Exception 
	 * @throws Exception
	 */
	public void execute(UploadProduceLog uploadProduceLog) throws Exception {
		String filePath = uploadProduceLog.getFilePath();
		File file = new File(filePath);
		
		//处理文件
		if(ProduceFileType.TOLLING_REPLACE.getValue().toString().equals(uploadProduceLog.getFileType())) {
			ReplcaeCodeHandler handler = new ReplcaeCodeHandler();
			handler.proceeFile(uploadProduceLog, file);
		} else {
			HZProduceFileHandler handler = new HZProduceFileHandler();
			handler.proceeFile(uploadProduceLog, file);
		}
		
	}

}
