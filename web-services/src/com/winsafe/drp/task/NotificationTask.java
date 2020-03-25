package com.winsafe.drp.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.log4j.Logger;


import com.winsafe.drp.task.upr.IFile;
import com.winsafe.drp.task.upr.IFileFactory;
import com.winsafe.drp.task.upr.TFile;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.notification.util.NotificationHandler;
import com.winsafe.sap.util.FileUploadUtil;
import com.winsafe.sap.util.SapConfig;
import com.winsafe.sap.util.UnicodeInputStream;

/**
 * 自动处理生产数据
 * @author Andy.liu
 *
 */
public class NotificationTask{
	
	private static Logger logger = Logger.getLogger(NotificationTask.class);
	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

	private NotificationHandler handler;
	
	private final static String FILE_PATH = "notificationFilePath";
	private final static String IDENTITY = "identity";

	private String[] filePaths; // 目录
	private String filePathBak; // 备份目录
	private String filePathErr; // 错误日志目录
	private String fullFilePathErr;// 错误日志文件目录
	private int identity;
	private String[] serviceCalls;

	/**
	 * 初始化路径
	 */
	public void init() throws Exception{
		filePaths = SapConfig.getSapConfig().getProperty(FILE_PATH).split(",");
		identity =Integer.valueOf(SapConfig.getSapConfig().getProperty(IDENTITY));
		serviceCalls = SapConfig.getSapConfig().getProperty("logistic.service.call").split(",");
	}

	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					this.init();
					isRunning = true;
					logger.info(DateUtil.getCurrentDate() + "自动处理物流信息通知---开始---");
					for(int i=0; i<filePaths.length; i++) {
						String filePath = filePaths[i];
						String serviceCall = serviceCalls[i];
						IFile file = IFileFactory.getInstence(identity,filePath+"upload");
						if(file==null){
							return;
						}
						filePathBak = filePath + "backup" + File.separator + DateUtil.getCurrentYear();
						filePathErr = filePath + "error";
						List obj = file.getFiles();
						if (null==obj) { //无文件 
							logger.info("[文件不存在]: "+filePath+"upload");
							continue;
						}
						for (Object object : obj) {
							File tmpfile = (File)object;
							
							//只读取xml文件
							if(tmpfile == null || !tmpfile.getName().toLowerCase().endsWith(".xml")){
								continue;
							}
							logger.info("处理的文件："+tmpfile.getAbsolutePath());
							String errFileName = tmpfile.getName().substring(0, tmpfile.getName().indexOf(".")) + ".err";
							fullFilePathErr = filePathErr + File.separator + errFileName;
							if(object instanceof File){
								File f = (File)object;
								//备份文件
								String filefullpath = notifyBakFile(f);
								this.execute(new File(filefullpath), serviceCall);
							}
							logger.info("文件："+tmpfile.getAbsolutePath()+"处理完成");
						}
					}
				} catch (Exception e) {
					logger.error(e);
					logger.info(DateUtil.getCurrentDate() + "自动处理物流信息通知发生异常", e);
				} finally {
					HibernateUtil.closeSession();
					isRunning = false;
					try {
						logger.info(DateUtil.getCurrentDate() + "自动处理物流信息通知---结束---");
					} catch (Exception e) {
						logger.error(e);
					}
				}
			}
		}

	}
	
	/**
	 * 
	 * @param serviceCall 
	 * @param fileName
	 * @throws Exception
	 */
	public void execute(File file, String serviceCall){
		logger.debug("start processing file :" + file.getName());
		handler = new NotificationHandler();
		boolean hasError = handler.parse(file);
		try {
			if(hasError) {
				//生成错误日志文件
				notifyErrFile(handler.getErrMsg().toString(), file.getName());
			} else {
				hasError = handler.handle(fullFilePathErr, file, serviceCall);
				if(hasError) {
					notifyErrFile(handler.getErrMsg().toString(), file.getName());
				}
			}
		} catch (Exception e) {
			logger.error("error occured when create notification log file", e);
		} 
	}

	/**
	 * 备份文件(与源目录同级文件夹)
	 * @param filePath 
	 * 
	 * @param files
	 * @return BakPathfileName备份文件完整路径
	 */
	public String notifyBakFile(File file) throws Exception {
		logger.debug("start backup file: " + file.getName());
		//创建文件备份目录
		TFile.mkdirs(filePathBak);
		//备份文件
		String fileName = file.getName();
		String fullFilePath = filePathBak + File.separator+ fileName;
		copyFile(file.getPath(), fullFilePath);
		// 删除源文件 
		file.delete();
		logger.debug("backup file complete");
		return fullFilePath;
		
	}
	
	/**
	 * 添加错误日志文件
	 * @param filePath 
	 * 
	 * @param files
	 * @return BakPathfileName备份文件完整路径
	 */
	public void notifyErrFile(String errMsg, String fileName) throws Exception {
		logger.debug("start create error file :" + fileName);
		//创建文件备份目录
		TFile.mkdirs(filePathErr);
		//保存日志文件
		fileName = fileName.substring(0, fileName.indexOf(".")) + ".err";
		FileUploadUtil.CreateFileWithMessage(errMsg, filePathErr + File.separator, fileName);
		logger.debug("create error file complete");
	}
	
	public static void copyFile(String src, String dest) throws Exception {
		logger.debug("backup file from " + src + " to "+dest);
		UnicodeInputStream uis = null;
		FileOutputStream out = null;
		try {
			uis = new UnicodeInputStream(new FileInputStream(src), Constants.DEFAULT_FILE_ENCODE);
			File file = new File(dest);
			if (!file.exists())
			    file.createNewFile();
			out = new FileOutputStream(file);
			int c;
			byte buffer[] = new byte[1024*8];
			while ((c = uis.read(buffer)) != -1) {
				out.write(buffer, 0, c);
			}
		} catch (Exception e) {
			logger.debug("backup file error:" + e.getMessage(), e);
			throw e;
		} finally {
			if(uis != null) {uis.close();}
			if(out != null) {out.close();;}
		}
		
	}

}
