package com.winsafe.sap.task;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.winsafe.aws.util.S3Util;
import com.winsafe.drp.server.DealUploadIdcodeService;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.mail.dao.AppBatchCompleteMail;
import com.winsafe.mail.metadata.MailType;
import com.winsafe.mail.pojo.BatchCompleteMail;
import com.winsafe.mail.smtp.base.SMTPMailFactory;
import com.winsafe.sap.dao.AppSapUploadLog;
import com.winsafe.sap.dao.SapUploadHandler;
import com.winsafe.sap.metadata.SapFileErrorType;
import com.winsafe.sap.metadata.SapFileType;
import com.winsafe.sap.metadata.SapUploadLogStatus;
import com.winsafe.sap.pojo.UploadSAPLog;
import com.winsafe.sap.util.FileUploadUtil;

/*******************************************************************************************  
 * 定时任务类,处理上传的SAP文件
 * @author: ryan.xi	  
 * @date：2014-11-30  
 * @version 1.0  
 *  
 *  
 * Version    Date       ModifiedBy                 Content  
 * -------- ---------    ----------         ------------------------  
 * 1.0      2014-11-30   ryan.xi         
 * 1.1      2016-03-02   ryan.xi        	添加分装厂SAP条码导入文件处理逻辑
 *******************************************************************************************  
 */  
public class SapBillUploadTask {
	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

	private static Logger logger = Logger.getLogger(SapBillUploadTask.class);
	//待处理的上传日志
	private static UploadSAPLog sapUploadLog;

	private AppSapUploadLog appSapUploadLog = new AppSapUploadLog();
	private AppBatchCompleteMail abcm = new AppBatchCompleteMail();

	/**
	 * 初始化要处理的任务
	 * @param types 
	 */
	public void init(boolean isBill) throws Exception {
		String types = "'"+SapFileType.DELIVERY.getDatabaseValue()+"','"+SapFileType.INVOICE.getDatabaseValue()+"'";
		StringBuffer whereSql = new StringBuffer();
		whereSql.append(" where isDeal = "+ SapUploadLogStatus.NOT_PROCESS.getDatabaseValue());
		if(isBill) {
			whereSql.append(" and fileType in ("+types+") order by fileType,makeDate" );
		} else {
			whereSql.append(" and fileType not in ("+types+") order by makeDate" );
		}
				 
		sapUploadLog = appSapUploadLog.findSapUploadLogByWhere(whereSql.toString());
	}
	/**
	 * 定时任务入口方法
	 */
	public void run(boolean isBill) {
		if (!isRunning) {
			synchronized (lock) {
				try {
					this.init(isBill);
					isRunning = true;
					logger.info(DateUtil.getCurrentDate()
							+ " 自动处理SAP上传文件任务---开始---");
					if (sapUploadLog != null) {
							logger.debug("start processing sap upload file." +sapUploadLog.getFileName());
							long startTime = System.currentTimeMillis();
							if(startProcess(sapUploadLog)) {
								if(!"90".equals(sapUploadLog.getFileType())){
									execute(sapUploadLog);
								}else{
									executeText(sapUploadLog);
								}
							}
							long endTime = System.currentTimeMillis();
							logger.debug("文件"
									+ sapUploadLog.getFilePath()
									+ "处理时间为" + (endTime - startTime) + "ms "
									+ (endTime - startTime) / 1000 + "s");
						}
				} catch (Exception e) {
					logger.error(e);
					logger.info(DateUtil.getCurrentDate()
							+ " 自动处理SAP上传文件任务发生异常" + e.getMessage());
				} finally {
					HibernateUtil.closeSession();
					isRunning = false;
					logger.info(DateUtil.getCurrentDate()
							+ " 自动处理SAP上传文件任务---结束---");
				}
			}
		}
	}

	/**
	 * 更新文件上传日志状态
	 * @param sapUploadLog
	 */
	private void updUploadSAPlog(UploadSAPLog sapUploadLog) {
		logger.debug("update sapUploadLog isdeal status to " + SapUploadLogStatus.parse(sapUploadLog.getIsDeal()).getDisplayName());
		try {
			appSapUploadLog.updUploadSAPlog(sapUploadLog);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			logger.error("error occurred when updating sapUploadLog processing status: " + e.getMessage(),
					e);
			HibernateUtil.rollbackTransaction();
		}
		
	}
	/**
	 * 更新文件上传日志状态
	 * @param sapUploadLog
	 */
	private boolean startProcess(UploadSAPLog sapUploadLog) {
		String sql = "update UPLOAD_SAP_LOG set isdeal = "
				+ SapUploadLogStatus.PROCESSING.getDatabaseValue()
				+ " where id = " + sapUploadLog.getId() + " and isdeal="
				+ SapUploadLogStatus.NOT_PROCESS.getDatabaseValue();
		try {
			int result = appSapUploadLog.updSapUploadLogBySql(sql);
			HibernateUtil.commitTransaction();
			if(result > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error(
					"hibernate rollback error when update sapUploadLog's isdeal status: "
							+ e.getMessage(), e);
			HibernateUtil.rollbackTransaction();
			return false;
		}
	}

	/**
	 * 处理上传的Sap单据文件
	 * @param fileName
	 * @throws Exception
	 */
	public void execute(UploadSAPLog sapUploadLog) {
		logger.debug("start process sap file " + sapUploadLog.getFileName());
		boolean success = false;
		StringBuffer resultMsg = new StringBuffer();
		String filePath = sapUploadLog.getFilePath();
		String getFileType = sapUploadLog.getFileType();
		try {
			//File file = new File(filePath);
			File file = S3Util.downloadFile(filePath);
			if (!file.exists()) {
				success = false;
				resultMsg = resultMsg.append("文件不存在：").append(filePath);
			} else {
				SapFileType fileType = SapFileType.parse(sapUploadLog
						.getFileType());
				SapUploadHandler pto = SapUploadHandler.getHandler(fileType);
				pto.setUploadSAPLog(sapUploadLog);
				if (pto.parse(file)) {
					success = false;
					resultMsg = pto.getErrMsg();
				} else {
					pto.handle();
					success = true;
					resultMsg = resultMsg.append("成功导入").append(
							pto.getTotalCount()).append("条数据。");
				}
				sapUploadLog.setErrorCount(pto.getErrCount());
				sapUploadLog.setTotalCount(pto.getTotalCount());
			}
		} catch (Exception e) {
			success = false;
			//#start modified by ryan.xi at 20150724
			sapUploadLog.setErrorType(SapFileErrorType.FILE_PROCESS_ERROR.getDbValue());
			//#end modified by ryan.xi at 20150724
			logger.error("processing sap upload file error: " + e.getMessage(),
					e);
			resultMsg = resultMsg.append("导入数据时发生系统错误" + e.getMessage());
			HibernateUtil.rollbackTransaction();
		}
		
		if(success) {
			sapUploadLog.setIsDeal(SapUploadLogStatus.PROCESS_SUCCESS
					.getDatabaseValue());
			updUploadSAPlog(sapUploadLog);
		} else {
			sapUploadLog.setIsDeal(SapUploadLogStatus.PROCESS_FAIL
					.getDatabaseValue());
			createLogFile(sapUploadLog, resultMsg);
			updUploadSAPlog(sapUploadLog);
			if(SapFileType.DELIVERY.getDatabaseValue().equals(getFileType)){
				addNotificationMail(sapUploadLog);
			}
		}
		logger.debug("process sap file " + sapUploadLog.getFileName() + " complete");
	}
	
	/**
	 * 处理分装厂上传的条码文件
	 * @param fileName
	 * @throws Exception
	 */
	public void executeText(UploadSAPLog sapUploadLog) {
		logger.debug("start process sap file " + sapUploadLog.getFileName());
		boolean success = false;
		StringBuffer resultMsg = new StringBuffer();
		String filePath = sapUploadLog.getFilePath();
		String getFileType ="";
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				success = false;
				resultMsg = resultMsg.append("文件不存在：").append(filePath);
			} else {
				SapFileType fileType = SapFileType.parse(sapUploadLog
						.getFileType());
				getFileType = sapUploadLog.getFileErrorType();
				DealUploadIdcodeService uploadIdcodeService = new DealUploadIdcodeService();
				
				resultMsg= uploadIdcodeService.dealSapCodeUpload(file,sapUploadLog);
				if(resultMsg.toString().startsWith("成功导入")){
					success = true;
				}else{
					success = false;
				}

			}
		} catch (Exception e) {
			success = false;
			//#start modified by ryan.xi at 20150724
			sapUploadLog.setErrorType(SapFileErrorType.FILE_PROCESS_ERROR.getDbValue());
			//#end modified by ryan.xi at 20150724
			logger.error("processing sap upload file error: " + e.getMessage(),
					e);
			resultMsg = resultMsg.append("导入数据时发生系统错误" + e.getMessage());
			HibernateUtil.rollbackTransaction();
		}
		
		if(success) {
			sapUploadLog.setIsDeal(SapUploadLogStatus.PROCESS_SUCCESS
					.getDatabaseValue());
			updUploadSAPlog(sapUploadLog);
		} else {
			sapUploadLog.setIsDeal(SapUploadLogStatus.PROCESS_FAIL
					.getDatabaseValue());
			createLogFile(sapUploadLog, resultMsg);
			updUploadSAPlog(sapUploadLog);
		}
		logger.debug("process sap file " + sapUploadLog.getFileName() + " complete");
	}

	/**
	 * 生成处理日志文件
	 * @param sapUploadLog
	 * @param resultMsg
	 */
	private void createLogFile(UploadSAPLog sapUploadLog, StringBuffer resultMsg) {
		File file = null;
		try {
			// 创建日志文件
			/*String savePath = FileUploadUtil.getSapLogFilePath()
					+ DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM")
					+ "/";
			String fileName = sapUploadLog.getFileName();
			String saveName = DateUtil.getCurrentDateTimeString() + "_"
					+ fileName.substring(fileName.indexOf("."), fileName.length())
					+ "_LOG.txt";
			logger.debug("start creating sap log file :" + savePath + saveName);
			FileUploadUtil.CreateLogFile(resultMsg.toString(), savePath,
					saveName);
			sapUploadLog.setLogFilePath(savePath + saveName);*/
			file = File.createTempFile(sapUploadLog.getFileName(), ".txt");
			FileUploadUtil.CreateFileWithMessage(resultMsg.toString(), file);
			S3Util.uploadToS3(file, FileUploadUtil.getSapLogFilePath(), file.getName());
			sapUploadLog.setLogFilePath(FileUploadUtil.getSapLogFilePath() +"/"+ file.getName());
		} catch (Exception e) {
			logger.error(
					"error occurred when creating sapUploadLog log file: " + e.getMessage(), e);
		} finally {
			if(file!=null) {
				file.deleteOnExit();
			}
		}
	}
	
	/**
	 * 新增通知邮件记录
	 * @param sapUploadLog
	 */
	private void addNotificationMail(UploadSAPLog sapUploadLog) {
		String dateType = DateUtil.formatDate(new Date()) + "_" +MailType.DELIVERY_ERROR.getDbValue();
		try {
			BatchCompleteMail mail = abcm.getMailByDateType(dateType);
			if(mail != null) {
				mail.setMailBody(mail.getMailBody()+"发货日志编号："+sapUploadLog.getId()+" 发货单号："+StringUtil.removeNull(sapUploadLog.getBillNo())+"\r\n");
				abcm.update(mail);
			} else {
				mail = new BatchCompleteMail();
				Properties mailPro = SMTPMailFactory.getSMTPMailFactory();
				mail.setMailSender(mailPro.getProperty("delivery_mailSender"));
				mail.setMailFrom(mailPro.getProperty("delivery_mailFrom"));
				mail.setMailTo(mailPro.getProperty("delivery_mailTo"));
				mail.setMailCc(mailPro.getProperty("delivery_mailCc"));
				mail.setMailSubject(mailPro.getProperty("delivery_mailSubject"));
				mail.setMailBody("发货日志编号："+sapUploadLog.getId()+" 发货单号："+StringUtil.removeNull(sapUploadLog.getBillNo())+"\r\n");
				mail.setCreateDate(DateUtil.getCurrentDate());
				mail.setMailType(MailType.DELIVERY_ERROR.getDbValue());
				mail.setMailDateType(dateType);
				abcm.add(mail);
			}
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			logger.error("添加更新邮件时异常：",e);
		}
	}
}
