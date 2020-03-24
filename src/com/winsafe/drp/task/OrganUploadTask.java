package com.winsafe.drp.task;

import java.io.File; 

import org.apache.log4j.Logger;

import com.winsafe.drp.server.ImportOrganService;
import com.winsafe.drp.server.UpdOrganService;
import com.winsafe.drp.util.JProperties;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.sap.dao.AppOrganUploadLog;
import com.winsafe.sap.metadata.SapUploadLogStatus;
import com.winsafe.sap.pojo.OrganUploadLog;
import com.winsafe.sap.util.FileUploadUtil;

/**
 * 自动处理导入文件的Sap文件
 * 
 * @author Ryan.Xi
 * 
 */
public class OrganUploadTask {
	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

	private static Logger logger = Logger.getLogger(OrganUploadTask.class);

//	private static List<UploadSAPLog> sapUploadLogs;

	private AppOrganUploadLog appOrganUploadLog = new AppOrganUploadLog();

	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					isRunning = true;
					logger.info(DateUtil.getCurrentDate()
							+ " 自动处理机构 上传文件任务---开始---");
					OrganUploadLog log = appOrganUploadLog.getNotDealedLog();
					if (log != null) {
						logger.debug("start processing organ upload file." +log.getFileName());
						long startTime = System.currentTimeMillis();
						if(startProcess(log)) {
							execute(log);
						}
						long endTime = System.currentTimeMillis();
						logger.debug("文件"
								+ log.getId()
								+ "处理时间为" + (endTime - startTime) + "ms "
								+ (endTime - startTime) / 1000 + "s");
					}
				} catch (Exception e) {
					logger.error(e);
					logger.info(DateUtil.getCurrentDate()
							+ " 自动处理机构 上传文件任务发生异常" + e.getMessage());
				} finally {
					HibernateUtil.closeSession();
					isRunning = false;
					logger.info(DateUtil.getCurrentDate()
							+ " 自动处理机构 上传文件任务---结束---");
				}
			}
		}
	}

	// 更新文件上传日志状态
	private void updOrganUploadlog(OrganUploadLog log) {
		logger.debug("update OrganUploadLog isdeal status to " + SapUploadLogStatus.parse(log.getIsDeal()).getDisplayName());
		try {
			appOrganUploadLog.updOrganUploadLog(log);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			logger.error("error occurred when updating OrganUploadLog processing status: " + e.getMessage(),
					e);
			HibernateUtil.rollbackTransaction();
		}
		
	}

	private boolean startProcess(OrganUploadLog organUploadLog) {
		String sql = "update Organ_Upload_Log set isdeal = "
				+ SapUploadLogStatus.PROCESSING.getDatabaseValue()
				+ " where id = " + organUploadLog.getId() + " and isdeal="
				+ SapUploadLogStatus.NOT_PROCESS.getDatabaseValue();
		try {
			int result = appOrganUploadLog.updOrganUploadLogBySql(sql);
			HibernateUtil.commitTransaction();
			if(result > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error(
					"hibernate rollback error when update organ UploadLog's isdeal status: "
							+ e.getMessage(), e);
			HibernateUtil.rollbackTransaction();
			return false;
		}
	}

	/**
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	public void execute(OrganUploadLog log) {
		logger.debug("start process organ file " + log.getId());
		boolean success = false;
		StringBuffer resultMsg = new StringBuffer();
		String filePath = log.getFilePath();
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				success = false;
				resultMsg = resultMsg.append("文件不存在：").append(filePath);
			} else {
				if(log.getType() != null && log.getType() == 1) {
					ImportOrganService ios = new ImportOrganService();
					ios.execute(log, file);
				} else {
					UpdOrganService ios = new UpdOrganService();
					ios.execute(log, file);
				}
				success = true;
			}
		} catch (Exception e) {
			success = false;
			logger.error("processing sap upload file error: " + e.getMessage(),
					e);
			resultMsg = resultMsg.append("导入数据时发生系统错误" + e.getMessage());
			HibernateUtil.rollbackTransaction();
		}
		
		if(success) {
			log.setIsDeal(SapUploadLogStatus.PROCESS_SUCCESS
					.getDatabaseValue());
			updOrganUploadlog(log);
		} else {
			log.setIsDeal(SapUploadLogStatus.PROCESS_FAIL
					.getDatabaseValue());
			createLogFile(log, resultMsg);
			updOrganUploadlog(log);
		}
		logger.debug("process sap file " + log.getFileName() + " complete");
	}
	
	/**
	 * 
	 * @param fileName
	 * @throws Exception
	 */

	private void createLogFile(OrganUploadLog uploadLog, StringBuffer resultMsg) {
		try {
			// 创建日志文件
			String uploadPath = (String)JProperties.loadProperties("upr.properties",JProperties.BY_CLASSLOADER).get("organLogFilePath"); 
//			String uploadPath = "/upload/organ/log/";
			String saveFileName = uploadLog.getFileName()+"_"+uploadLog.getId()+"_log.txt";
			
			FileUploadUtil.CreateLogFile(resultMsg.toString(), uploadPath,
					saveFileName);
			uploadLog.setLogFilePath(uploadPath + saveFileName);
		} catch (Exception e) {
			logger.error(
					"error occurred when creating sapUploadLog log file: " + e.getMessage(), e);
		} 
	}
	
}
