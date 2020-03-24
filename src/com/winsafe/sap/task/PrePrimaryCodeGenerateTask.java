package com.winsafe.sap.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.getNumber.GetFWNumber;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.sap.dao.AppPrimaryCode;
import com.winsafe.sap.metadata.YesOrNo;
import com.winsafe.sap.pojo.PrimaryCode;

/*******************************************************************************************  
 * 13为小包装码预生成定时任务
 * @author: ryan.xi	  
 * @date：2014-11-28  
 * @version 1.0  
 *  
 *  
 * Version    Date       ModifiedBy                 Content  
 * -------- ---------    ----------         ------------------------  
 * 1.0      2014-11-28   ryan.xi                
 *******************************************************************************************  
 */  
public class PrePrimaryCodeGenerateTask {
	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;
	
	private static Logger logger = Logger
			.getLogger(PrePrimaryCodeGenerateTask.class);

	private AppBaseResource appBaseResource = new AppBaseResource();
	//需要与生成的总数量
	private Integer preGeneratedCount;
	//系统繁忙起始时间
	private String busyFromTime;
	//系统繁忙结束时间
	private String busyToTime;
	//系统繁忙时每次运行任务生成的小码数量
	private Integer busyTimeCount;
	//系统空闲时每次运行任务生成的小码数量
	private Integer idleTimeCount;

	private AppPrimaryCode appPrimaryCode = new AppPrimaryCode();

	/**
	 * 初始化要处理的任务
	 * @throws Exception 
	 */
	public void init() throws Exception {
		preGeneratedCount = Integer.parseInt(appBaseResource.getBaseResourceValue("PreGeneratedPCodeCnt", 1).getTagsubvalue());
		busyFromTime = appBaseResource.getBaseResourceValue("BusyFromTime", 1).getTagsubvalue();
		busyToTime = appBaseResource.getBaseResourceValue("BusyToTime", 1).getTagsubvalue();
		busyTimeCount = Integer.parseInt(appBaseResource.getBaseResourceValue("BusyTimeDelay", 1).getTagsubvalue());
		idleTimeCount = Integer.parseInt(appBaseResource.getBaseResourceValue("IdleTimeDelay", 1).getTagsubvalue());
	}
	/**
	 * 定时任务入口方法
	 */
	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					logger.debug("start genernate primary code.");
					this.init();
					isRunning = true;
					execute();
				} catch (Exception e) {
					e.printStackTrace();
					try {
						logger.error(e);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} finally {
					HibernateUtil.closeSession();
					isRunning = false;
				}
			}
		}

	}
	
	/**
	 * 预生成13位小包装码
	 * @param fileName
	 * @throws Exception
	 */
	public void execute() {
		Integer avaCount = appPrimaryCode.getAvailablePrimaryCodeCount(13);
		Integer genCount = 0;
		if(isBusyTime()) {
			genCount = busyTimeCount;
		} else {
			genCount = idleTimeCount;
		}
		logger.debug("genernate primary code, "+ avaCount);
		if(avaCount + genCount <= preGeneratedCount) {
			for(int i = 0; i < genCount; i++) {
				try {
					PrimaryCode primaryCode = new PrimaryCode();
					//生成13位小包装码
					primaryCode.setPrimaryCode(GetFWNumber.getFWNumber(13));
					primaryCode.setIsUsed(YesOrNo.NO.getValue());
					primaryCode.setCreateDate(Dateutil.getCurrentDate());
					primaryCode.setCodeLength(13);
					appPrimaryCode.addPrimaryCode(primaryCode);
					HibernateUtil.commitTransaction();
				} catch (Exception e) {
					logger.error("13位小包装码预生成发生异常", e);
					logger.error("hibernate rollback error: " + e.getMessage(), e);
					HibernateUtil.rollbackTransaction();
				}
			}
		}
	}

	/**
	 * 判断系统是否繁忙
	 * @return
	 */
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
