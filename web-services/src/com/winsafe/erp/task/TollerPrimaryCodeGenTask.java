package com.winsafe.erp.task;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.winsafe.erp.dao.AppApplyQrCode;
import com.winsafe.erp.metadata.QrStatus;
import com.winsafe.erp.services.TollerLabelGenerateService;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.sap.pojo.ApplyQrCode; 
/*******************************************************************************************  
 * 定时任务类,处理码申请,生成小包装码
 * @author: ryan.xi	  
 * @date：2017-11-01  
 * @version 1.0  
 *  
 *  
 * Version    Date       ModifiedBy                 Content  
 * -------- ---------    ----------         ------------------------  
 * 1.0      2017-11-01   ryan.xi   
 *******************************************************************************************  
 */  
public class TollerPrimaryCodeGenTask {
	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

	private static Logger logger = Logger
			.getLogger(TollerPrimaryCodeGenTask.class);

	//待处理的码申请
	private static List<ApplyQrCode> applyQrCodeList;

	private AppApplyQrCode appApplyQrCode = new AppApplyQrCode();


	/**
	 * 初始化要处理的任务
	 * 
	 * @throws Exception
	 */
	public void init() throws Exception {
		applyQrCodeList = appApplyQrCode.getAllUnProcessedApply();
	}
	/**
	 * 定时任务入口方法
	 */
	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					logger.debug("start processing sap upload file.");
					this.init();
					isRunning = true;
					logger
							.info(DateUtil.getCurrentDate()
									+ " 小包装码分配任务---开始---");
					if (applyQrCodeList != null) {
						for(ApplyQrCode aqc : applyQrCodeList) {
							logger.debug("开始时间："
									+ aqc.getId()
									+ new Date());
							long startTime = System.currentTimeMillis();
							if (startProcess(aqc)) {
								execute(aqc);
							}
							long endTime = System.currentTimeMillis();
							logger.debug("编号为"
									+ aqc.getId()
									+ "的码申请的生成时间为" + (endTime - startTime)
									+ "ms " + (endTime - startTime) / 1000
									+ "s");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.info(DateUtil.getCurrentDate() + " 小包装码分配任务发生异常"
							+ e.getCause().getMessage());
					logger.error(e);
				} finally {
					HibernateUtil.closeSession();
					isRunning = false;
					logger
							.info(DateUtil.getCurrentDate()
									+ " 小包装码分配任务---结束---");
				}
			}
		}

	}

	/**
	 * 更新打印任务的小包装码生成状态
	 * @param aqc
	 * @return
	 */
	private boolean startProcess(ApplyQrCode aqc) {
		try {
			aqc.setStatus(QrStatus.GENERATING.getValue());
			appApplyQrCode.updApplyQrCode(aqc);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			logger.error(
					"hibernate rollback error when update ApplyQrCode's status: "
							+ e.getMessage(), e);
			HibernateUtil.rollbackTransaction();
			return false;
		}
		return true;
	}

	/**
	 * 分配小包装码
	 * @param fileName
	 * @throws Exception 
	 * @throws Exception
	 */
	public void execute(ApplyQrCode aqc) {
		try {
			TollerLabelGenerateService tlg = new TollerLabelGenerateService(aqc);
			tlg.generateQrCode();
			aqc.setStatus(QrStatus.GENERATED.getValue());
			updApplyQrCode(aqc);
		} catch (Exception e) {
			logger.error("",e);
			aqc.setStatus(QrStatus.GENERATE_ERROR.getValue());
			updApplyQrCode(aqc);
		}
	}

	/**
	 * 更新打印任务
	 * @param printJob
	 */
	private void updApplyQrCode(ApplyQrCode applyQrCode) {
		try {
			appApplyQrCode.updApplyQrCode(applyQrCode);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			logger.error("hibernate rollback error: " + e.getMessage(), e);
			HibernateUtil.rollbackTransaction();
		}
	}
	
	public static void main(String[] args) {
		TollerPrimaryCodeGenTask task = new TollerPrimaryCodeGenTask();
		task.run();
	}

}
