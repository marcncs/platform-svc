package com.winsafe.drp.task;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.jcraft.jsch.ChannelSftp;
import com.winsafe.drp.util.WfLogger;
import com.winsafe.drp.util.sftp.NotificationSFTPServices;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.notification.util.NotificationHandler;
import com.winsafe.sap.util.SapConfig;

/**
 * 自动处理生产数据
 * @author Andy.liu
 *
 */
public class NotificationSftpTask{
	
	private static Logger logger = Logger.getLogger(NotificationSftpTask.class);
	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

	private NotificationHandler handler;
	
	private final static String FILE_PATH = "notificationFilePath";

	private String[] filePaths; // 目录
	private String fullFilePathErr;// 错误日志文件目录
	private String[] serviceCalls;
	private NotificationSFTPServices sftpServices = null;

	/**
	 * 初始化路径
	 */
	public void init() throws Exception{
		filePaths = SapConfig.getSapConfig().getProperty(FILE_PATH).split(",");
		serviceCalls = SapConfig.getSapConfig().getProperty("logistic.service.call").split(",");
		sftpServices = new NotificationSFTPServices();
	}

	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					this.init();
					isRunning = true;
					WfLogger.error(DateUtil.getCurrentDate() + "自动处理物流信息通知---开始---");
					for(int i=0; i<filePaths.length; i++) {
						execute(filePaths[i], serviceCalls[i]);
					}
				} catch (Exception e) {
					WfLogger.error(DateUtil.getCurrentDate() + "自动处理物流信息通知发生异常", e);
				} finally {
					HibernateUtil.closeSession();
					isRunning = false;
					try {
						WfLogger.error(DateUtil.getCurrentDate() + "自动处理物流信息通知---结束---");
					} catch (Exception e) {
						WfLogger.error("",e);
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		NotificationSFTPServices sftpServices = null;
		try {
			sftpServices = new NotificationSFTPServices();
			sftpServices.listFiles("/beifang-test/upload");
			//列出所有文件
			/*WfLogger.error("list beifang file...");
			sftpServices.listFiles("/beifang-test/upload");
			WfLogger.error("list cosco file...");
			sftpServices.listFiles("/cosco-test/upload");*/
			//移动文件
			sftpServices.moveFiles("/beifang-test/upload", "/beifang-test/backup");
			
		} catch (Exception e) {
			WfLogger.error("", e);
		} finally {
			if(sftpServices != null) {
				try {
					sftpServices.close();
				} catch (Exception e) {
					WfLogger.error("", e);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param serviceCall 
	 * @param fileName
	 * @throws Exception
	 */
	public void execute(String rootPath, String serviceCall) throws Exception {
		WfLogger.error("start processing file path:"+rootPath);
		String tempPath = "/";
		String uploadPath = rootPath+"/upload";
		String logPath = rootPath+"/error";
		String bakPath = rootPath+"/backup";
		ChannelSftp chSftp = sftpServices.getChannelSftp();
		chSftp.cd(uploadPath);
		Vector v = chSftp.ls("*");
		for (int i = 0; i < v.size(); i++) {
			handler = new NotificationHandler();
			//InputStream is = null;
			InputStream logIs = null;
			try {
				String fileName = v.get(i).toString().substring(v.get(i).toString().lastIndexOf(" ") + 1);
				String uploadFilePath = uploadPath+"/"+v.get(i).toString().substring(v.get(i).toString().lastIndexOf(" ") + 1);
				String bakFilePath = bakPath+"/"+v.get(i).toString().substring(v.get(i).toString().lastIndexOf(" ") + 1);
				String logFilePath = logPath+"/"+v.get(i).toString().substring(v.get(i).toString().lastIndexOf(" ") + 1)+".txt";
				File tempfile = File.createTempFile(UUID.randomUUID().toString(), ".xml");
				//读取文件并处理
				WfLogger.error("start processing file:"+uploadFilePath);
				chSftp.get(uploadFilePath, tempfile.getPath());
				//is = chSftp.get(uploadFilePath);
				boolean hasError = handler.parse(tempfile);
				if(hasError) {
					WfLogger.error(handler.getErrMsg().toString());
					//生成错误日志文件
					logIs = new ByteArrayInputStream(handler.getErrMsg().toString().getBytes("UTF-8"));
					chSftp.put(logIs, logFilePath);
				} else {
					hasError = handler.handle(fullFilePathErr, tempfile, serviceCall);
					if(hasError) {
						WfLogger.error(handler.getErrMsg().toString());
						logIs = new ByteArrayInputStream(handler.getErrMsg().toString().getBytes("UTF-8"));
						chSftp.put(logIs, logFilePath);
					}
				}
				//备份文件
				WfLogger.error("start backup file:"+uploadFilePath);
				chSftp.put(tempfile.getPath(), bakFilePath);
				chSftp.rm(uploadFilePath);
				tempfile.deleteOnExit();
			} catch (Exception e) {
				WfLogger.error("", e);
			} finally {
				/*if(is!=null) {
					try {
						is.close();
					} catch (IOException e) {
						WfLogger.error("", e);
					}
				}*/
				if(logIs!=null) {
					try {
						logIs.close();
					} catch (IOException e) {
						WfLogger.error("", e);
					}
				}
			}
		}
	}
}
