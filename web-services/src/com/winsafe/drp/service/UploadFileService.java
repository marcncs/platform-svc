package com.winsafe.drp.service;

import org.apache.struts.upload.FormFile;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.PlantConfig;
import com.winsafe.erp.metadata.ProduceFileType;
import com.winsafe.hbm.util.DateUtil; 
import com.winsafe.sap.dao.AppUploadProduceLog;
import com.winsafe.sap.metadata.SapUploadLogStatus;
import com.winsafe.sap.pojo.UploadProduceLog;
import com.winsafe.sap.util.FileUploadUtil;
import com.winsafe.sap.util.MD5BigFileUtil;

public class UploadFileService {
	
	private AppUploadProduceLog appLog = new AppUploadProduceLog();
	
	private void addProduceUploadLog(FormFile convertCodeFile, UsersBean users, ProduceFileType fileType) throws Exception {
		//检查是否已上传过
		String md5 = MD5BigFileUtil.md5(convertCodeFile.getInputStream());
		
		// 保存文件
		String fileName = convertCodeFile.getFileName();
		String saveFileName = DateUtil.getCurrentDateTimeString() + "_" + fileName;
		String savePath = PlantConfig.getConfig().getProperty("produceFilePath") + DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM") + "/";
		FileUploadUtil.saveUplodedFile(convertCodeFile.getInputStream(), savePath, saveFileName);
		
		
		//添加文件上传日志
		
		UploadProduceLog uploadProduceLog = new UploadProduceLog();
		uploadProduceLog.setFileName(saveFileName);
		uploadProduceLog.setFilePath(savePath + saveFileName);
		if(users != null) {
			uploadProduceLog.setMakeId(users.getUserid());
			uploadProduceLog.setMakeOrganId(users.getMakeorganid());
		} else {
			uploadProduceLog.setMakeId(1);
			uploadProduceLog.setMakeOrganId("1");
		}
		uploadProduceLog.setMakeDate(DateUtil.getCurrentDate());
		uploadProduceLog.setIsDeal(SapUploadLogStatus.NOT_PROCESS
				.getDatabaseValue());
		uploadProduceLog.setFileHaseCode(md5);
		uploadProduceLog.setFileType(fileType.getValue().toString());
		appLog.addUploadProduceLog(uploadProduceLog);
	}
	
	private String checkFile(FormFile convertCodeFile, String fileSufix) {
		if (convertCodeFile != null && !convertCodeFile.equals("") && convertCodeFile.getContentType() != null) {
			if (!(convertCodeFile.getFileName().toLowerCase().indexOf(fileSufix) >= 0)) {
				return "文件后缀不正确";
			}
		} else {
			return "上传文件为空";
		}
		return null;
	}

	public String uploadFile(FormFile formFile, UsersBean users, ProduceFileType fileType, String fileSufix) throws Exception {

		String resultMsg = checkFile(formFile, fileSufix);
		
		if(!StringUtil.isEmpty(resultMsg)) {
//			ResponseUtil.writeJsonMsg(response, "-2", resultMsg);
			return resultMsg;
		}
		
		addProduceUploadLog(formFile, users, fileType);
		return null;
//		ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS,
//				Constants.CODE_SUCCESS_MSG);
		
	}

}
