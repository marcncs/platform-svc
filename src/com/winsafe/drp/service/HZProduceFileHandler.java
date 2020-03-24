package com.winsafe.drp.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList; 
import java.util.List;

import net.sf.json.JSONObject;

import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.FileUtil;
import com.winsafe.drp.util.PlantConfig;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.sap.dao.AppPrimaryCode;
import com.winsafe.sap.pojo.UploadProduceLog;

/*******************************************************************************************
 * 类描述：  
 * 分装厂码替换处理类
 * @author: ryan.xi	  
 * @date：2017-12-01  
 * @version 1.0  
 *  
 * Version    Date       ModifiedBy                 Content  
 * -------- ---------    ----------         ------------------------  
 *                             
 *******************************************************************************************  
 */
public class HZProduceFileHandler extends ProduceFileHandler{
	
	private AppPrimaryCode appPrimaryCode = new AppPrimaryCode();

	/*public void proceeFile(UploadProduceLog uploadProduceLog, File file,
			StringBuffer resultMsg) throws Exception {
		try {
			//更新为处理中
			updateLogStatus(uploadProduceLog, SapUploadLogStatus.PROCESSING.getDatabaseValue());
			if (!file.exists()) {
				resultMsg.append(UploadErrorMsg.getError(UploadErrorMsg.E00214, file.getPath()));
			} else {
				execute(file, uploadProduceLog);
			}
			updateLogStatus(uploadProduceLog, SapUploadLogStatus.PROCESS_SUCCESS.getDatabaseValue());
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			logger.error("", e);
			resultMsg.append(e.getMessage());
			updateLogStatus(uploadProduceLog, SapUploadLogStatus.PROCESS_FAIL.getDatabaseValue());
		}
		
	}*/
	
	@Override
	public void execute(File zipFile, UploadProduceLog uploadProduceLog, StringBuffer resultMsg) throws Exception {
		int lineNo = 0;
		Integer printJobId = null;
		List<String> updSqls = new ArrayList<String>();
		BufferedReader br = null;
		List<File> files = unZipFiles(zipFile);
		File file = files.get(0);
		try {
			br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			for (String line = br.readLine(); line != null; line = br
					.readLine()) {
				// 判断是否为空行
				if ("".equals(line.trim())) {
					continue;
				}
				lineNo++;
				JSONObject obj = JSONObject.fromString(line);
				String primaryCode = obj.getString("primaryCode");
				String cartonCode = obj.getString("cartonCode");
				if(printJobId == null) {
					printJobId = obj.getInt("printJobId");
				}
				updSqls.add(getUpdSql(primaryCode, cartonCode));
				if(updSqls.size() == Constants.DB_BULK_SIZE) {
					appPrimaryCode.batchUpdPrimaryCodes(updSqls);
					updSqls.clear();
				}
			}
			if(updSqls.size() > 0) {
				appPrimaryCode.batchUpdPrimaryCodes(updSqls);
				updSqls.clear();
			}
			uploadProduceLog.setTotalCount(lineNo);
			uploadProduceLog.setPrintJobId(printJobId);
		} finally {
			br.close();
		}
	}
	
	private List<File> unZipFiles(File file) throws Exception {
		String savePath = PlantConfig.getConfig().getProperty("produceFilePath") + DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM") + "/";
		return FileUtil.unZipFiles(file, savePath);  
	}

	private String getUpdSql(String primaryCode, String cartonCode) {
		return "update PRIMARY_CODE set IS_USED=1, CARTON_CODE='"+cartonCode+"' where PRIMARY_CODE='"+primaryCode+"'";
	}


}
