package com.winsafe.drp.keyretailer.task;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganWithdraw;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.keyretailer.dao.AppSBonusConfig;
import com.winsafe.drp.keyretailer.metadata.BonusStatus;
import com.winsafe.drp.keyretailer.metadata.BonusType;
import com.winsafe.drp.keyretailer.pojo.SBonusConfig;
import com.winsafe.drp.keyretailer.service.SBonusService;
import com.winsafe.drp.metadata.DealerType;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;

/**
 * 自动处理导入文件的Sap文件
 * 
 * @author Ryan.Xi
 * 
 */
public class BonusTask {
	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

	private static Logger logger = Logger.getLogger(BonusTask.class);

	private AppStockAlterMove appStockAlterMove = new AppStockAlterMove();
	private AppOrganWithdraw appOrganWithdraw = new AppOrganWithdraw();
	private AppOrgan appOrgan = new AppOrgan();
	
	private SBonusService sBonusService = new SBonusService();
	private AppIdcode appIdcode = new AppIdcode();
	private AppSBonusConfig appSBonusConfig = new AppSBonusConfig();
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

	/**
	 * 
	 * @param fileName
	 * @throws Exception                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
	 * @throws Exception
	 */
	public void execute() {
		SBonusConfig sbc = appSBonusConfig.getCurrentPeriodConfig(DateUtil.getCurrentDateString(),1);
		//看是否在积分周期内
		if(sbc != null) {
			dealBKDOutBonus(sbc);
//			dealBKRInBonus();
			dealBKRReturnBonus(sbc);
			dealBKRReturnTBonus(sbc);
		}
	}
	
	private void dealBKRReturnBonus(SBonusConfig sbc) {
		List<OrganWithdraw> owList = appOrganWithdraw.getOrganWithdrawToBonus(DateUtil.formatDate(sbc.getStartDate()), DateUtil.formatDate(sbc.getEndDate()));
		for(OrganWithdraw ow : owList) {
			BonusStatus bs = BonusStatus.NOT_COUNTTED;
			try {
				List<Idcode> idcodeList = appIdcode.getIdcodeByOwid(ow.getId());
				if(idcodeList.size() > 0) {
					sBonusService.deductSBonus(ow.getPorganid(), ow.getReceiveorganid(), idcodeList,DealerType.BKR, BonusType.RETURN, ow.getId());
//					ow.setBonusStatus(BonusStatus.DELIVER.getValue());
//					updateOrganWithdraw(ow);
//					bs = BonusStatus.DELIVER;
					Organ inOrgan = appOrgan.getOrganByID(ow.getReceiveorganid());
					if(inOrgan.getOrganModel() != 1) {//退给非PD的,收货机构才扣减积分
						sBonusService.deductSBonus(ow.getReceiveorganid(), ow.getPorganid(), idcodeList, DealerType.BKD, BonusType.RETURN_RECEIVE, ow.getId());
					}
					appIdcode.undoIntegral(ow.getId());
					ow.setBonusStatus(BonusStatus.COMPLETED.getValue());
					updateOrganWithdraw(ow);
				}
				HibernateUtil.commitTransaction();
			} catch (Exception e) {
				HibernateUtil.rollbackTransaction();
				logger.error("单据"+ow.getId()+"积分失败："+e.getMessage(),e);
				if(bs == BonusStatus.NOT_COUNTTED) {
					ow.setBonusStatus(BonusStatus.DELIVER_FAILED.getValue());
				} else if(bs == BonusStatus.DELIVER) {
					ow.setBonusStatus(BonusStatus.RECEIVE_FAILED.getValue());
				}
				updateOrganWithdraw(ow);
			}
		}
	}

	private void updateOrganWithdraw(OrganWithdraw ow) {
		try {
			appOrganWithdraw.update(ow);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			logger.error("更新单据"+ow.getId()+"的积分状态失败",e);
		}
		
	}

	/*private void dealBKRInBonus() {
		List<StockAlterMove> samList = appStockAlterMove.getReceivedStockAlterMoveToBonus();
		for(StockAlterMove sam : samList) {
			try {
				List<Idcode> idcodeList = appIdcode.getInIdcodeBySamid(sam.getId());
				if(idcodeList.size() > 0) {
					sBonusService.addSBonus(sam.getReceiveorganid(),sam.getOutOrganId(), idcodeList, DealerType.BKR, BonusType.DELIVER_RECEIVE, sam.getId());
					sam.setBonusStatus(BonusStatus.COMPLETED.getValue());
					updateStockAlterMove(sam);
				}
			} catch (Exception e) {
				HibernateUtil.rollbackTransaction();
				logger.error("单据"+sam.getId()+"积分失败："+e.getMessage(),e);
				sam.setBonusStatus(BonusStatus.DELIVER_FAILED.getValue());
				updateStockAlterMove(sam);
			}
		}
		
	}*/

	private void dealBKDOutBonus(SBonusConfig sbc) { 
		List<StockAlterMove> samList = appStockAlterMove.getShippedStockAlterMoveToBonus(DateUtil.formatDate(sbc.getStartDate()), DateUtil.formatDate(sbc.getEndDate())); 
		for(StockAlterMove sam : samList) {
			try {
				Set<String> productSet = new HashSet<String>();
				List<Idcode> idcodeList = appIdcode.getInIdcodeBySamid(sam.getId());
				if(idcodeList.size() > 0) {
					Organ outOrgan = appOrgan.getOrganByID(sam.getOutOrganId());
					if(outOrgan.getOrganModel() != 1 ) {//出库机构不是PD才加积分
						sBonusService.addSBonus(sam.getOutOrganId(), sam.getReceiveorganid(), idcodeList, DealerType.BKD, BonusType.DELIVER, sam.getId(), productSet);
					}
					Organ inOrgan = appOrgan.getOrganByID(sam.getReceiveorganid());
					if((inOrgan.getOrganModel()!=1&&inOrgan.getOrganModel()!=2)&& inOrgan.getOrganType() == 2 && inOrgan.getIsKeyRetailer() != null && inOrgan.getIsKeyRetailer() == 1) {
						sBonusService.addSBonus(sam.getReceiveorganid(),sam.getOutOrganId(), idcodeList, DealerType.BKR, BonusType.DELIVER_RECEIVE, sam.getId(), productSet);
					} 
					sam.setBonusStatus(BonusStatus.COMPLETED.getValue());
					updateStockAlterMove(sam);
					sBonusService.updIdcodeIntegralStatus(sam.getId(), productSet);
				}
				HibernateUtil.commitTransaction();
			} catch (Exception e) {
				HibernateUtil.rollbackTransaction();
				logger.error("单据"+sam.getId()+"积分失败："+e.getMessage(),e);
				sam.setBonusStatus(BonusStatus.DELIVER_FAILED.getValue());
				updateStockAlterMove(sam);
			}
		}
	}

	private void updateStockAlterMove(StockAlterMove sam) {
		try {
			appStockAlterMove.updstockAlterMove(sam);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			logger.error("更新单据"+sam.getId()+"的积分状态失败",e);
		}
		
	}
	

	//非关健-->关健
	private void dealBKRReturnTBonus(SBonusConfig sbc) {
		List<OrganWithdraw> owList = appOrganWithdraw.getOrganWithdrawToTBonus(DateUtil.formatDate(sbc.getStartDate()), DateUtil.formatDate(sbc.getEndDate()));
		for(OrganWithdraw ow : owList) {
			BonusStatus bs = BonusStatus.NOT_COUNTTED;
			try {
				List<Idcode> idcodeList = appIdcode.getIdcodeByOwid(ow.getId());
				if(idcodeList.size() > 0) {
//					sBonusService.deductSBonus(ow.getPorganid(), ow.getReceiveorganid(), idcodeList,DealerType.BKR, BonusType.RETURN, ow.getId());
//					ow.setBonusStatus(BonusStatus.DELIVER.getValue());
//					updateOrganWithdraw(ow);
//					bs = BonusStatus.DELIVER;
					sBonusService.deductSBonus(ow.getReceiveorganid(), ow.getPorganid(), idcodeList, DealerType.BKD, BonusType.RETURN_RECEIVE, ow.getId());
					appIdcode.undoIntegral(ow.getId());
					ow.setBonusStatus(BonusStatus.COMPLETED.getValue());
					updateOrganWithdraw(ow);
				}
				HibernateUtil.commitTransaction();
			} catch (Exception e) {
				HibernateUtil.rollbackTransaction();
				logger.error("单据"+ow.getId()+"积分失败："+e.getMessage(),e);
				if(bs == BonusStatus.NOT_COUNTTED) {
					ow.setBonusStatus(BonusStatus.DELIVER_FAILED.getValue());
				} else if(bs == BonusStatus.DELIVER) {
					ow.setBonusStatus(BonusStatus.RECEIVE_FAILED.getValue());
				}
				updateOrganWithdraw(ow);
			}
		}
	}
	
}
