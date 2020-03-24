package com.winsafe.drp.util.fileListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.winsafe.common.util.FileUtil;
import com.winsafe.common.util.Function;
import com.winsafe.drp.server.StockAlterMoveImportService;
import com.winsafe.drp.util.Config;
import com.winsafe.hbm.util.DateUtil;

public class ImportFile extends Thread{
	private static final Logger log = Logger.getLogger(FileMonitor.class);
	private long nextSleepTime = Config.getSleeptimeMin();
	private StockAlterMoveImportService service = new StockAlterMoveImportService();
	/**
	 * 日期格式
	 */
	private SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
	private SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
	
	public void run() {
		List<File> fileFrom = new ArrayList<File>();
		fileFrom.add(new File(Config.getImportFileDir()));
		while (true) {
			try {
				dealFile(fileFrom);
			} catch (Exception e) {
				e.printStackTrace();
				log.info("", e);
			} finally {
				try {
					Thread.sleep(nextSleepTime);
					nextSleepTime = Function.getSleepTime(nextSleepTime, Config
							.getSleeptimeMin(), Config.getSleeptimeMax());
				} catch (Exception e) {
				}
			}

		}
	}
	
	private void dealFile(List<File> fromDir){
		// 临时文件对象
		File tempFile;

		for (File file : fromDir) {
			File[] fileArray = file.listFiles();
			if (fileArray == null) {
				return;
			}
			Function.sortFile(fileArray);
			File temp;
			for (int i = 0; i < fileArray.length; i++) {
				temp = fileArray[i];

				if (temp.exists() && temp.isFile() && temp.canRead()) {
					
					// set sleeptime
					nextSleepTime = Config.getSleeptimeMin();
					try {
						
						// 复制文件到临时目录
						tempFile = FileUtil.getUniqueFile(temp.getName(), Config.getTempDir());

						// 如果转移文件失败，不做下面操作

						if (!FileUtil.rename(temp,tempFile)) {
							continue;
						}

						/*
						 * 备份文件
						 */
						String root=Config.getBackupDir();
						// 文件名
						String filerealname = temp.getName();
						// 备份上传文件路径
						String fileAddress = "";
						// 备份上传文件路径2
						String fileAddressBak = "";
						
						// 本地备份
						String saveFileName = DateUtil.getCurrentDateTimeString() + "_"
								+ filerealname;
						String uploadPath = "/backup1/StockAlterMove/"
								+ DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM")
								+ "/";
						String uploadBakPath = "/backup2/StockAlterMove/"
								+ DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM")
								+ "/";

						FileUtil.createDir(root + uploadPath);
						FileUtil.createDir(root + uploadBakPath);
						// 保存原文件(2处备份)
						fileAddress = root + uploadPath + saveFileName;
						fileAddressBak = root + uploadBakPath + saveFileName;
						InputStream fis = new FileInputStream(tempFile);
						FileUtil.copyFile(fis, fileAddress,fileAddressBak);

						//上传日志
						log.info("ftp上传："+temp.getName());
						
						String ftpLogPath = Config.getLogDir()+ "/ftp_" + df.format(new Date())+ ".log";
						FileUtil.write(df2.format(new Date())+" "+temp.getName(), ftpLogPath);
						
						/*
						 * 判断文件格式
						 */

						if (tempFile.getName().endsWith(".xls")) {
							// do nothing;
						} else {
							throw new Exception("文件类型不正确，"+fileAddress);
						}
						/*
						 * 处理文件
						 */
						String result = service.dealFile(fileAddress,null,null);
						result = result.replace("<br>", "\r\n");
						
						result = "文件名:"+fileAddress+"\r\n"+result;
						log.info(result);
						
						tempFile.delete();
					} catch (Exception e) {
						e.printStackTrace();
						log.info("", e);
					}
				}
			}
		}
	}
}
