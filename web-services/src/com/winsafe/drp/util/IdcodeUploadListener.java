package com.winsafe.drp.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.winsafe.drp.dao.AppIdcodeUpload;
import com.winsafe.drp.dao.AppIdcodeUploadLog;
import com.winsafe.drp.dao.IdcodeUpload;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.DealUploadIdcodeService;
import com.winsafe.hbm.entity.HibernateUtil;

public class IdcodeUploadListener  {
	//日志
	private static Logger logger = Logger.getLogger(IdcodeUploadListener.class);
	//同步锁
	private static Object lock = new Object();
	private static boolean isRunning=false;
	
	private static Map<Integer, IdcodeUpload> idcodeUploads = new HashMap<Integer, IdcodeUpload>();
	
	private DealUploadIdcodeService uploadIdcodeService = new DealUploadIdcodeService();
	private AppIdcodeUpload appiu = new AppIdcodeUpload();
	private AppIdcodeUploadLog appIdcodeUploadLog = new AppIdcodeUploadLog();
	
	public void run() {
		if(!isRunning){
			synchronized(lock){
				try {
					isRunning = true;
					//调用处理上传文件入口
					autoIdcodeUpload();
				} catch (Exception e) {
					logger.error("", e);
				}finally{
					isRunning = false;
				}
			}
		}
	}
	
	public  void autoIdcodeUpload() throws Exception {      
		try {
			String sql = "from IdcodeUpload where isupload=0 order by makedate asc";
			IdcodeUpload iu  = (IdcodeUpload) EntityManager.findReadOnly(sql);
			if(iu != null){
//				logger.info("----条码上传监听任务 --开始---");
				try {
					
					if(appiu.isFileAlreadyExists(iu.getFileHashCode())) {
						iu.setIsdeal(-1);
						iu.setIsupload(1);
						addFailedLogFile(iu);
						appiu.updIdcodeUpload(iu);
						HibernateUtil.commitTransaction();
					} else {
						idcodeUploads.put(iu.getId(), iu);
						//更新条码上传isupload状态
						appiu.updIsUpload(iu.getId(), 1);
						//更新条码上传为处理中
						appiu.updIsDeal(iu.getId(), 1);
						HibernateUtil.commitTransaction();
						
						dealIdCodeUpload(iu);	//调用
						HibernateUtil.commitTransaction();
					}
					
				} catch (Exception e) {
					logger.error("", e);
					HibernateUtil.rollbackTransaction();
				}finally {
					idcodeUploads.remove(iu.getId());
//					logger.info("----条码上传监听任务 --结束---");
				}
			}
		} catch (Exception e) {
			logger.error("条码上传监听任务 ", e);
		} finally {
			HibernateUtil.closeSession();
		}
	}

	private void addFailedLogFile(IdcodeUpload iu) {
		String physicalpath = iu.getPhysicalpath();
		String fileaddress = iu.getFilepath();
		fileaddress = fileaddress.substring(0, fileaddress.lastIndexOf(".")) + "_fail.txt";
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(physicalpath+fileaddress, false),Constants.DEFAULT_FILE_ENCODE));
			out.write("已处理过相同的文件");
		} catch (Exception e) {
			logger.error("创建错误日志文件时发生异常 ", e);
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					logger.error("",e);
				}
			}
		}
		iu.setFailfilepath(fileaddress);
	}

	private void dealIdCodeUpload(IdcodeUpload iu) throws Exception{
		List<String> errMsg = null;
		Integer id = iu.getId();
		String physicalpath = iu.getPhysicalpath();
		String upusername = iu.getUpusername();
		String fileaddress = iu.getFilepath();
		Integer billsort = iu.getBillsort();
		//有单无单标志
		Integer isticket = iu.getIsticket();
		//有单
		if(isticket==1){
			uploadIdcodeService.threadDealCode(billsort, physicalpath, fileaddress, iu.getId());
		}
		/** 无单处理:
		 *  无单订购出库	17
			无单转仓	18
			无单退货	19
			无单退回	21
		 */
		else if(isticket==0){
			if (billsort == 17) {
				// 无单出库
				uploadIdcodeService.dealNoBillStockAlterMove(physicalpath, fileaddress, id, upusername);
			} else if (billsort == 18) {
				// 无单转仓
				uploadIdcodeService.dealNoBillStockMove(physicalpath, fileaddress, id, upusername);
			} else if (billsort == 19) {
				// 无单退货
				uploadIdcodeService.dealNoBillOrganWithdraw(physicalpath, fileaddress, id, upusername);
			} else if (billsort == 21) {
				// 无单退回
				uploadIdcodeService.dealNoBillPlantWithdraw(physicalpath, fileaddress, id, upusername);
			} else if (billsort == 23) {
				// BKD无单出库
				uploadIdcodeService.dealBKDNoBillStockAlterMove(physicalpath, fileaddress, id, upusername);
			} else if (billsort == 24) {
				// BKR无单退回
				uploadIdcodeService.dealBKDNoBillOrganWithdraw(physicalpath, fileaddress, id, upusername);
			} 
		}
		//手工上传数据
		else if(isticket==2){
			if (billsort == 18) {
				// 无单一级经销商出库
				uploadIdcodeService.dealNoBillStockAlterMoveByNotExist(physicalpath, fileaddress, id, upusername);
			} else if (billsort == 20) {
				// 经销商入库签收
				uploadIdcodeService.dealReceiveIncomeByNotExist(physicalpath, fileaddress, id, upusername);
			}
		}
	}
	
	public static IdcodeUpload getIdcodeUpload(Integer idcodeUploadId) {
		return idcodeUploads.get(idcodeUploadId);
	}

}
