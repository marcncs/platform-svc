package com.winsafe.drp.task.upr;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import com.winsafe.drp.action.machin.ThreadUploadProductReport;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.RecordDao;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.quartz.QuartzListener;

/**
 * 生产数据上传处理类(线程使用)
 * 
 * @author Andy.liu
 * 
 */
public class UploadHandle {
	String logFilePath;

	public void produce(String fileName, String fileFullPath, int logid,
			String saveFileName, InputStream is) throws Exception {
		String warehouseId = "";
		boolean bool = false;
		if (fileFullPath != null && !fileFullPath.equals("")) {
			if (fileFullPath.indexOf("mdb") >= 0
					|| fileFullPath.indexOf("accdb") >= 0) {
				bool = true;
			}
		}
		String filePath = QuartzListener.REAL_PATH + "WSPrint/Record/";
		String time = DateUtil.getCurrentDateTimeString();
		String path = filePath + fileName;
		logFilePath = path.substring(0, path.lastIndexOf("_")) + "_LOG" + time
				+ ".txt";
		if (bool) {
			InputStream fis = is;
			String firstName = fileName.substring(0, fileName.indexOf("."));
			String[] fileInfo = firstName.split("_");
			if (fileInfo.length < 2) {
				/*
				 * 文件格式不正确
				 */
				writeErrorLogToTxt("错误内容：文件名格式不正确！");
				updateDeal(logid, 0, 1);
				return;
			}
			AppOrgan ao = new AppOrgan();
			// 通过仓库简称得到仓库
			Organ o = ao.getOrganByShortName(fileInfo[0]);
			if (o == null) {
				/*
				 * 机构不存在
				 */
				writeErrorLogToTxt("错误内容：机构不存在！");
				updateDeal(logid, 0, 1);
				return;

			} else {// 机构存在
				AppWarehouse aw = new AppWarehouse();
				// 通过organID查找warehouse
				List<Warehouse> ows = aw.getWarehouseListByOID(o.getId());
				// 通过仓库简称查找warehouse
				Warehouse w = aw.getWarehouseByShortName(fileInfo[1]);
				boolean flag = false;
				for (Warehouse wh : ows) {
					if (wh.equals(w)) {
						warehouseId = w.getId();
						flag = true;
						break;
					}
				}
				if (!flag) {
					/*
					 * 机构中无此仓库
					 */
					writeErrorLogToTxt("错误内容：机构中无此仓库！");
					updateDeal(logid, 0, 1);
					return;

				}
			}
			this.backup(saveFileName, filePath, fis);
			String info = null;
			// 正式生产上传
			ThreadUploadProductReport tupr = new ThreadUploadProductReport(
					filePath + saveFileName, logid, warehouseId, logFilePath);
			info = tupr.run();
			if (info != null) {
				/*
				 * 其他错误
				 */
				writeErrorLogToTxt("错误内容：" + info);
				updateDeal(logid, 0, 1);
				return;

			}
		}else{
			writeErrorLogToTxt("错误内容：文件不属于MDB文件，读取失败!");
			updateDeal(logid, 0, 1);
			return;
		}
	}

	/**
	 * 备份
	 * 
	 * @param saveFileName
	 * @param filePath
	 * @param fis
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void backup(String saveFileName, String filePath, InputStream fis)
			throws FileNotFoundException, IOException {
		File filePathf = new File(filePath);
		if (!filePathf.exists()) {
			filePathf.mkdirs();
		}
		OutputStream fos = new FileOutputStream(filePath + saveFileName);

		// Richie yu 20100625-----备份2处
		String uploadFilePath = QuartzListener.REAL_PATH
				+ "/upload/ProduceReport/" + saveFileName;
		File dirFile = new File(QuartzListener.REAL_PATH
				+ "/upload/ProduceReport/");
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		OutputStream fos2 = new FileOutputStream(uploadFilePath);

		byte[] buffer = new byte[1048576];// 缓存1MB
		int bytesRead = 0;
		while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
			fos.write(buffer, 0, bytesRead);
			fos2.write(buffer, 0, bytesRead);
		}
		fos.close();
		fos2.close();
		fis.close();
	}

	/**
	 * 写日志 一条日志写一行
	 * 
	 * @param errorLog
	 * @throws Exception
	 */
	public void writeErrorLogToTxt(String errorLog) throws Exception {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(logFilePath, true)));
			out.write(errorLog);
			out.write("\r\n");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 主要功能:更新生产上传日志
	 * 
	 * @param logid
	 *            登陆者id
	 * @param isdeal
	 *            是否处理标识符
	 */
	public void updateDeal(Integer logid, Integer isdeal, int errorCount) {
		try {
			HibernateUtil.currentTransaction();
			RecordDao rDao = new RecordDao();
			rDao.updateDeal(logid, isdeal);
			// 添加日志错误信息
			rDao.updateLog(logid, logFilePath, errorCount);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
		} finally {
			// HibernateUtil.closeSession();
		}
	}

}
