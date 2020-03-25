package com.winsafe.drp.util;

import org.apache.log4j.Logger;

import com.winsafe.drp.dao.AppBarcodeUpload;
import com.winsafe.drp.dao.BarcodeUpload;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.DealUploadStockCheckServer;
import com.winsafe.hbm.entity.HibernateUtil;

public class UploadStockCheckListener {
	// 日志
	private static Logger logger = Logger.getLogger(UploadStockCheckListener.class);
	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

	private AppBarcodeUpload abu = new AppBarcodeUpload();
	private DealUploadStockCheckServer barcodeuploadservice = new DealUploadStockCheckServer();

	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					isRunning = true;
					// 调用处理上传文件入口
					autoBarcodeUpload();
				} catch (Exception e) {
					logger.error("", e);
				} finally {
					isRunning = false;
				}
			}
		}
	}

	public void autoBarcodeUpload() throws Exception {
		try {
			// logger.info("----条码上传监听任务 --开始---");
			String sql = "from BarcodeUpload where isupload=0  order by makedate asc";
			BarcodeUpload iu = (BarcodeUpload) EntityManager.findReadOnly(sql);
			if (iu != null) {
				try {
					// 更新条码上传isupload状态
					abu.updIsUpload(iu.getId(), 1);
					// 更新条码上传为处理中
					abu.updIsDeal(iu.getId(), 1);
					HibernateUtil.commitTransaction();

					dealBarCodeUpload(iu); // 调用
					HibernateUtil.commitTransaction();
				} catch (Exception e) {
					logger.error("", e);
					HibernateUtil.rollbackTransaction();
				}
			}
			// logger.info("----条码上传监听任务 --结束---");
		} catch (Exception e) {
			logger.error("条码上传监听任务 ", e);
		} finally {
			HibernateUtil.closeSession();
		}
	}

	private void dealBarCodeUpload(BarcodeUpload iu) throws Exception {
		Integer id = iu.getId();
		String physicalpath = iu.getPhysicalpath();
		String upusername = iu.getUpusername();
		String fileaddress = iu.getFilepath();
		// 操作类型标志
		Integer billsort = iu.getBillsort();
		if (billsort == 99) {
			// 处理上传盘库文件
			barcodeuploadservice.dealBarcodeUpload(physicalpath,
			 fileaddress, id, upusername);
		}
	}

}
