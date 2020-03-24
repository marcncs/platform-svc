package com.winsafe.drp.task;

import java.io.IOException;


import org.apache.log4j.Logger; 

import com.winsafe.erp.metadata.FileType;
import com.winsafe.erp.services.FileTransferService;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;

/**
 * 自动处理导入文件的Sap文件
 * 
 * @author Ryan.Xi
 * 
 */
public class CreateCSSITransferFileTask {

	private static Logger logger = Logger
			.getLogger(CreateCSSITransferFileTask.class);
	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

//	private AppFileTransfer appTl = new AppFileTransfer();
	//要处理的传输日志
//	private List<TransferLog> transferLogs = null;

	/**
	 * 初始化要处理的任务
	 * 
	 * @throws IOException
	 */
	public void init() throws IOException {
//		transferLogs = appTl.getAllUnTransferedLogs();
	}

	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					logger.debug("start genernate primary code.");
					logger.info(DateUtil.getCurrentDate() + " 传输文件生成任务---开始---");
					this.init();
					isRunning = true;
					startCreateTransferFile();
				} catch (Exception e) {
					logger.info(DateUtil.getCurrentDate() + " 传输文件生成任务发生异常:"
							+ e.getMessage());
					logger.error("",e);
				} finally {
					HibernateUtil.closeSession();
					isRunning = false;
					logger.info(DateUtil.getCurrentDate() + " 传输文件生成任务---结束---");
				}
			}
		}
	}
	
	private void startCreateTransferFile() throws Exception {
		try {
			FileTransferService fts = FileTransferService.getTransferService(FileType.CSSI_FILE);
			fts.createTransferFile();
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			logger.error("生成传输文件发生异常",e);
		}
	}
}
