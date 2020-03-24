package com.winsafe.drp.service;

import java.io.File;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.winsafe.drp.util.PlantConfig;
import com.winsafe.drp.util.UploadErrorMsg;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil; 
import com.winsafe.sap.dao.AppUploadProduceLog;
import com.winsafe.sap.metadata.SapUploadLogStatus;
import com.winsafe.sap.pojo.UploadProduceLog;
import com.winsafe.sap.util.FileUploadUtil;

public class ProduceFileHandler {
	
	private static Logger logger = Logger.getLogger(ProduceFileHandler.class);
	private AppUploadProduceLog appLog = new AppUploadProduceLog();

	protected void updateLogStatus(UploadProduceLog uploadPrLog, int status) throws Exception {
		uploadPrLog.setIsDeal(status);
		appLog.updUploadProduceLog(uploadPrLog);
		HibernateUtil.commitTransaction();
	}
	
	public void proceeFile(UploadProduceLog uploadProduceLog, File file) throws Exception {
		//错误信息
		StringBuffer resultMsg = new StringBuffer();
		
		//获取要生成的日志文件路径
		String savePath = PlantConfig.getConfig().getProperty("produceLogFilePath")
				+ DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM")
				+ "/";
//		String fileName = uploadProduceLog.getFileName();
		String saveName = DateUtil.getCurrentDateTimeString() + "_"+UUID.randomUUID()
//				+ fileName.substring(fileName.indexOf("."), fileName.length())
				+ "_LOG.txt"; 
		// 错误信息
		
		if(appLog.isFileAlreadyExists(uploadProduceLog.getFileHaseCode(),uploadProduceLog.getId())) {
			resultMsg.append("已处理过相同的文件");
			updateLogStatus(uploadProduceLog, SapUploadLogStatus.PROCESS_FAIL.getDatabaseValue());
			createLogFile(resultMsg.toString(), savePath, saveName, uploadProduceLog);
			return;
		}
		
		try {
			//更新为处理中
			updateLogStatus(uploadProduceLog, SapUploadLogStatus.PROCESSING.getDatabaseValue());
			if (!file.exists()) {
				resultMsg.append(UploadErrorMsg.getError(UploadErrorMsg.E00214, file.getPath()));
			} else {
				execute(file, uploadProduceLog, resultMsg);
			}
			updateLogStatus(uploadProduceLog, SapUploadLogStatus.PROCESS_SUCCESS.getDatabaseValue());
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			logger.error("", e);
			resultMsg.append(e.getMessage());
			updateLogStatus(uploadProduceLog, SapUploadLogStatus.PROCESS_FAIL.getDatabaseValue());
		}
		
		if(resultMsg.length() > 0) {
			createLogFile(resultMsg.toString(), savePath, saveName, uploadProduceLog);
		}
		
	}
	
	private void createLogFile(String string, String savePath, String saveName, UploadProduceLog uploadProduceLog) {
		try {
			FileUploadUtil.CreateFileWithMessage(string,
					savePath, saveName);
			uploadProduceLog.setLogFilePath(savePath + saveName);
			appLog.updUploadProduceLog(uploadProduceLog);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			logger.error("error occurred when create log file :" +savePath+saveName, e);
		}
	}

	public void execute(File file, UploadProduceLog uploadProduceLog, StringBuffer resultMsg)  throws Exception  {
		
	}
}
