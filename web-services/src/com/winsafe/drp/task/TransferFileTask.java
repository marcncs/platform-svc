package com.winsafe.drp.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import com.winsafe.drp.util.HttpUtils;
import com.winsafe.drp.util.SysConfig;
import com.winsafe.drp.util.sftp.CSSISFTPServices;
import com.winsafe.drp.util.sftp.SFTPServices;
import com.winsafe.erp.dao.AppFileTransfer;
import com.winsafe.erp.metadata.FileType;
import com.winsafe.erp.metadata.TransferFileStatus;
import com.winsafe.erp.pojo.TransferLog;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Encrypt;

/**
 * 自动处理导入文件的Sap文件
 * 
 * @author Ryan.Xi
 * 
 */
public class TransferFileTask {

	private static Logger logger = Logger
			.getLogger(TransferFileTask.class);
	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

	private AppFileTransfer appTl = new AppFileTransfer();
	//要处理的传输日志
	private List<TransferLog> transferLogs = null;

	/**
	 * 初始化要处理的任务
	 * 
	 * @throws IOException
	 */
	public void init() throws IOException {
		transferLogs = appTl.getAllUnTransferedLogs();
	}

	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					logger.info(DateUtil.getCurrentDate() + " 数据传输任务---开始---");
					this.init();
					isRunning = true;
					startTransfer();
				} catch (Exception e) {
					logger.info(DateUtil.getCurrentDate() + " 数据传输任务发生异常:"
							+ e.getMessage());
					logger.error(e);
				} finally {
					HibernateUtil.closeSession();
					isRunning = false;
					logger.info(DateUtil.getCurrentDate() + " 数据传输任务---结束---");
				}
			}
		}
	}
	
	private void startTransfer() throws Exception {
		if(transferLogs.size() > 0) {
			SFTPServices sftpServices = new SFTPServices();
			//CSSISFTPServices cssiSftpServices = new CSSISFTPServices();
			try {
				for(TransferLog log : transferLogs) {
					if(FileType.CSSI_FILE.getValue().equals(log.getFileType())) {
						transferFileToCSSI(log);			
					} else {
						transferFile(sftpServices, log);			
					}
						
				}
			} finally {
				sftpServices.close();
			}
		}
	}

	private void transferFileToCSSI(TransferLog log) {
		logger.debug("开始传输日志编号为"+log.getId()+"的文件");
		File file = new File(log.getFilePath());
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));){
			String host = SysConfig.getSysConfig().getProperty("cssi_http_host");
			String username = SysConfig.getSysConfig().getProperty("cssi_http_username");
			String password = SysConfig.getSysConfig().getProperty("cssi_sftp_password");
			password = DigestUtils.md5Hex(Encrypt.getSecret(password, 2));
			StringBuilder sb = new StringBuilder();
			String s = "";
	        while ((s =br.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
	            sb.append(s).append("\r\n");//将读取的字符串添加换行符后累加存放在缓存中
	        }
	        String url= host+"/rtcidata/"+username+"/"+password+"/"+log.getFileName();
	        if(HttpUtils.uploadToCSSI(url, sb.toString())) {
	        	log.setStatus(TransferFileStatus.TRANSFERED.getValue());
	        } else {
	        	log.setStatus(TransferFileStatus.TRANSFER_ERROR.getValue());
	        }
	        updateTransferLog(log);
		} catch (Exception e) {
			logger.error("", e);
			log.setStatus(TransferFileStatus.TRANSFER_ERROR.getValue());
			updateTransferLog(log);
		}
		logger.debug("结束传输日志编号为"+log.getId()+"的文件");
	}

	private void transferFile(SFTPServices sftpServices, TransferLog log) {
		logger.debug("开始传输日志编号为"+log.getId()+"的文件");
		if(sftpServices.uploadFile(log.getFilePath(), log.getFileName())) {
			log.setStatus(TransferFileStatus.TRANSFERED.getValue());
			updateTransferLog(log);
		} else {
			log.setStatus(TransferFileStatus.TRANSFER_ERROR.getValue());
			updateTransferLog(log);
		}
		logger.debug("结束传输日志编号为"+log.getId()+"的文件");
	}
	
	private void transferFile(CSSISFTPServices sftpServices, TransferLog log) {
		logger.debug("开始传输日志编号为"+log.getId()+"的文件");
		if(sftpServices.uploadFile(log.getFilePath(), log.getFileName())) {
			log.setStatus(TransferFileStatus.TRANSFERED.getValue());
			updateTransferLog(log);
		} else {
			log.setStatus(TransferFileStatus.TRANSFER_ERROR.getValue());
			updateTransferLog(log);
		}
		logger.debug("结束传输日志编号为"+log.getId()+"的文件");
	}

	private void updateTransferLog(TransferLog log) {
		try {
			appTl.updTransferLog(log);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			logger.error("更新传输日志发生异常", e); 
		}
	}
	
	public static void main(String[] args) {
		new TransferFileTask().run();
	}
	
}
