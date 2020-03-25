package com.winsafe.sap.service;

import java.io.InputStream;

import org.apache.log4j.Logger;

import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.sap.dao.AppSapUploadLog;
import com.winsafe.sap.metadata.SapFileType;
import com.winsafe.sap.metadata.SapUploadLogStatus;
import com.winsafe.sap.pojo.UploadSAPLog;
import com.winsafe.sap.util.FileUploadUtil;

/*******************************************************************************************  
 * 类描述：  
 * 保存手工上传的SAP订单文件
 * @author: ryan.xi	  
 * @date：2014-10-13  
 * @version 1.0  
 *  
 *  
 * Version    Date       ModifiedBy                 Content  
 * -------- ---------    ----------         ------------------------  
 * 1.0      2014-10-13   ryan.xi                               
 *******************************************************************************************  
 */  
public class SapFileUploadService {

	private static Logger logger = Logger.getLogger(SapFileUploadService.class);

	private AppSapUploadLog appUploadSAPlog = new AppSapUploadLog();

	/**
	 * 保存文件,添加上传日志
	 * @param file
	 * @param fileName
	 * @param fileType
	 * @param savePath
	 * @param saveName
	 * @param user
	 * @param md5
	 * @throws Exception
	 */
	public void processSapFile(InputStream file, String fileName,
			SapFileType fileType, String savePath, String saveName,
			UsersBean user, String md5) throws Exception {

		logger.debug("save sap file " + fileName);
		// 保存上传的文件
		FileUploadUtil.saveUplodedFile(file, savePath, saveName);

		// 生成SAP文件上传日志记录
		UploadSAPLog uploadSAPLog = new UploadSAPLog();
		uploadSAPLog.setFileName(fileName);
		uploadSAPLog.setFilePath(savePath + saveName);
		uploadSAPLog.setFileType(fileType.getDatabaseValue());
		uploadSAPLog.setMakeId(user.getUserid());
		uploadSAPLog.setMakeOrganId(user.getMakeorganid());
		uploadSAPLog.setMakeDeptId(user.getMakedeptid());
		uploadSAPLog.setMakeDate(Dateutil.getCurrentDate());
		uploadSAPLog.setIsDeal(SapUploadLogStatus.NOT_PROCESS
				.getDatabaseValue());
		uploadSAPLog.setFileHaseCode(md5);
		appUploadSAPlog.addUploadSAPlog(uploadSAPLog);

	}

	/**
	 * 判断是否已上传过相同的文件
	 * @param md5
	 * @return
	 */
	public boolean isFileAlreadyExists(String md5) {
		return appUploadSAPlog.isFileAlreadyExists(md5);
	}
}
