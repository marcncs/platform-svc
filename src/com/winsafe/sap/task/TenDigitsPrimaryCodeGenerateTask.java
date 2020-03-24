package com.winsafe.sap.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.getNumber.GetFWNumber;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.sap.dao.AppPrimaryCode;
import com.winsafe.sap.metadata.YesOrNo;
import com.winsafe.sap.pojo.PrimaryCode;

/**
 * 自动处理导入文件的Sap文件
 * 
 * @author Ryan.Xi
 * 
 */
public class TenDigitsPrimaryCodeGenerateTask {
	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

	private static Logger logger = Logger
			.getLogger(TenDigitsPrimaryCodeGenerateTask.class);

	private AppBaseResource appBaseResource = new AppBaseResource();

	private Integer tenDigitsPCodeCount;

	private String busyFromTime;
	private String busyToTime;
	private Integer busyTimeCount;
	private Integer idleTimeCount;

	private AppPrimaryCode appPrimaryCode = new AppPrimaryCode();

	/**
	 * 初始化要处理的任务
	 * @throws Exception 
	 */
	public void init() throws Exception {
		tenDigitsPCodeCount = Integer.parseInt(appBaseResource.getBaseResourceValue("TenDigitsPCodeCount", 1).getTagsubvalue());
		busyFromTime = appBaseResource.getBaseResourceValue("BusyFromTime", 1).getTagsubvalue();
		busyToTime = appBaseResource.getBaseResourceValue("BusyToTime", 1).getTagsubvalue();
		busyTimeCount = Integer.parseInt(appBaseResource.getBaseResourceValue("BusyTimeDelay", 1).getTagsubvalue());
		idleTimeCount = Integer.parseInt(appBaseResource.getBaseResourceValue("IdleTimeDelay", 1).getTagsubvalue());
	}

	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					logger.debug("start genernate primary code.");
					logger.info(DateUtil.getCurrentDate() + " 10位小包装码预生成任务---开始---");
					this.init();
					isRunning = true;
					execute();
				} catch (Exception e) {
					e.printStackTrace();
					try {
						logger.info(DateUtil.getCurrentDate() + " 10位小包装码预生成任务发生异常:" + e.getMessage());
						logger.error(e);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} finally {
					HibernateUtil.closeSession();
					isRunning = false;
					logger.info(DateUtil.getCurrentDate() + " 10位小包装码预生成任务---结束---");
				}
			}
		}

	}
	
	/**
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	public void execute() {
		Integer avaCount = appPrimaryCode.getAvailablePrimaryCodeCount(10);
		Integer genCount = 0;
		if(isBusyTime()) {
			genCount = busyTimeCount;
		} else {
			genCount = idleTimeCount;
		}
		logger.debug("genernate primary code, "+ avaCount);
		if(avaCount + genCount <= tenDigitsPCodeCount) {
			for(int i = 0; i < genCount; i++) {
				try {
					PrimaryCode primaryCode = new PrimaryCode();
					//生成10位小包装码
					primaryCode.setPrimaryCode(GetFWNumber.getFWNumber(10));
					primaryCode.setIsUsed(YesOrNo.NO.getValue());
					primaryCode.setCreateDate(Dateutil.getCurrentDate());
					primaryCode.setCodeLength(10);
					appPrimaryCode.addPrimaryCode(primaryCode);
					HibernateUtil.commitTransaction();
				} catch (Exception e) {
					logger.error("10位小包装码预生成发生异常", e);
					logger.error("hibernate rollback error: " + e.getMessage(), e);
					HibernateUtil.rollbackTransaction();
				}
			}
		}
	}

	private boolean isBusyTime() {
		Date currentTime = Dateutil.getCurrentDate();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
		String now = dateFormat.format(currentTime);
		if (now.compareTo(busyFromTime) > 0 && now.compareTo(busyToTime) < 0) {
			return true;
		} else {
			return false;
		}
	}
	
}
