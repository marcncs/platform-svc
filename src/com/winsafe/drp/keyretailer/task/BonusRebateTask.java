package com.winsafe.drp.keyretailer.task;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.winsafe.drp.keyretailer.dao.AppSBonusConfig;
import com.winsafe.drp.keyretailer.dao.AppSBonusDetail;
import com.winsafe.drp.keyretailer.dao.AppSBonusTarget;
import com.winsafe.drp.keyretailer.metadata.BonusType;
import com.winsafe.drp.keyretailer.pojo.SBonusConfig;
import com.winsafe.drp.keyretailer.pojo.SBonusTarget;
import com.winsafe.drp.keyretailer.service.SBonusService;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;

/**
 * 自动处理导入文件的Sap文件
 * 
 * @author Ryan.Xi
 * 
 */
public class BonusRebateTask {
	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

	private static Logger logger = Logger.getLogger(BonusRebateTask.class);
	
	private AppSBonusConfig appSBonusConfig = new AppSBonusConfig();
	private AppSBonusTarget appSBonusTarget = new AppSBonusTarget();
	private AppSBonusDetail appSBonusDetail = new AppSBonusDetail();
	private Map<Integer,SBonusConfig> bonusPeriod = new HashMap<Integer,SBonusConfig>();
	private Map<Integer,SBonusConfig> appraisePeriod = new HashMap<Integer,SBonusConfig>();
//	private Map<Integer,SBonusConfig> ratio = new HashMap<Integer,SBonusConfig>();
	
	
	/**
	 * 初始化要处理的任务
	 */

	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					isRunning = true;
					logger.info(DateUtil.getCurrentDate()
							+ " 自动统计积分任务---开始---");
					init();
					execute();
				} catch (Exception e) {
					HibernateUtil.rollbackTransaction();
					logger.error(e);
					logger.info(DateUtil.getCurrentDate()
							+ " 自动自动统计积分任务发生异常" + e.getMessage());
				} finally {
					HibernateUtil.closeSession();
					isRunning = false;
					logger.info(DateUtil.getCurrentDate()
							+ " 自动自动统计积分任务---结束---");
				}
			}
		}
	}

	private void init() {
		String curDate = DateUtil.getCurrentDateString();
		List<SBonusConfig> configs = appSBonusConfig.getNotCountedConfig(curDate);
		for(SBonusConfig config : configs) {
			if(config.getConfigType() == 1) {
				bonusPeriod.put(config.getYear(), config);
			} else if(config.getConfigType() == 2) {
				appraisePeriod.put(config.getYear(), config);
			} /*else if(config.getConfigType() == 3) {
				ratio.put(config.getYear(), config);
			}*/
		}
	}

	/**
	 * 
	 * @throws SQLException 
	 * @throws HibernateException 
	 */
	public void execute() throws Exception {
		Date curDate = DateUtil.getCurrentDate();
		//积分周期结束后统计所得积分，并对比目标计算完成率；
		for(Integer year : bonusPeriod.keySet()) {
			SBonusConfig sbc = bonusPeriod.get(year);
			if(curDate.compareTo(sbc.getEndDate()) > 0) {
				dealBonusPeriod(sbc);
				sbc.setIsCounted(1);
				appSBonusConfig.updSBonusConfig(sbc);
			}
		}
		HibernateUtil.commitTransaction();
		for(Integer year : appraisePeriod.keySet()) {
			SBonusConfig sbc = appraisePeriod.get(year);
			SBonusConfig ratioConfig = appSBonusConfig.getSBonusConfigByYearAndType(year,3);
			if(curDate.compareTo(sbc.getEndDate()) > 0) {
				if(ratioConfig == null) {
					SBonusService.addSBonusLog("", "计算"+year+"年度年终积分返利失败,未找到该年度达标系数设定", BonusType.REBATE, "");
					continue;
				}
				dealAppraisePeriod(sbc,ratioConfig);
				sbc.setIsCounted(1);
				appSBonusConfig.updSBonusConfig(sbc);
			}
		}
		HibernateUtil.commitTransaction();
	}
	
	/*private void dealAppraisePeriod(SBonusConfig sbc, SBonusConfig ratioConfig) throws Exception {
		List<SBonusTarget> btList = appSBonusTarget.getSBonusTargetByYear(sbc.getYear());
		for(SBonusTarget bt : btList) {
			//返利积分
			double rebate = 0d;
			//返利系数
			double rebateRate = 0d;
			//完成率
			double completeRate = 0d;
			if(bt.getFinalBonusPoint() != null && bt.getBonusPoint() != null && bt.getBonusPoint() != 0) {
				completeRate = ArithDouble.div(bt.getFinalBonusPoint(), bt.getBonusPoint(), 4);
			}
			//达标系数
			double ratio = Double.valueOf(ratioConfig.getValue());
			//是否支持
			boolean isSupported = false;
			if(bt.getIsSupported() == null || bt.getIsSupported() != 1) {
				isSupported = false;
			} else if(bt.getIsSupported() == 1){
				isSupported = true;
			} else {
				isSupported = false;
			}
			
			if(completeRate < ratio) {
				rebateRate = 0;
			} else if(completeRate >= 1.1) {
				if(isSupported) {
					rebateRate = 1.2;
				} else {
					rebateRate = 1.1;
				}
			} else if(completeRate >= 1.0) {
				if(isSupported) {
					rebateRate = 1.1;
				} else {
					rebateRate = 1.0;
				}
			} else {
				if(isSupported) {
					rebateRate = 0.9;
				} else {
					rebateRate = 0.8;
				}
			}
			rebate = ArithDouble.mul(bt.getFinalBonusPoint(), rebateRate);
			bt.setRebate(rebate);
			appSBonusTarget.updSBonusTarget(bt);
		}
	}*/
	
	private void dealAppraisePeriod(SBonusConfig sbc, SBonusConfig ratioConfig) throws Exception {
		List<Map<String,String>> btList = appSBonusTarget.getSBonusTargetByYearToAccount(sbc.getYear());
		for(Map<String,String> bt : btList) {
			String fromOrganId = bt.get("fromorganid");
			String toOrganId = bt.get("toorganid");
			Double targetPoint = getDouble(bt.get("targetpoint"));
			Double finalPoint = getDouble(bt.get("finalpoint"));
			String supported = bt.get("issupported");
			//返利系数
			double rebateRate = 0d;
			//完成率
			double completeRate = 0d;
			if(targetPoint != 0) {
				completeRate = ArithDouble.div(finalPoint, targetPoint, 4);
			}
			//达标系数
			double ratio = Double.valueOf(ratioConfig.getValue());
			//是否支持
			boolean isSupported = false;
			if("1".equals(supported)) {
				isSupported = true;
			} else {
				isSupported = false;
			}
			
			if(completeRate < ratio) {
				rebateRate = 0;
			} else if(completeRate >= 1.1) {
				if(isSupported) {
					rebateRate = 1.2;
				} else {
					rebateRate = 1.1;
				}
			} else if(completeRate >= 1.0) {
				if(isSupported) {
					rebateRate = 1.1;
				} else {
					rebateRate = 1.0;
				}
			} else {
				if(isSupported) {
					rebateRate = 0.9;
				} else {
					rebateRate = 0.8;
				}
			}
			appSBonusTarget.updSBonusTargetRebate(fromOrganId, toOrganId, rebateRate, sbc.getYear());
		}
	}

	private Double getDouble(String value) {
		if(StringUtil.isEmpty(value)) {
			return 0d;
		}
		return Double.valueOf(value);
	}

	private void dealBonusPeriod(SBonusConfig sbc) throws Exception {
		Map<String,String> finalBonusMap = appSBonusDetail.getFinalBonusMap(sbc.getYear());
		List<SBonusTarget> btList = appSBonusTarget.getSBonusTargetByYear(sbc.getYear());
		for(SBonusTarget bt : btList) {
			String finalBonus = finalBonusMap.get(bt.getToOrganId()+bt.getProductName()+bt.getSpec());
			if(!StringUtil.isEmpty(finalBonus)) {
				bt.setFinalBonusPoint(Double.valueOf(finalBonus));
			} else {
				bt.setFinalBonusPoint(0d);
			}
			appSBonusTarget.updSBonusTarget(bt);
		}
	}
}
